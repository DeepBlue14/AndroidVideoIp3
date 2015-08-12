/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class handles administrator login.
 *
 * References:
 *
 * Last Modified: 8/11/2015
 */

package com.alias.james.androidvideoip3;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

public class Login
{
    private AlertDialog.Builder loginDialog;

    public void initLoginDialog(Activity activity, LayoutInflater layoutInflater)
    {
        LayoutInflater inflater = layoutInflater;
        final View view = inflater.inflate(R.layout.login, null);

        loginDialog = new AlertDialog.Builder(activity)
                .setTitle("Frankenscooter UI")
                .setMessage("Welcome to Frankenscooter UI")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*
                        EditText userName = (EditText) view.findViewById(R.id.ip_address);
                        EditText password = (EditText) view.findViewById(R.id.port);

                        if(!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty() )
                        {
                            ;
                        }
                        */
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("\"Cancel\" alert button selected");
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
                //.show();
    }


    //helper methods



}
