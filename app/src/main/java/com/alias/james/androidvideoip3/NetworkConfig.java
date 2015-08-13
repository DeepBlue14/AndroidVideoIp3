/*
 * File:   NetworkConfig.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This class allows an user/administrator to enter the ip address, port, and
 *                   ROS topics that the program should be listening to.  It also will display
 *                   a list of the computers available on the network.
 *
 * References: See AndroidVideoIp2 mainActivity.java
 *             http://stackoverflow.com/questions/2586301/set-inputtype-for-an-edittext
 *             http://stackoverflow.com/questions/2784081/android-create-spinner-programmatically-from-array
 *
 *             (for backend)
 *             nmap -sP 10.0.4.1/24 #displays all computers on the network
 *             nmap -PN 10.0.4.6 #displays open ports on the specified computer
 *             https://www.digitalocean.com/community/tutorials/how-to-use-nmap-to-scan-for-open-ports-on-your-vps
 *             http://stackoverflow.com/questions/478898/how-to-execute-a-command-and-get-output-of-command-within-c
 *
 *
 * Last Modified: 8/11/2015
 */

package com.alias.james.androidvideoip3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class NetworkConfig
{
    private String ipAddressStr = "10.0.4.6";
    private int portInt = 8080;
    private String leftCamTopic = "/usb_cam1/image_raw";
    private String rightCamTopic = "/usb_cam2/image_raw";
    private String camUrlStr = "http:10.0.4.6:8080/stream_viewer?topic=/camera/rgb/image_rect_color";
    private AlertDialog.Builder networkDialog;


    public void initNetworkConfigDialog(Activity activity, LayoutInflater layoutInflater)
    {
        LayoutInflater inflater = layoutInflater;
        final View view = inflater.inflate(R.layout.network_config, null);

        networkDialog = new AlertDialog.Builder(activity)
            .setTitle("Frankenscooter UI")
                .setMessage("Welcome to Frankenscooter UI")
                .setView(view)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText userName = (EditText) view.findViewById(R.id.ip_address);
                        EditText password = (EditText) view.findViewById(R.id.port);

                        if (!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                            ipAddressStr = userName.getText().toString();
                            portInt = Integer.parseInt(password.getText().toString());
                        }
                        setUrl(ipAddressStr, portInt);
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("\"Cancel\" alert button selected");
                        //System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert);
    }


    private void setUrl(String ipAddressStr, int portInt)
    {
        camUrlStr =  "http:" + ipAddressStr + ":" + portInt + "/stream_viewer?topic=/camera/rgb/image_rect_color";
    }


    public AlertDialog.Builder getNetworkDialog()
    {
        return networkDialog;
    }


}
