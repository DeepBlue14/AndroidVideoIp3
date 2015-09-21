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
 * <p>
 *             (for backend)
 *             nmap -sP 10.0.4.1/24 #displays all computers on the network
 *             nmap -PN 10.0.4.6 #displays open ports on the specified computer
 *             https://www.digitalocean.com/community/tutorials/how-to-use-nmap-to-scan-for-open-ports-on-your-vps
 *             http://stackoverflow.com/questions/478898/how-to-execute-a-command-and-get-output-of-command-within-c
 * </p>
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
    private String ipAddressStr = "10.0.4.6"; /** Is the IP address of the robot.  Currently hard-coded to James K's lab machine robot-lab6. */
    private int portInt = 8080; /** Is the port that the video stream will be sent to. */
    private String leftCamTopic = "/usb_cam1/image_raw"; /** Is the ROS topic that the left camera will stream to. */
    private String rightCamTopic = "/usb_cam2/image_raw"; /** Is the ROS topic that the right camera will stream to. */
    private String camUrlStr = "http:10.0.4.6:8080/stream_viewer?topic=/camera/rgb/image_rect_color"; /** Is the HTTP address the robot will stream video to. */
    private AlertDialog.Builder networkDialog; /** Is the dialog which will present networking options (if the user is logged in as administrator. */


    /**
     * Initializes the network configuration dialog (which can only be accessed by
     * an administrator).
     *
     * @param activity
     * @param layoutInflater
     */
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

                        if (!userName.getText().toString().isEmpty() && !password.getText().toString().isEmpty() ) {
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


    /**
     * Mutator.
     * @see #ipAddressStr
     *
     * @param ipAddressStr
     */
    public void setIpAddressStr(String ipAddressStr)
    {
        this.ipAddressStr = ipAddressStr;
    }


    /**
     * Accessor.
     * @see #ipAddressStr
     *
     * @return
     */
    public String getIpAddressStr()
    {
        return ipAddressStr;
    }


    /**
     * Mutator.
     * @see #portInt
     *
     * @param portInt
     */
    public void setPortInt(int portInt)
    {
        this.portInt = portInt;
    }


    /**
     * Accessor.
     * @see #portInt
     *
     * @return
     */
    public int getPortInt()
    {
        return portInt;
    }


    /**
     * Mutator.
     * @see #leftCamTopic
     *
     * @param leftCamTopic
     */
    public void setLeftCamTopic(String leftCamTopic)
    {
        this.leftCamTopic = leftCamTopic;
    }


    /**
     * Accessor.
     * @see #leftCamTopic
     *
     * @return
     */
    public String getLeftCamTopic()
    {
        return leftCamTopic;
    }


    /**
     * Mutator.
     * @see #rightCamTopic
     *
     * @param rightCamTopic
     */
    public void setRightCamTopic(String rightCamTopic)
    {
        this.rightCamTopic = rightCamTopic;
    }


    /**
     * Accessor.
     * @see #rightCamTopic
     *
     * @return
     */
    public String getRightCamTopic()
    {
        return rightCamTopic;
    }


    /**
     * Mutator.
     * @see #camUrlStr
     *
     * @param camUrlStr
     */
    public void setCamUrlStr(String camUrlStr)
    {
        this.camUrlStr = camUrlStr;
    }

    /**
     * Accessor.
     * @see #camUrlStr
     *
     * @return
     */
    public String getCamUrlStr()
    {
        return camUrlStr;
    }

    /**
     * Mutator.
     * @see #networkDialog
     *
     * @param networkDialog
     */
    public void setNetworkDialog(AlertDialog.Builder networkDialog)
    {
        this.networkDialog = networkDialog;
    }


    /**
     * Accessor.
     * @see #networkDialog
     *
     * @return
     */
    public AlertDialog.Builder getNetworkDialog()
    {
        return networkDialog;
    }


    /**
     * Concatinate the ipaddress and port in order to set up the http address.
     * @see #ipAddressStr
     * @see #portInt
     *
     * @param ipAddressStr
     * @param portInt
     */
    private void setUrl(String ipAddressStr, int portInt)
    {
        camUrlStr =  "http:" + ipAddressStr + ":" + portInt + "/stream_viewer?topic=/camera/rgb/image_rect_color";
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


} // End of class NetworkConfig
