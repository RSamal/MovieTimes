package com.udacity.movietimes.webservices;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This is a singleton class to provide one Volley Request queue for the networking call.
 */
public class ConnectionManager {

    private static RequestQueue mRequestQueue;


    private ConnectionManager(){}

    /**
     * This method will be used to get a single instance of Request queue for the entire application.
     * @param context
     * @return RequestQueue
     */
    public static RequestQueue getRequestQueue(Context context) {

        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(context);
        }

        return mRequestQueue;
    }

}
