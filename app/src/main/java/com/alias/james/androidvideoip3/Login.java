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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class Login
{
    private AlertDialog.Builder loginDialog;
    private MenuItem adminCb;

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

                        String userNameStr = ((EditText) view.findViewById(R.id.username)).getText().toString();
                        String passwordStr = ((EditText) view.findViewById(R.id.password)).getText().toString();

                        if (userNameStr.equals("h") && passwordStr.equals("r")) {
                            if (adminCb.isChecked() == true) {
                                adminCb.setChecked(false); // log out
                            } else {
                                adminCb.setChecked(true); // log in
                            }
                        }


                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("\"Cancel\" alert button selected");
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
                //.show();
    }


    public void show(MenuItem checkbox)
    {
        adminCb = checkbox;
        loginDialog.show();
    }


    //helper methods



}
