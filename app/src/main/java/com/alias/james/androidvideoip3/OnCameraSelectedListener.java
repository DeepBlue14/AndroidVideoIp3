/*
 * File:   OnCameraSelectedListener.java
 * Author: James Kuczynski
 * Email: jkuczyns@cs.uml.edu
 * File Description: This interface allows the fragments to communicate with the main activity
 *                   via callback methods.
 *
 * Reference: http://examples.javacodegeeks.com/android/core/socket-core/android-socket-example/
 * Note: Look up TLS and SSL
 *
 * Created July 17, 2015 at 8:00pm
 */


package com.alias.james.androidvideoip3;


public interface OnCameraSelectedListener
{
    /**
     *
     *
     * @param position
     */
    public void onImgSelected(int position);
}
