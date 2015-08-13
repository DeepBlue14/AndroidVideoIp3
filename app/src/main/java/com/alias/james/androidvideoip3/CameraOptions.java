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





    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



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
        ImageButton leftBtn = (ImageButton) view.findViewById(R.id.left_camera);
        ImageButton rightBtn = (ImageButton) view.findViewById(R.id.right_camera);

        if(lFrame != null)
        {
            leftBtn.setImageBitmap(lFrame);
        }

        if(rFrame != null)
        {
            rightBtn.setImageBitmap(rFrame);
        }


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


        return view;
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
