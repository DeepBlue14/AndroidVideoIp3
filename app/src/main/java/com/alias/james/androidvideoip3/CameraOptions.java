/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class handles the options page, which allows the user to choose the
 *                   left or right camera.
 *
 * References:
 *
 * Last Modified: 9/18/2015
 */

package com.alias.james.androidvideoip3;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class CameraOptions extends Fragment
{
    private String[] cameraArr = {"Left Camera", "Right Camera"};/** Array of cameras. */
    private OnCameraSelectedListener cameraCallback; /** Callback object to handle events. */
    private Bitmap leftBitmap; /** Bitmap contained within left ImageButton. */
    private Bitmap rightBitmap; /** Bitmap contained within right ImageButton. */
    private ImageButton leftImgBtn; /** The left ImageButton. */
    private ImageButton rightImgBtn; /** The right ImageButton. */


    /**
     * Default onCreate method.  Calls onCreate() of superclass.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    /**
     * Initializes the both left and right camera image buttons.  It then calls updateButtons
     * to set each button's image.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState state of fragment from the last time it was invoked
     * @return the current View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.camera_options, container, false);
        leftImgBtn = (ImageButton) view.findViewById(R.id.left_camera);
        rightImgBtn = (ImageButton) view.findViewById(R.id.right_camera);

        updateButtons(leftBitmap, rightBitmap);


        leftImgBtn.setOnTouchListener(new ImageButton.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Left button selected");

                cameraCallback.onImgSelected(0);

                return true;
            }
        });


        rightImgBtn.setOnTouchListener(new ImageButton.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                System.out.println("Right button selected");

                cameraCallback.onImgSelected(1); // !!!???Hard coded for camera 1???!!!

                return true;
            }
        });

        //System.out.println("^^^HERE (7)");
        return view;
    }


    /**
     * Sets an image to each button.  If a valid bitmap associated with a button's corrisponding
     * camera can be found, it is assigned that bitmap, otherwise it is assigned a
     * "no camera found" bitmap.
     *
     * @param leftBitmap the bitmap for the left camera options button.
     * @param rightBitmap the bitmap for the left camera options button.
     */
    public void updateButtons(Bitmap leftBitmap, Bitmap rightBitmap)
    {
        setLeftBitmap(leftBitmap);
        setRightBitmap(rightBitmap);

        if(leftBitmap != null)
        {
            leftImgBtn.setImageBitmap(leftBitmap);
            leftImgBtn.setEnabled(true);
        }
        else
        {
            leftImgBtn.setEnabled(false);
        }

        if(rightBitmap != null)
        {
            rightImgBtn.setImageBitmap(rightBitmap);
            rightImgBtn.setEnabled(true);
        }
        else
        {
            rightImgBtn.setEnabled(false);
        }
    }


    /**
     *
     * @param leftBitmap
     */
    public void updateLeftButton(Bitmap leftBitmap) {
        setLeftBitmap(leftBitmap);

        if(leftBitmap != null)
        {
            leftImgBtn.setImageBitmap(leftBitmap);
            leftImgBtn.setClickable(true);
        }
        else
        {
            leftImgBtn.setClickable(false);
        }
    }


    /**
     *
     *
     * @param rightBitmap
     */
    public void updateRightButton(Bitmap rightBitmap)
    {
        if(rightBitmap != null)
        {
            rightImgBtn.setImageBitmap(rightBitmap);
            rightImgBtn.setClickable(true);
        }
        else
        {
            rightImgBtn.setClickable(false);
        }
    }


    /**
     * Calls onStart() method of superclass.
     */
    @Override
    public void onStart()
    {
        super.onStart();
    }


    /**
     * Attempt to cast cameraCallback.  This will fail if OnCameraSelectionListener has not
     * been implemented.
     * @see #cameraCallback
     *
     * @param activity
     */
    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);

        try
        {
            cameraCallback = (OnCameraSelectedListener) activity;
        }catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() + "must implement OnCameraSelectionListener");
        }
    }


    // Accessor and mutator methods

    /**
     * Mutator method to assign member variable.
     * @see #cameraArr
     *
     * @param cameraArr
     */
    public void setCameraArr(String[] cameraArr)
    {
        this.cameraArr = cameraArr;
    }


    /**
     * Accessor.
     * @see #cameraArr
     *
     * @return cameraArr;
     */
    public String[] getCameraArr()
    {
        return cameraArr;
    }


    /**
     * Mutator method to assign member variable.
     * @see #cameraCallback
     *
     * @param cameraCallback
     */
    public void setCameraCallback(OnCameraSelectedListener cameraCallback)
    {
        this.cameraCallback = cameraCallback;
    }


    /**
     * Accessor.
     * @see #cameraCallback
     *
     * @return cameraCallback
     */
    public OnCameraSelectedListener getCameraCallback()
    {
        return cameraCallback;
    }


    /**
     * Mutator method to assign member variable.
     * @see #leftBitmap
     *
     * @param leftBitmap
     */
    public void setLeftBitmap(Bitmap leftBitmap)
    {
        this.leftBitmap = leftBitmap;
    }


    /**
     * Accessor.
     * @see #leftBitmap
     *
     * @return leftBitmap
     */
    public Bitmap getLeftBitmap()
    {
        return leftBitmap;
    }


    /**
     * Mutator.
     * @see #rightBitmap
     *
     * @param rightBitmap
     */
    public void setRightBitmap(Bitmap rightBitmap)
    {
        this.rightBitmap = rightBitmap;
    }


    /**
     * Accessor.
     * @see #rightBitmap
     *
     * @return rightBitmap
     */
    public Bitmap getRightBitmap()
    {
        return rightBitmap;
    }


    /**
     * Mutator.
     * @see #rightImgBtn
     *
     * @param rightImgBtn
     */
    public void setRightImgBtn(ImageButton rightImgBtn)
    {
        this.rightImgBtn = rightImgBtn;
    }


    /**
     * Mutator.
     * @see #rightImgBtn
     *
     * @return rightImgBtn
     */
    public ImageButton getRightImgBtn()
    {
        return rightImgBtn;
    }


    /**
     * Mutator.
     * @see #leftImgBtn
     *
     * @param leftImgBtn
     */
    public void setLeftImgBtn(ImageButton leftImgBtn)
    {
        this.leftImgBtn = leftImgBtn;
    }


    /**
     * Accessor.
     * @see #leftImgBtn
     *
     * @return leftImgBtn
     */
    public ImageButton getLeftImgBtn()
    {
        return leftImgBtn;
    }


    /**
     * TODO: implement this method with relevent data.
     *
     * @return
     */
    public String toString()
    {
        return "^^^***empty toString() method***^^^";
    }


}// End of class CameraOptions