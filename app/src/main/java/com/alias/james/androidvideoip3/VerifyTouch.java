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
    private AlertDialog.Builder verifyDialog; /**  */
    private DataCom dataCom; /**  */
    private String msg; /** */


    /**
     * This method initializes and customizes the dialog.
     *
     * @param activity
     * @param layoutInflater
     * @param msg
     * @param dataCom
     */
    public void initVerifyDialog(Activity activity, LayoutInflater layoutInflater, final String msg, final DataCom dataCom)
    {
        LayoutInflater inflater = layoutInflater;
        this.dataCom = dataCom;
        this.msg = msg;
        dataCom.setUiStuff(activity, layoutInflater);

        final View view = inflater.inflate(R.layout.verify_touch, null);

        verifyDialog = new AlertDialog.Builder(activity)
                .setTitle("at VerifyTouch")
                .setMessage("Send point for evaluation?")
                .setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Send coors");
                        DataCom.Fetch myFetcher = dataCom.genFetch();
                        myFetcher.setMsg(msg);
                        myFetcher.execute(5, 5, 5);
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


    /**
     * This is a pass-through method which calls show() on the dialog UI
     */
    public void show()
    {
        verifyDialog.show();
    }


    /**
     * Mutator.
     * @see #verifyDialog
     *
     *
     * @param verifyDialog
     */
    public void setVerifyDialog(AlertDialog.Builder verifyDialog)
    {
        this.verifyDialog = verifyDialog;
    }


    /**
     * Accessor.
     * @see #verifyDialog
     *
     * @return
     */
    public AlertDialog.Builder getVerifyDialog()
    {
        return verifyDialog;
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
     * Mutator.
     * @see #msg
     *
     * @param msg
     */
    public void setMsg(String msg)
    {
        this.msg = msg;
    }


    /**
     * Accessor.
     * @see #msg
     *
     * @return
     */
    public String getMsg()
    {
        return msg;
    }


    /**
     * TODO: implement this.
     *
     * @return
     */
    public String toString()
    {
        return "^^^*** METHOD STUB ***^^^";
    }



} // End of class VerifyTouch
