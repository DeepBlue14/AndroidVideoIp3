/*
 * File:   SecondFrag.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class displays the data stream from a camera, and acts as the base UI
 *                   during most of the execution.
 *
 * Reference:
 *
 * Created July 17, 2015 at 8:00pm
 */


package com.alias.james.androidvideoip3;


import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.w3c.dom.Text;

public class VideoFrag extends Fragment implements WebView.OnTouchListener, TextToSpeech.OnInitListener
{
    VerifyTouch verifyTouch = new VerifyTouch();
    private DataCom dataCom = new DataCom();
    private TextToSpeech tts;
    private WebView webView;
    private float fingerPressX = 0;
    private float fingerPressY = 0;
    private float fingerReleaseX = 0;
    private float fingerReleaseY = 0;
    private String ipAddressStr = "10.0.4.6"; // robot-lab6
    private int portInt = 8080;
    //private String camUrlStr = "http:10.0.2.2:8080/stream_viewer?topic=/camera/rgb/image_rect_color";
    private String camUrlStr = "http:10.0.4.6:8080/stream_viewer?topic=/camera/rgb/image_rect_color"; // robot-lab6

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.camera_layout, container, false);
        //verifyTouch.initVerifyDialog(getActivity(), inflater);
        tts = new TextToSpeech(getActivity(), this);
        webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setOnTouchListener(this);

        startWebCamStream();

        return view;
    }





    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        int action = event.getAction();




        switch(action)
        {
            case MotionEvent.ACTION_DOWN:
                fingerPressX = event.getX();
                fingerPressY = event.getY();
                System.out.println("ACTION_DOWN at: (" + event.getX() + ", " + event.getY() + ")");
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                fingerReleaseX = event.getX();
                fingerReleaseY = event.getY();
                System.out.println("ACTION_UP at: (" + event.getX() + ", " + event.getY() + ")");
                if(isWithinMargin(fingerPressX, fingerReleaseX, 25.0f) && isWithinMargin(fingerPressY, fingerReleaseY, 25.0f) )
                {
                    //verifyTouch.show();
                    //alertDialog.show(); //***James was here***
                    //dataCom.sendMsg("Msg: |" + fingerPressX + "|" + fingerPressY + "|"); // ***James was here***
                    //alertDialog.show(); //***James was here***
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                System.out.println("ACTION_CANCEL");
                break;
            default:
                System.out.println("default");
        }

        return false;
    }


    public boolean isWithinMargin(float pressInt, float releaseInt, float margin)
    {
        if(Math.abs(pressInt - releaseInt) - margin < 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    @Override
    public void onInit(int status)
    {
        if(tts == null)
        {
            tts = new TextToSpeech(getActivity(), this);
        }

        if(status == TextToSpeech.SUCCESS)
        {
             speakOut();

        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void speakOut()
    {
        String text = "Testing";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    public void setCamUrlStr(String ipAddressStr, int portInt)
    {
        camUrlStr = "http:" + ipAddressStr + ":" + portInt + "/stream_viewer?topic=/camera/rgb/image_rect_color";
    }


    public void startWebCamStream()
    {
        webView.loadUrl(camUrlStr);
    }


    public void stopWebCamStream()
    {
        webView.stopLoading();
    }


    @Override
    public void onDestroy()
    {
        super.onDestroy();
        tts.shutdown();
    }
}
