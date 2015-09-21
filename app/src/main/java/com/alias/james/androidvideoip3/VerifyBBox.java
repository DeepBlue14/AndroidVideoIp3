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
    private AlertDialog.Builder verifyDialog; /** Is the dialog which will ask the user if the the object with a bounding-box is the correct one. */
    private static Bitmap bBoxBitmap; /** Is the image with the selected object surrounded by a bounding-box. */
    private ImageView imageView; /** Is the imageview that the bitmap bBoxBitmap will be stored it. */
    private DataCom dataCom; /** Is the class which handles robot/Android IP traffic after a initial location has been selected by the user. */


    /**
     * Initializes the VerifyBBox dialog.
     *
     * @param activity
     * @param layoutInflater
     * @param dataCom
     */
    public void initVerifyDialog(Activity activity, LayoutInflater layoutInflater, final DataCom dataCom)
    {
        LayoutInflater inflater = layoutInflater;
        final View view = inflater.inflate(R.layout.verify_bbox, null);
        imageView = (ImageView) view.findViewById(R.id.verify_image);
        bBoxBitmap = DataCom.getlFrame();
        imageView.setImageBitmap(bBoxBitmap);
        this.dataCom = dataCom;

        verifyDialog = new AlertDialog.Builder(activity)
                .setTitle("at VerifyBBox")
                .setMessage("Is this the correct object?")
                .setView(view)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Correct obj");
                        DataCom.SendYesOrNo sendYesOrNo = dataCom.genSendYesOrNo();
                        sendYesOrNo.setYOrNMsg("Y");
                        sendYesOrNo.execute(5, 5, 5);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("Correct obj");
                        DataCom.SendYesOrNo sendYesOrNo = dataCom.genSendYesOrNo();
                        sendYesOrNo.setYOrNMsg("N");
                        sendYesOrNo.execute(5, 5, 5);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);

    }


    /**
     * Displays the dilaog
     */
    public void show()
    {
        verifyDialog.show();
    }


    /**
     * Mutator.
     * @see #verifyDialog
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
     * @see #bBoxBitmap
     *
     * @param bBoxBitmap
     */
    public void setbBoxBitmap(Bitmap bBoxBitmap)
    {
        this.bBoxBitmap = bBoxBitmap;
    }


    /**
     * Accessor.
     * @see #bBoxBitmap
     *
     * @return
     */
    public Bitmap getbBoxBitmap()
    {
        return bBoxBitmap;
    }


    /**
     * Mutator.
     * @see #imageView
     *
     * @param imageView
     */
    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }


    /**
     * Accessor.
     * @see #imageView
     *
     * @return
     */
    public ImageView getImageView()
    {
        return imageView;
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
     * TODO: implement this.
     *
     * @return
     */
    public String toString()
    {
        return "^^^*** METHOD STUB ***^^^";
    }
}
