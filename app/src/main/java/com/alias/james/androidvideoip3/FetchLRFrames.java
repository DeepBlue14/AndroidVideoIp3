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


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class FetchLRFrames
{
    private Socket socket;
    private String ipAddressStr = "10.0.4.6";
    private int port = 50000;
    private Bitmap lFrame;
    private Bitmap rFrame;


    public FetchLRFrames(Resources resources) //Resources from Activity
    {
        System.out.println("^^^Starting FetchLRFrames...^^^");
        lFrame = BitmapFactory.decodeResource(resources, R.drawable.camera);
        rFrame = BitmapFactory.decodeResource(resources, R.drawable.camera);

        new Thread(new ComThread()).start();
    }


    public void requestLFrame()
    {
        try
        {
            String request = "LF";
            PrintWriter pub = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            pub.println(request);
            System.out.println("^^^Left frame request successfully sent^^^");
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace(); //cant find server :(
        }
        catch (IOException e)
        {
            e.printStackTrace(); //error passing data.
        }
        catch (Exception e)
        {
            e.printStackTrace(); //basically, you are screwed.
        }
    }


    class ComThread implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                InetAddress serverAddress = InetAddress.getByName(ipAddressStr);
                socket = new Socket(serverAddress, port);
                System.out.println("^^^Successfully connected to server^^^");

                InputStream sub = socket.getInputStream();

                while(!Thread.currentThread().isInterrupted() )
                {
                    // !!!???will this be true 32 & 64 bit processors???!!!
                    byte[] imgSizeByteArr = new byte[4];
                    sub.read(imgSizeByteArr, 0, 4);
                    String sizeStr = new String(imgSizeByteArr);
                    int size = Integer.parseInt(sizeStr);

                    byte[] buffer = new byte[size];
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

                    System.out.println("^^^Successfully read image^^^");

                    lFrame = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                    System.out.println("^^^Successfully decoded byte array --> bitmap^^^");
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
            }


        }//end of method run()


        public void recv()
        {

        }


    }//end of inner class ComThead




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