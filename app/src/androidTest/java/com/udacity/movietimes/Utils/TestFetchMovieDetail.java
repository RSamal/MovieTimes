package com.udacity.movietimes.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.movietimes.R;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.database.MovieDbHelper;
import com.udacity.movietimes.sync.MovieSyncAdapter;


/**
 * Created by ramakant on 9/10/2015.
 */
public class TestFetchMovieDetail extends AndroidTestCase {


    private static SQLiteQueryBuilder resultSet = null;
    private static SQLiteQueryBuilder selectMovie = null;
    private static SQLiteQueryBuilder selectTrailer = null;
    private static SQLiteQueryBuilder selectReview = null;


    private String movieQuery;
    private String trailerQuery;
    private String reviewQuery;


    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW
    };


    private static final String[] TRAILER_COLUMNS = {
            //MovieContract.TrailerEntry._ID,
            MovieContract.TrailerEntry.COLUMN_MOVIE_ID,
            MovieContract.TrailerEntry.COLUMN_TRAILER_KEY
    };

    private static final String[] REVIEW_COLUMNS = {
            // MovieContract.ReviewEntry._ID,
            MovieContract.ReviewEntry.COLUMN_MOVIE_ID,
            MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    private static final String[] MOVIE_DETAIL_COLUMNS = {

            "T1." + MovieContract.MovieEntry.COLUMN_TITLE,
            "T1." + MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            "T1." + MovieContract.MovieEntry.COLUMN_RATING,
            "T1." + MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            "T1." + MovieContract.MovieEntry.COLUMN_OVERVIEW,
            "T2." + MovieContract.TrailerEntry.COLUMN_TRAILER_KEY,
            "T3." + MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            "T3." + MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    public void testSample() {
//
      MovieSyncAdapter.syncImmediately(mContext);


//
//        MovieDbHelper helper = new MovieDbHelper(mContext);
//        SQLiteDatabase database = helper.getReadableDatabase();
//        selectMovie = new SQLiteQueryBuilder();
//        selectMovie.setTables(MovieContract.MovieEntry.TABLE_NAME);
//        movieQuery = selectMovie.buildQuery(MOVIE_COLUMNS, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, null);
////        Log.d("JOINTEST", movieQuery);
//        String[] args = {"76341"};
//        Cursor cursor = database.rawQuery(movieQuery, args);
//        cursor.moveToFirst();
////        Log.d("JOINTEST", cursor.getString(0));
////        Log.d("JOINTEST", cursor.getString(1));
////        Log.d("JOINTEST", cursor.getString(2));
////        Log.d("JOINTEST", cursor.getString(3));
////        Log.d("JOINTEST", cursor.getString(4));
////        Log.d("JOINTEST", cursor.getString(5));
//
//
//
//        selectTrailer = new SQLiteQueryBuilder();
//        selectTrailer.setTables(MovieContract.TrailerEntry.TABLE_NAME);
//        trailerQuery = selectTrailer.buildQuery(TRAILER_COLUMNS, MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, "1");
//
//        Log.d("JOINTEST", trailerQuery);
//        cursor = database.rawQuery(trailerQuery, args);
//        cursor.moveToFirst();
//
//        do {
//            Log.d("JOINTEST", cursor.getString(0));
//            Log.d("JOINTEST", cursor.getString(1));
//        } while (cursor.moveToNext());
//
//
//        selectReview = new SQLiteQueryBuilder();
//        selectReview.setTables(MovieContract.ReviewEntry.TABLE_NAME);
//        reviewQuery = selectReview.buildQuery(REVIEW_COLUMNS, MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, null);
//
//        Log.d("JOINTEST", reviewQuery);
//        cursor = database.rawQuery(reviewQuery, args);
//        cursor.moveToFirst();
//
//        do {
//            Log.d("JOINTEST", cursor.getString(0));
//            Log.d("JOINTEST", cursor.getString(1));
//            Log.d("JOINTEST", cursor.getString(2));
//        } while (cursor.moveToNext());
//
////        Log.d("JOINTEST","You are here");
////        resultSet = new SQLiteQueryBuilder();
////        resultSet.setTables("(" + movieQuery + ") T1 LEFT JOIN (" + trailerQuery + ") T2 ON " +
////                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T2." + MovieContract.TrailerEntry.COLUMN_MOVIE_ID
////                        + " LEFT JOIN (" + reviewQuery + ") T3 ON " +
////                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T3." + MovieContract.ReviewEntry.COLUMN_MOVIE_ID
////        );
////        String[] joinArg = {"76341","76341","76341"};
////        String finalQuery = resultSet.buildQuery(MOVIE_DETAIL_COLUMNS, null, null, null, null, null, null);
////        Log.d("JOINTEST", finalQuery);
////        cursor = database.rawQuery(finalQuery, joinArg);


//        Uri uri = MovieContract.MovieEntry.buildMovieDetailUri(53189);
//        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null, null);
//        cursor.moveToFirst();


//        do {
//            Log.d("JOINTEST", "Cursor-->" + cursor.getPosition());
//            if (cursor.getPosition() == 0) {
//                Log.d("JOINTEST", cursor.getString(0));
//                Log.d("JOINTEST", cursor.getString(1));
//                Log.d("JOINTEST", cursor.getString(2));
//                Log.d("JOINTEST", cursor.getString(3));
//                Log.d("JOINTEST", cursor.getString(4));
//                Log.d("JOINTEST", cursor.getString(5));
//                Thread.currentThread().wait(2000);
//
//            } else {
//                Log.d("JOINTEST", cursor.getString(1));
//
//            }
//        } while (cursor.moveToNext());
        //Cursor cursor = database.rawQuery(reviewQuery,args);

//
//
//        MovieDbHelper helper = new MovieDbHelper(mContext);
//        SQLiteDatabase database = helper.getReadableDatabase();
//
//        Cursor cursor = database.rawQuery(finalQuery, args);
//        //Cursor cursor = database.rawQuery(reviewQuery,args);
//
//        Log.d("HELLOD",finalQuery);
//        Log.d("HELLOD",Integer.toString(cursor.getCount()));
//


    }

}
