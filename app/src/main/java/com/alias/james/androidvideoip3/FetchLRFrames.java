/*
 * File:   FetchLRFrames.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class fetches a frame from each of the cameras--left and right
 *                   (on the robot).
 *
 * References: See AndroidVideoIp2 mainActivity.java
 *             http://stackoverflow.com/questions/2586301/set-inputtype-for-an-edittext
 *             http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
 *
 *
 * Last Modified: 8/11/2015
 */


package com.alias.james.androidvideoip3;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;


public class FetchLRFrames extends AsyncTask<Integer, Integer, Long>
{
    private Socket socket; /** To connect to robot */
    private String ipAddressStr = "10.0.4.6"; /** IP address of robot host.  Currently hard-coded to robot-lab6 (James Kuczynski's computer). */
    private int port = 50000; /** Is the port used for this connection. */
    private final int MATRIX_SIZE = 921600; /** Is the size of the OpenCV matrix sent. */
    private Bitmap lFrame; /** Is a bitmap created from the OpenCV matrix passed from the robot's left camera */
    private Bitmap rFrame; /** Is a bitmap created from the OpenCV matrix passed from the robot's right camera */
    private Activity activity; /** is a reference to the MainActivity's Acitivy object */
    private CameraOptions cameraOptions; /** Is a reference to the fragment which allows the user to choose the left or right camera */


    /**
     * This constructor method initializes variables and loads the OpenCV JNI modules.
     *
     * @param resources
     * @param activity
     */
    public FetchLRFrames(Resources resources, Activity activity) //Resources from Activity
    {
        System.out.println("^^^Starting FetchLRFrames...^^^");
        this.activity = activity;

        if(!OpenCVLoader.initDebug() )
        {
            System.err.println("^^^Failed to load OpenCV @ FetchLRFrames::FetchLRFrames()");
        }

        lFrame = BitmapFactory.decodeResource(resources, R.drawable.camera_off);
        rFrame = BitmapFactory.decodeResource(resources, R.drawable.camera_off);

        cameraOptions = new CameraOptions();
        cameraOptions.setLeftBitmap(getlFrame());
        cameraOptions.setRightBitmap(getRFrame());
        cameraOptions.setArguments(activity.getIntent().getExtras());
    }


    /**
     * Connects to the server.
     * TODO: The connection should take place elsewhere, so it can update
     * TODO (cont): See how class DataCom does this.
     *
     * @param params three (meaningless) numbers
     *
     * @return null
     */
    @Override
    protected Long doInBackground(Integer... params)
    {
        try
        {
            InetAddress serverAddress = InetAddress.getByName(ipAddressStr);
            socket = new Socket(serverAddress, port);
            System.out.println("^^^Successfully connected to server^^^");


            InputStream sub = socket.getInputStream();
            System.out.println("^^^Generating new InputStream obj");
            // !!!???will this be true 32 & 64 bit processors???!!!
            byte[] buffer = new byte[MATRIX_SIZE];
            int currPos = 0;
            int bytesRead = 0;

            do
            {
                bytesRead = sub.read(buffer, currPos, (buffer.length - currPos) );

                if(bytesRead != -1 && bytesRead != 0)
                {
                    currPos += bytesRead;
                }
                else
                {
                    break;
                }

            }while(bytesRead != -1 && bytesRead != 0);

            System.out.println("^^^recieved image bytes:" + currPos);
            Mat mat = new Mat(480, 640, CvType.CV_8UC3);
            mat.put(0, 0, buffer);
            lFrame = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, lFrame);
        }
        catch (UnknownHostException e)
        {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        }

        cameraOptions.setLeftBitmap(getlFrame());
        cameraOptions.setRightBitmap(getRFrame());

        return null;
    }


    /**
     * Updates the buttons with the fetched images.
     *
     * @param aLong
     */
    @Override
    protected void onPostExecute(Long aLong)
    {
        super.onPostExecute(aLong);
        cameraOptions.updateButtons(getlFrame(), getRFrame());
    }


    /**
     * Mutator.
     * @see #port
     *
     * @param port
     */
    public void setPort(int port)
    {
        this.port = port;
    }


    /**
     * Accessor.
     * @see #port
     *
     * @return
     */
    public int getPort()
    {
        return port;
    }


    /**
     * Mutator.
     * @see #ipAddressStr
     *
     * @param ipAddressStr
     */
    public void setIpAddressStr(String ipAddressStr)
    {
        this.ipAddressStr = ipAddressStr;
    }


    /**
     * Accessor.
     * @see #ipAddressStr
     *
     * @return
     */
    public String getIpAddressStr()
    {
        return ipAddressStr;
    }


    /**
     * Mutator.
     * @see #lFrame
     *
     * @param lFrame
     */
    public void setlFrame(Bitmap lFrame)
    {
        this.lFrame = lFrame;
    }


    /**
     * Accessor.
     * @see #lFrame
     *
     * @return
     */
    public Bitmap getlFrame()
    {
        return lFrame;
    }


    /**
     * Mutator.
     * @see #rFrame
     *
     * @param rFrame
     */
    public void setRFrame(Bitmap rFrame)
    {
        this.rFrame = rFrame;
    }


    /**
     * Accessor.
     * @see #rFrame
     *
     * @return
     */
    public Bitmap getRFrame()
    {
        return rFrame;
    }


    /**
     * Mutator.
     * @see #activity
     *
     * @param activity
     */
    public void setActivity(Activity activity)
    {
        this.activity = activity;
    }


    /**
     * Accessor.
     * @see #activity
     *
     * @return
     */
    public Activity getActivity()
    {
        return activity;
    }


    /**
     * Mutator.
     * @see #cameraOptions
     *
     * @param cameraOptions
     */
    public void setCameraOptions(CameraOptions cameraOptions)
    {
        this.cameraOptions = cameraOptions;
    }


    /**
     * Accessor.
     * @see #cameraOptions
     *
     * @return
     */
    public CameraOptions getCameraOptions()
    {
        return cameraOptions;
    }


    /**
     * Closes the socket.
     */
    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * TODO: implement this with relevant data.
     *
     * @return
     */
    public String toString()
    {
        return "^^^*** METHOD STUB ***^^^";
    }

}// End of class FetchLRFrames