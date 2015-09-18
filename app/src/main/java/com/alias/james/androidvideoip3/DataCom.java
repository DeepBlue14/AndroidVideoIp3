/*
 * File:   DataCom.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class sends pixel points to the robot, which will send back a OpenCV
 *                   matrix with a bounding box around the object.
 *
 * Reference: http://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/
 * Note: Look up TLS and SSL
 *
 * Created July 17, 2015 at 8:00pm
 */


package com.alias.james.androidvideoip3;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

public class DataCom
{
    private Socket socket;
    private String m_msg;
    private static final int SERVER_PORT = 50001;
    private String ipAddressStr = "10.0.4.6"; // robot-lab6
    private final int MATRIX_SIZE = 921600;
    private Bitmap bBoxBitmap;
    Activity activity;
    InetAddress serverAddress = null;


    public DataCom() //Resources from Activity
    {
        System.out.println("^^^Starting DataCom...^^^");

        if (!OpenCVLoader.initDebug()) {
            System.err.println("^^^Failed to load OpenCV @ FetchLRFrames::FetchLRFrames()");
        }
    }


    public Fetch genFetch()
    {
        return new Fetch();
    }


    protected class Fetch extends AsyncTask<Integer, Integer, Long>
    {

        public void setMsg(String msg) {
            System.out.println("^^^Setting Msg");
            m_msg = msg;
        }


        public String getMsg() {
            return m_msg;
        }


        public void sendMsg(String msg) {
            try {
                System.out.println("^^^Sending message...");
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true); // ???Do this once???
                out.println(msg);
                System.out.println("^^^Message has been sent");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        private void connect() {
            System.out.println("^^^@ connect()");

            try {
                serverAddress = InetAddress.getByName(ipAddressStr);
                socket = new Socket(serverAddress, SERVER_PORT); //!!!connect in a separate method--same goes for FetchLRFrames!!!
                System.out.println("^^^Successfully connected to server^^^");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        @Override
        protected Long doInBackground(Integer... params) {
            try {
                if (serverAddress == null) {
                    System.out.println("^^^Connecting");
                    connect();
                }
                //InetAddress serverAddress = InetAddress.getByName(ipAddressStr);
                //socket = new Socket(serverAddress, SERVER_PORT); //!!!connect in a separate method--same goes for FetchLRFrames!!!
                //System.out.println("^^^Successfully connected to server^^^");

                sendMsg(getMsg());

                InputStream sub = socket.getInputStream(); // ???Do this once???
                System.out.println("^^^Generating new InputStream obj");
                // !!!???will this be true 32 & 64 bit processors???!!!
                byte[] buffer = new byte[MATRIX_SIZE];
                int currPos = 0;
                int bytesRead = 0;

                //bytesRead = sub.read(buffer, 0, buffer.length);
                //currPos = bytesRead;
                do {
                    bytesRead = sub.read(buffer, currPos, (buffer.length - currPos));

                    if (bytesRead != -1 && bytesRead != 0) {
                        currPos += bytesRead;
                    } else {
                        break;
                    }

                } while (bytesRead != -1 && bytesRead != 0);

                System.out.println("^^^recieved image bytes @ DataCom::doInBackground(...):" + currPos);
                Mat mat = new Mat(480, 640, CvType.CV_8UC3);
                mat.put(0, 0, buffer);
                bBoxBitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(mat, bBoxBitmap);
            } catch (UnknownHostException e) {
                System.out.println("^^^Ousted");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("^^^Ousted");
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
            //display popup "is this the object" with bounding-box

        }


        public Bitmap getlFrame() {
            return bBoxBitmap;
        }


        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}