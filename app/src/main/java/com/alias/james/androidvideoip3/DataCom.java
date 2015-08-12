/*
 * File:   Transmitter.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class contains protocols TCP/IP data transfer between the app and the
 *                   robot.
 *
 * Reference: http://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/
 * Note: Look up TLS and SSL
 *
 * Created July 17, 2015 at 8:00pm
 */


package com.alias.james.androidvideoip3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class DataCom extends Activity
{
    private Socket socket;

    private static final int SERVERPORT = 50000;
    //private String ipAddressStr = "10.0.2.2";
    private String ipAddressStr = "10.0.4.6"; // robot-lab6

    private byte[] masterbytes;
    private Bitmap imgSegment;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        System.out.println("at Transmitter::onCreate(...) (about to start new ClientThread");
        super.onCreate(savedInstanceState);

        new Thread(new CommunicationThread()).start();
    }


    //Since this is not a main Activity, onCreate will not be called; thus this function must
    //be manually called, and serves as a "shaddow" (or perhaps "counterfeit") onCreate,
    public void onCreateShaddow()
    {
        System.out.println("at Transmitter::onCreateShaddow(...) (about to start new ClientThread");
        new Thread(new CommunicationThread()).start();
    }


    public void sendMsg(String msg)
    {
        try {
            System.out.println("Sending message...");
            String myMsg = "Hello Robot";
            //myMsg = msg;
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            out.println(myMsg);
            System.out.println("Message has been sent");




        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }catch (IOException e)
        {
            e.printStackTrace();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    class CommunicationThread implements Runnable
    {
        @Override
        public void run() {
            System.out.println("at ClientThread::run()");
            try
            {
                System.out.println("Setting up socket (main program)");
                InetAddress serverAddr = InetAddress.getByName(ipAddressStr);
                socket = new Socket(serverAddr, SERVERPORT);

                //--------------------
                //BufferedReader input;
                //input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                InputStream in = socket.getInputStream();

                while(!Thread.currentThread().isInterrupted()) {
                    //String str = input.readLine();
                    //System.out.println("Android heard: " + str);

                    //^^^^^^^^^^^^^^^^^^^^^^^^^^

                    byte[] b = new byte[4];
                    in.read(b, 0, 4);
                    String s = new String(b);
                    int i =  Integer.parseInt(s);
                    System.out.println("***I await your comming***: " + s);

                    byte[] buffer = new byte[i];
                    int currPos = 0;
                    int bytesRead = 0;

                    bytesRead = in.read(buffer, 0, buffer.length);
                    currPos = bytesRead;

                    do {
                        bytesRead = in.read(buffer, currPos, (buffer.length - currPos));

                        if(bytesRead != -1 && bytesRead != 0)
                        {
                            currPos += bytesRead;
                        }
                        else
                        {
                            break;
                        }
                        //System.out.println("recieving data...");
                    }while (bytesRead != -1 && bytesRead != 0);
                    //^^^^^^^^^^^^^^^^^^^^^^^^^^
                    System.out.println("Finished reading image dat: " + buffer.length);
                    masterbytes = buffer;
                    imgSegment = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);




                }

                //--------------------

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }//end if inner class ClientThread


    public byte[] getMasterbytes()
    {
        return masterbytes;
    }


    public Bitmap getImgSegment()
    {
        return imgSegment;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}