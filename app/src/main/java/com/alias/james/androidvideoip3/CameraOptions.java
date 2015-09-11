/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class handles the options page, which allows the user to choose the
 *                   left or right camera.
 *
 * References:
 *
 * Last Modified: 8/11/2015
 */

package com.alias.james.androidvideoip3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.support.v4.app.Fragment;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;


public class CameraOptions extends Fragment
{
    String[] cameraArr = {"Left Camera", "Right Camera"};
    OnCameraSelectedListener mCallback;
    Bitmap lFrame;
    Bitmap rFrame;
    ImageButton leftBtn;
    ImageButton rightBtn;


    public CameraOptions()
    {
        ;
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        //System.out.println("^^^HERE (1)");
        super.onCreate(savedInstanceState);
        //System.out.println("^^^HERE (2)");


    }


    public void setLRFrames(Bitmap lFrame, Bitmap rFrame)
    {
        this.lFrame = lFrame;
        this.rFrame = rFrame;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.camera_options, container, false);
        leftBtn = (ImageButton) view.findViewById(R.id.left_camera);
        rightBtn = (ImageButton) view.findViewById(R.id.right_camera);

        /*if(lFrame != null)
        {
            leftBtn.setImageBitmap(lFrame);
        }

        if(rFrame != null)
        {
            rightBtn.setImageBitmap(rFrame);
        }*/
        updateButtons(lFrame, rFrame);


        leftBtn.setOnTouchListener(new ImageButton.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Left button selected");

                mCallback.onImgSelected(0);

                return true;
            }
        });


        rightBtn.setOnTouchListener(new ImageButton.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Right button selected");

                mCallback.onImgSelected(1);

                return true;
            }
        });

        //System.out.println("^^^HERE (7)");
        return view;
    }


    public void updateButtons(Bitmap lFrame, Bitmap rFrame)
    {
        setLRFrames(lFrame, rFrame);

        if(lFrame != null)
        {
            leftBtn.setImageBitmap(lFrame);
            leftBtn.setClickable(true);
        }
        else
        {
            leftBtn.setClickable(false);
        }

        if(rFrame != null)
        {
            rightBtn.setImageBitmap(rFrame);
            rightBtn.setClickable(true);
        }
        else
        {
            rightBtn.setClickable(false);
        }
    }


    public ImageButton getRightBtn()
    {
        return rightBtn;
    }


    public ImageButton getLeftBtn()
    {
        return leftBtn;
    }



    @Override
    public void onStart()
    {
        super.onStart();


    }


    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            mCallback = (OnCameraSelectedListener) activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "must implement OnCameraSelectionListener");
        }
    }


}
