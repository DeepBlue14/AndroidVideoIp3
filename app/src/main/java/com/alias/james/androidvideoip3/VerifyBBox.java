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
    private AlertDialog.Builder verifyDialog; /**  */
    private static Bitmap bBoxBitmap; /**  */
    private ImageView imageView; /**  */
    private DataCom dataCom; /**  */



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
     *
     *
     * @param verifyDialog
     */
    public void setVerifyDialog(AlertDialog.Builder verifyDialog)
    {
        this.verifyDialog = verifyDialog;
    }


    /**
     *
     *
     * @return
     */
    public AlertDialog.Builder getVerifyDialog()
    {
        return verifyDialog;
    }


    /**
     *
     *
     * @param bBoxBitmap
     */
    public void setbBoxBitmap(Bitmap bBoxBitmap)
    {
        this.bBoxBitmap = bBoxBitmap;
    }


    /**
     *
     *
     * @return
     */
    public Bitmap getbBoxBitmap()
    {
        return bBoxBitmap;
    }


    /**
     *
     *
     * @param imageView
     */
    public void setImageView(ImageView imageView)
    {
        this.imageView = imageView;
    }


    /**
     *
     *
     * @return
     */
    public ImageView getImageView()
    {
        return imageView;
    }


    /**
     *
     *
     * @param dataCom
     */
    public void setDataCom(DataCom dataCom)
    {
        this.dataCom = dataCom;
    }



    public DataCom getDataCom()
    {
        return dataCom;
    }



    public String toString()
    {
        return "^^^*** METHOD STUB ***^^^";
    }
}
