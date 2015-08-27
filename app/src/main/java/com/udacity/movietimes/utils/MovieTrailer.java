package com.udacity.movietimes.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.webservices.ConnectionManager;

/**
 * Created by ramakant on 8/26/2015.
 * <p/>
 * This class has been created to fetch the Youtube Movie Trailer Image from Youtube.
 */
public class MovieTrailer {

    private static String mMovieId;
    private static Context mContext;
    private static RequestQueue mRequestQueue;
    private static Trailer mTrailer;



}
