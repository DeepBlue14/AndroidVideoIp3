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
    VerifyTouch verifyTouch = new VerifyTouch(); /** VerifyTouch dialog object. */
    private TextToSpeech tts; /** Is used for speech. */
    private WebView webView; /** Contains the video feed from the robot. */
    private float fingerPressX = 0; /** Is the x coordinate of the finger press action. */
    private float fingerPressY = 0; /** Is the y coordinate of the finger press action. */
    private float fingerReleaseX = 0; /** Is the x coordinate of the finger release action. */
    private float fingerReleaseY = 0; /** Is the y coordinate of the finger release action. */
    private String ipAddressStr = "10.0.4.6"; /** IP address of the robot, currently hard-coded to James K's lab machine: robot-lab6 */
    private String topic = "/camera/rgb/image_rect_color";
    private int portInt = 8080; /** Default port for the robot's video stream to use. */
    //private String camUrlStr = "http:10.0.2.2:8080/stream_viewer?topic=/camera/rgb/image_rect_color";
    private String camUrlStr = "http:10.0.4.6:8080/stream_viewer?topic=/camera/rgb/image_rect_color"; /** Default URL that of the robot's video feed. */
    LayoutInflater inflater; /** Is an inflater object to inflate the VerifyTouch dialog UI. */
    DataCom dataCom; /** Is used to handle transactions between the robot and Android device after an point has been initially selected. */


    /**
     *
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataCom = new DataCom();

    }


    /**
     * Initializes the UI components related to this fragment, especially setting up the WebView
     * settings.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     *
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.camera_layout, container, false);
        this.inflater = inflater;
        dataCom.setUiStuff(getActivity(), inflater);
        tts = new TextToSpeech(getActivity(), this);
        webView = (WebView) view.findViewById(R.id.web_view);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setOnTouchListener(this);

        startWebCamStream();

        return view;
    }


    /**
     * Is called when the user touches the video.  If the action is detected as a touch instead of
     * a slide
     * @see #isWithinMargin(float, float, float)
     * then a dialog appears asking the user if he wishes the location evaluated by the robot.
     *
     * @param v
     * @param event
     *
     * @return
     */
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
                    verifyTouch.initVerifyDialog(getActivity(), inflater, "|" + (int) fingerPressX + "|" + (int) fingerPressY + "|", dataCom);
                    verifyTouch.show();
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


    /**
     * Checks to see if the user event was a press or a slide.
     *
     * @param pressInt
     * @param releaseInt
     * @param margin
     *
     * @return
     */
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


    /**
     * Sets up the speech module.
     *
     * @param status
     */
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

        } else
        {
            Log.e("TTS", "Initilization Failed!");
        }
    }


    /**
     * When called, this method invokes speak(...).
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void speakOut()
    {
        String text = "Testing";
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    /**
     * Starts accepting the video stream.
     */
    public void startWebCamStream()
    {
        webView.loadUrl(camUrlStr);
    }


    /**
     * Stops accepting the video stream.
     */
    public void stopWebCamStream()
    {
        webView.stopLoading();
    }


    /**
     * Invokes the onDestroy of the superclass, and shuts down the speech module.
     */
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        tts.shutdown();
    }



    // Accessor and mutator methods


    /**
     * Mutator.
     * @see #verifyTouch
     *
     * @param verifyTouch
     */
    public void setVerifyTouch(VerifyTouch verifyTouch)
    {
        this.verifyTouch = verifyTouch;
    }


    /**
     * Accessor
     * @see #verifyTouch
     *
     * @return
     */
    public VerifyTouch getVerifyTouch()
    {
        return verifyTouch;
    }


    /**
     * Mutator.
     * @see #tts
     *
     * @param tts
     */
    public void setTts(TextToSpeech tts)
    {
        this.tts = tts;
    }


    /**
     * Accessor.
     * @see #tts
     *
     * @return
     */
    public TextToSpeech getTts()
    {
        return tts;
    }


    /**
     * Mutator.
     * @see #webView
     *
     * @param webView
     */
    public void setWebView(WebView webView)
    {
        this.webView = webView;
    }


    /**
     * Accessor.
     * @see #webView
     *
     * @return
     */
    public WebView getWebView()
    {
        return webView;
    }


    /**
     * Mutator.
     * @see #fingerPressX
     *
     * @param fingerPressX
     */
    public void setFingerPressX(float fingerPressX)
    {
        this.fingerPressX = fingerPressX;
    }


    /**
     * Accessor.
     * @see #fingerPressX
     *
     * @return
     */
    public float getFingerPressX()
    {
        return fingerPressX;
    }


    /**
     * Mutator.
     * @see #fingerPressY
     *
     * @param fingerPressY
     */
    public void setFingerPressY(float fingerPressY)
    {
        this.fingerPressY = fingerPressY;
    }


    /**
     * Accessor.
     * @see #fingerPressY
     *
     * @return
     */
    public float getFingerPressY()
    {
        return fingerPressY;
    }


    /**
     * Mutator.
     * @see #fingerReleaseX
     *
     * @param fingerReleaseX
     */
    public void setFingerReleaseX(float fingerReleaseX)
    {
        this.fingerReleaseX = fingerReleaseX;
    }


    /**
     * Accessor.
     * @see #fingerReleaseX
     *
     * @return
     */
    public float getFingerReleaseX()
    {
        return fingerReleaseX;
    }


    /**
     * Mutator.
     * @see #fingerReleaseY
     *
     * @param fingerReleaseY
     */
    public void setFingerReleaseY(float fingerReleaseY)
    {
        this.fingerReleaseY = fingerReleaseY;
    }


    /**
     * Accessor.
     * @see #fingerReleaseY
     *
     * @return
     */
    public float getFingerReleaseY()
    {
        return fingerReleaseY;
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
     * @see #topic
     *
     * @param topic
     */
    public void setTopic(String topic)
    {
        this.topic = topic;
    }


    /**
     * Accessor.
     * @see #topic
     *
     * @return
     */
    public String getTopic() {
        return topic;
    }


    /**
     * Mutator.
     * @see #portInt
     *
     * @param portInt
     */
    public void setPortInt(int portInt)
    {
        this.portInt = portInt;
    }


    /**
     * Accessor
     * @see #portInt
     *
     * @return
     */
    public int getPortInt()
    {
        return portInt;
    }


    /**
     * Mutator.
     * @see #ipAddressStr
     * @see #portInt
     *
     * @param ipAddressStr
     * @param portInt
     */
    public void setCamUrlStr(String ipAddressStr, int portInt, String topic)
    {
        camUrlStr = "http:" + ipAddressStr + ":" + portInt + "/stream_viewer?topic=" + topic;
    }


    /**
     * Accessor.
     * @see #camUrlStr
     *
     * @return
     */
    public String getCamUrlStr()
    {
        return camUrlStr;
    }


    /**
     * Mutator.
     * @see #inflater
     *
     * @param inflater
     */
    public void setInflater(LayoutInflater inflater)
    {
        this.inflater = inflater;
    }


    /**
     * Accessor.
     * @see #inflater
     *
     * @return
     */
    public LayoutInflater getInflater()
    {
        return inflater;
    }


    /**
     * Mutator.
     * @see #dataCom
     *
     * @param dataCom
     */
    public void setDataCom(DataCom dataCom)
    {
        this.dataCom = dataCom;
    }


    /**
     * Accessor.
     * @see #dataCom
     *
     * @return
     */
    public DataCom getDataCom()
    {
        return dataCom;
    }


    /**
     * TODO: impelement this.
     *
     * @return
     */
    public String toString()
    {
        return "^^^*** METHOD STUB ***^^^";
    }




} // End of class VideoFrag