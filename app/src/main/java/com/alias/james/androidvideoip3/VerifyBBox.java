/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class displays a image of the object with a bounding box around it, and
 *                   asks the user if this was the correct object.
 *
 * References:
 *
 * Last Modified: 8/13/2015
 */

package com.alias.james.androidvideoip3;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class VerifyBBox
{
    private AlertDialog.Builder verifyDialog;
    private static Bitmap bBoxBitmap;
    private ImageView imageView;


    public void initVerifyDialog(Activity activity, LayoutInflater layoutInflater)
    {
        LayoutInflater inflater = layoutInflater;
        final View view = inflater.inflate(R.layout.verify_bbox, null);
        imageView = (ImageView) view.findViewById(R.id.verify_image);
        bBoxBitmap = DataCom.getlFrame();
        imageView.setImageBitmap(bBoxBitmap);

        verifyDialog = new AlertDialog.Builder(activity)
                .setTitle("at VerifyBBox")
                .setMessage("Is this the correct object?")
                .setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Correct obj");
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

    }


    public void show()
    {
        verifyDialog.show();
    }






}
