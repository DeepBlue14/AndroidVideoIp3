/*
 * Project: AndroidVideoIp3
 * File:   MainActivity.java
 * Files: CameraOptions.java, DataCom.java, FetchLRFrames.java, Login.java, MainActivity.java,
 *        NetworkConfig.java, OnCameraSelectedListener.java, VerifyBBox.java, VerifyTouch.java,
 *        VideoFrag.java,
 *        Camera_off.jpg,
 *        activity_main.xml, camera_layout.xml, camera_options.xml, login.xml, network_config.xml,
 *        verify_bbox.xml, verify_touch.xml,
 *        menu_main.xml,
 *        strings.xml,
 *        styles.xml,
 *        AndroidManifest.xml
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * Project Description:
 * File Description: This is the main activity of the project.
 *
 * References: http://jimiz.net/2011/09/find-computers-network-nmap-free-tool/#axzz3iWOiNQn4
 *
 * Last Modified: 8/11/2015
 */

package com.alias.james.androidvideoip3;

import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends FragmentActivity implements OnCameraSelectedListener
{
    private Menu menu; /** The apps menu. */
    private FetchLRFrames fetchLeftFrame; /** Reference to the class which fetches a frame from the left camera (if it is connected). */
    private FetchLRFrames fetchRightFrame; /** Reference to the class which fetches a frame from the right camera (if it is connected). */
    private NetworkConfig networkConfig = new NetworkConfig(); /** Is a reference to the class which handles the administrator networking options. */
    private Login login = new Login(); /** Contains the login dialog required if a user wishes to gain administrator privileges. */


    /**
     * Initializes the application by starting the thread to probe the left and right cameras.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container) != null)
        {
            if(savedInstanceState == null)
            {
                //this block should also be redone every time the user goes to the CameraOptions UI fragment
                fetchLeftFrame = new FetchLRFrames(getResources(), this, FetchLRFrames.CameraLoc.LEFT);
                fetchRightFrame = new FetchLRFrames(getResources(), this, FetchLRFrames.CameraLoc.RIGHT);
                fetchLeftFrame.execute(5, 5, 5);
                fetchRightFrame.execute(5, 5, 5);

                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, fetchLeftFrame.getCameraOptions()).commit();
            }
        }
    }


    /**
     * Initializes the menu.
     *
     * @param menu
     *
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        System.out.println("created menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;

        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Handles the menu UI, displaying the relevent sub-menu if an option is selected.
     *
     * @param item
     *
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId() )
        {
            case R.id.network:
                System.out.println("\"Network\" selected");
                networkConfig.initNetworkConfigDialog(this, getLayoutInflater() ); //!!!this should really happen on create!!!
                networkConfig.getNetworkDialog().show();
                return true;
            case R.id.help:
                System.out.println("\"Help\" selected");
                return true;
            case R.id.admin:
                System.out.println("\"Administrator\" selected");
                login.initLoginDialog(this, getLayoutInflater());
                login.show( menu.getItem(2));
                System.out.println("checking for admin status");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * Handles the fragment transactions (i.e. swaps in and out the different fragments).
     *
     * @param position
     */
    @Override
    public void onImgSelected(int position)
    {
        System.out.println("swapping");
        VideoFrag secondFrag = new VideoFrag();
        secondFrag.setArguments(getIntent().getExtras());

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container, secondFrag);
        transaction.addToBackStack(null); //this enables the back button
        transaction.commit();

    }



} // End of class MainActivity