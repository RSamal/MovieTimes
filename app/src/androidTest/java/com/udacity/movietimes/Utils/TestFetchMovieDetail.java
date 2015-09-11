package com.udacity.movietimes.Utils;

import android.test.AndroidTestCase;

import com.udacity.movietimes.webservices.FetchMovieDetails;

/**
 * Created by ramakant on 9/10/2015.
 */
public class TestFetchMovieDetail extends AndroidTestCase{

    public void testFetchMovieDetails() throws InterruptedException {
        Thread thread = Thread.currentThread();
        FetchMovieDetails details = new FetchMovieDetails();
        details.callMovieDbRest("vote_average.desc");
        Thread.sleep(3000);
    }

}
