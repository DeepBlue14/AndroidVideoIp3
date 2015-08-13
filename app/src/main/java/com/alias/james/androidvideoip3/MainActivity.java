/*
 * File:   MainActivity.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This is the main activity of the project.
 *
 * References: http://jimiz.net/2011/09/find-computers-network-nmap-free-tool/#axzz3iWOiNQn4
 *
 * Last Modified: 8/11/2015
 */

package com.alias.james.androidvideoip3;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity implements OnCameraSelectedListener
{
    private Menu menu;
    private FetchLRFrames fetchLRFrames;
    private NetworkConfig networkConfig = new NetworkConfig();
    private Login login = new Login();

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        fetchLRFrames = new FetchLRFrames(getResources() );
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.fragment_container) != null)
        {
            if(savedInstanceState == null)
            {
                CameraOptions cameraOptions = new CameraOptions();
                cameraOptions.setLRFrames(fetchLRFrames.getlFrame(), fetchLRFrames.getRFrame() );
                cameraOptions.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, cameraOptions).commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        System.out.println("created menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        this.menu = menu;

        return super.onCreateOptionsMenu(menu);
    }


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
}