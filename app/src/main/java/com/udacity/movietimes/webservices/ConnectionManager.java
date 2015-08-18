package com.udacity.movietimes.webservices;

import com.android.volley.RequestQueue;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This is a singleton class to provide one Request queue for the networking call.
 */
public class ConnectionManager {

    private RequestQueue mRequestQueue;
    private ConnectionManager(){}

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
