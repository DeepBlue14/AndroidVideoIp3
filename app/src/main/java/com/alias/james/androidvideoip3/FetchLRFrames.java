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
    private Socket socket;
    private String ipAddressStr = "10.0.4.6";
    private int port = 50000;
    private final int MATRIX_SIZE = 921600;
    private Bitmap lFrame;
    private Bitmap rFrame;
    Activity activity;
    CameraOptions cameraOptions;


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
        cameraOptions.setLRFrames(getlFrame(), getRFrame());
        cameraOptions.setArguments(activity.getIntent().getExtras());
    }


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

            //bytesRead = sub.read(buffer, 0, buffer.length);
            //currPos = bytesRead;
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


        cameraOptions.setLRFrames(getlFrame(), getRFrame());

        return null;
    }


    @Override
    protected void onPostExecute(Long aLong)
    {
        super.onPostExecute(aLong);
        cameraOptions.updateButtons(getlFrame(), getRFrame());
    }

    public CameraOptions getCameraOptions()
    {
        return cameraOptions;
    }


    public Bitmap getlFrame()
    {
        return lFrame;
    }


    public Bitmap getRFrame()
    {
        return rFrame;
    }


    public void close()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}