package com.udacity.movietimes.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.movietimes.R;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.webservices.FetchMovieDetails;

import java.util.List;

/**
 * Created by ramakant on 9/10/2015.
 */
public class TestFetchMovieDetail extends AndroidTestCase {

    public void testFetchPopularMovieDetails() throws InterruptedException {

        mContext.getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.TrailerEntry.CONTENT_URI, null, null);
        mContext.getContentResolver().delete(MovieContract.ReviewEntry.CONTENT_URI, null, null);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        preferences.edit().putString(
                mContext.getString(R.string.api_sort_key),
                mContext.getString(R.string.api_sort_popularity)
        );

        Thread thread = Thread.currentThread();
        FetchMovieDetails details = new FetchMovieDetails(mContext);
        details.callMovieDbRest();
        Thread.sleep(6000);

        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null, null);
        assertTrue(cursor.getCount() > 0);

        cursor.moveToFirst();
        int movieid = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_MOVIE_ID));
        Log.d("TESTVALLUES", Integer.toString(movieid));
        Cursor cursor2 = mContext.getContentResolver().query(MovieContract.TrailerEntry.CONTENT_URI, null, MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = " + movieid, null, null, null);
        cursor2.moveToFirst();
        Log.d("TESTVALLUES", cursor2.getString(cursor.getColumnIndex(MovieContract.TrailerEntry.COLUMN_TRAILER_ID)));
        Log.d("TESTVALLUES", Integer.toString(cursor2.getCount()));

        Cursor cursor1 = mContext.getContentResolver().query(MovieContract.ReviewEntry.CONTENT_URI,null, MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = " + movieid, null, null,null);
        cursor1.moveToFirst();
        Log.d("TESTVALLUES", cursor1.getString(cursor1.getColumnIndex(MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME)));
        Log.d("TESTVALLUES", Integer.toString(cursor1.getCount()));
    }

}
