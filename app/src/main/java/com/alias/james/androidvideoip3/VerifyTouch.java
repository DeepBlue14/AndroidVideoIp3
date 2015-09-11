/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class displays a image of the object with a bounding box around it, and
 *                   asks the user if this was the correct object.
 *
 * References:
 *
 * Last Modified:8/14/15
 */


package com.alias.james.androidvideoip3;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class VerifyTouch
{
    private AlertDialog.Builder verifyDialog;


    public void initVerifyDialog(Activity activity, LayoutInflater layoutInflater)
    {
        LayoutInflater inflater = layoutInflater;
        final View view = inflater.inflate(R.layout.verify_touch, null);

        verifyDialog = new AlertDialog.Builder(activity)
                .setTitle("at VerifyTouch")
                .setMessage("Send point for evaluation?")
                .setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Send coors");
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
