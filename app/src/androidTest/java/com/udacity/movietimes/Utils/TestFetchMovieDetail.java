package com.udacity.movietimes.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.movietimes.R;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.database.MovieDbHelper;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.webservices.FetchMovieDetails;

import java.util.List;
import java.util.Objects;

/**
 * Created by ramakant on 9/10/2015.
 */
public class TestFetchMovieDetail extends AndroidTestCase {


    private static SQLiteQueryBuilder resultSet = null;
    private static SQLiteQueryBuilder selectMovie = null;
    private static SQLiteQueryBuilder selectTrailer = null;
    private static SQLiteQueryBuilder selectReview = null;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW
    };


    private static final String[] TRAILER_COLUMNS = {
            MovieContract.TrailerEntry.COLUMN_MOVIE_ID,
            MovieContract.TrailerEntry.COLUMN_KEY
    };

    private static final String[] REVIEW_COLUMNS = {
            MovieContract.ReviewEntry.COLUMN_MOVIE_ID,
            MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    private  String movieQuery;
    private String trailerQuery;
    private  String reviewQuery;


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
        Thread.sleep(3000);

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


    public void testSample(){


        selectMovie = new SQLiteQueryBuilder();
        selectMovie.setTables(MovieContract.MovieEntry.TABLE_NAME);
        movieQuery = selectMovie.buildQuery(null, MovieContract.MovieEntry.COLUMN_MOVIE_ID  + " = ?", null, null, null, null);
        Log.d("HELLOD",movieQuery);
        selectTrailer = new SQLiteQueryBuilder();
        selectTrailer.setTables(MovieContract.TrailerEntry.TABLE_NAME);
        trailerQuery = selectTrailer.buildQuery(null, MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?", null, null, null,"1");


        selectReview = new SQLiteQueryBuilder();
        selectReview.setTables(MovieContract.ReviewEntry.TABLE_NAME);
        reviewQuery = selectReview.buildQuery(null, MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?",null,null,null,null);

        resultSet = new SQLiteQueryBuilder();
        Log.d("HELLOD",trailerQuery);
        resultSet.setTables("(" + movieQuery + ") T1 LEFT JOIN (" + trailerQuery + ") T2 ON " +
                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T2." + MovieContract.TrailerEntry.COLUMN_MOVIE_ID
                        + " LEFT JOIN (" + reviewQuery + ") T3 ON " +
                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T3." + MovieContract.ReviewEntry.COLUMN_MOVIE_ID
        );

        String finalQuery = resultSet.buildQuery(null,null,null,null,null,null,null);
        String[] args = {"76341","76341","76341"};


        MovieDbHelper helper = new MovieDbHelper(mContext);
        SQLiteDatabase database = helper.getReadableDatabase();

        Cursor cursor = database.rawQuery(finalQuery, args);
        //Cursor cursor = database.rawQuery(reviewQuery,args);

        Log.d("HELLOD",finalQuery);
        Log.d("HELLOD",Integer.toString(cursor.getCount()));

        cursor.moveToFirst();
        cursor.moveToNext();

        Log.d("HELLOD", cursor.getString(18));




    }

}
