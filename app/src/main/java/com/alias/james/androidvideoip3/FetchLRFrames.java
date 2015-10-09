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


public class FetchLRFrames extends AsyncTask<Integer, Integer, Long> // !!!wrap this in a outer class!!!
{
    private Socket leftCamSocket; /** To connect to robot's left camera server. */
    private Socket rightCamSocket; /** To connect to robot's right camera server. */
    //private String ipAddressStr = "10.0.4.6"; /** IP address of robot host.  Currently hard-coded to robot-lab6 (James Kuczynski's computer). */
    //private String ipAddressStr = "129.63.17.97"; /** IP address of robot host.  Currently hard-coded to robot-lab6 (James Kuczynski's computer). */
    //private int leftCamPort = 50000; /** Is the port used by the left camera. */
    //private int rightCamPort = 50002; /** Is the port used by the right camera. */
    private final int MATRIX_SIZE = 921600; /** Is the size of the OpenCV matrix sent. */
    private Bitmap lFrame; /** Is a bitmap created from the OpenCV matrix passed from the robot's left camera */
    private Bitmap rFrame; /** Is a bitmap created from the OpenCV matrix passed from the robot's right camera */
    private boolean isLFrameReal = false;
    private boolean isRFrameReal = false;
    private Activity activity; /** is a reference to the MainActivity's Activity object */
    private static CameraOptions cameraOptions; /** Is a reference to the fragment which allows the user to choose the left or right camera */
    private InetAddress serverAddress = null; /** Internet address of robot. */

    enum CameraLoc /** Type used to specify which camera should be used. */
    {
        LEFT,
        RIGHT
    }
    private CameraLoc cameraLoc = CameraLoc.LEFT; /** Variable used to specify which camera should be used. */



    /**
     * This constructor method initializes variables and loads the OpenCV JNI modules.
     *
     * @param resources
     * @param activity
     */
    public FetchLRFrames(Resources resources, Activity activity, CameraLoc cameraLoc) //Resources from Activity
    {
        System.out.println("^^^Starting FetchLRFrames...^^^");
        this.activity = activity;
        this.cameraLoc = cameraLoc;

        if(!OpenCVLoader.initDebug() )
        {
            System.err.println("^^^Failed to load OpenCV @ FetchLRFrames::FetchLRFrames()");
        }

        if(cameraLoc == CameraLoc.LEFT) {
            lFrame = BitmapFactory.decodeResource(resources, R.drawable.camera_off);
        }
        else if(cameraLoc == CameraLoc.RIGHT) {
            rFrame = BitmapFactory.decodeResource(resources, R.drawable.camera_off); //!!! other thread? !!!
        }

        if(cameraLoc == CameraLoc.LEFT)
        {
            System.out.println("^^^Generating new cameraOptions");
            cameraOptions = new CameraOptions();
        }

        if(cameraLoc == CameraLoc.LEFT)
        {
            System.out.println("^^^setting leftbitmap @ FetchLRFrames::doInBackground(...)");
            cameraOptions.setLeftBitmap(getlFrame());
        }
        else if(cameraLoc == CameraLoc.RIGHT)
        {
            System.out.println("^^^setting rightbitmap @ FetchLRFrames::doInBackground(...)");
            cameraOptions.setRightBitmap(getRFrame());
        }

        cameraOptions.setArguments(activity.getIntent().getExtras());
    }


    /**
     * Connects a socket to the appropriate server.
     */
    public void connect()
    {
        System.out.println("^^^@ connect()");
        try {
            serverAddress = InetAddress.getByName(UniversalDat.getIpAddressStr() );
            if(cameraLoc == CameraLoc.LEFT) {
                leftCamSocket = new Socket(serverAddress, UniversalDat.getLeftCamPort() );
            }
            else {
                rightCamSocket = new Socket(serverAddress, UniversalDat.getLeftCamPort() );
            }

        } catch (UnknownHostException e) {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        }catch (Exception e)
        {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        }

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
            if(serverAddress == null)
            {
                connect();
            }

            InputStream sub;
            if(cameraLoc == CameraLoc.LEFT && leftCamSocket != null)
            {
                sub = leftCamSocket.getInputStream();
            }
            else if(cameraLoc == CameraLoc.RIGHT && rightCamSocket != null)
            {
                sub = rightCamSocket.getInputStream();
            }
            else
            {
                sub = null;
            }

            if(sub != null) {

                System.out.println("^^^Generating new InputStream obj");
                // !!!???will this be true 32 & 64 bit processors???!!!
                byte[] buffer = new byte[MATRIX_SIZE];
                int currPos = 0;
                int bytesRead = 0;

                do {
                    bytesRead = sub.read(buffer, currPos, (buffer.length - currPos));

                    if (bytesRead != -1 && bytesRead != 0) {
                        currPos += bytesRead;
                    } else {
                        break;
                    }

                } while (true);

                System.out.println("^^^received image bytes:" + currPos);
                Mat mat = new Mat(480, 640, CvType.CV_8UC3);
                mat.put(0, 0, buffer);

                if (cameraLoc == CameraLoc.LEFT && currPos == 921600) {
                    lFrame = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(mat, lFrame);
                    System.out.println("^^^Here comes Fred's (left) students!");
                    isLFrameReal = true;
                } else if (cameraLoc == CameraLoc.RIGHT && currPos == 921600){
                    rFrame = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(mat, rFrame);
                    System.out.println("^^^Here comes Fred's (right) students!");
                    isRFrameReal = true;
                }



            }

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
        }catch (Exception e)
        {
            System.out.println("^^^Ousted");
            e.printStackTrace();
        }

        if(cameraLoc == CameraLoc.LEFT)
        {
            System.out.println("^^^setting leftbitmap @ FetchLRFrames::doInBackground(...)");
            cameraOptions.setLeftBitmap(getlFrame());
        }
        else if(cameraLoc == CameraLoc.RIGHT)
        {
            System.out.println("^^^setting rightbitmap @ FetchLRFrames::doInBackground(...)");
            cameraOptions.setRightBitmap(getRFrame());
        }



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
        System.out.println("^^^@ onPostExecute(...)");
        //cameraOptions.updateButtons(getlFrame(), getRFrame()); //!!! each AsyncTask should update it own button (exclusively) !!!
        if(cameraLoc == CameraLoc.LEFT)
        {
            cameraOptions.updateLeftButton(getlFrame(), isLFrameReal);
        }
        else if(cameraLoc == CameraLoc.RIGHT)
        {
            System.out.println("^^^I am the right button status: " + isRFrameReal);
            cameraOptions.updateRightButton(getRFrame(), isRFrameReal);
        }
    }


    /**
     * Mutator.
     * @see #cameraLoc
     *
     * @param cameraLoc
     */
    public void setCameraLoc(CameraLoc cameraLoc)
    {
        this.cameraLoc = cameraLoc;
    }


    /**
     * Accessor.
     * @see #cameraLoc
     *
     * @return
     */
    public CameraLoc getCameraLoc()
    {
        return cameraLoc;
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
     * Closes leftCamSocket.
     */
    public void closeLeftCamSocket()
    {
        try {
            leftCamSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Closes rightCamSocket
     */
    public void closeRightCamSocket()
    {
        try {
            rightCamSocket.close();
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