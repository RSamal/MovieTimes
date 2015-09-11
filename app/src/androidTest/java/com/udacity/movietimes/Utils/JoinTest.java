package com.udacity.movietimes.Utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.test.AndroidTestCase;
import android.util.Log;

import com.udacity.movietimes.database.MovieContract;

/**
 * Created by ramakantasamal on 9/11/15.
 */
public class JoinTest extends AndroidTestCase {



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

    private static final String movieQuery;
    private static final String trailerQuery;
    private static final String reviewQuery;


    static {
        resultSet = new SQLiteQueryBuilder();

        selectMovie = new SQLiteQueryBuilder();
        selectMovie.setTables(MovieContract.MovieEntry.TABLE_NAME);
        movieQuery = selectMovie.buildQuery(MOVIE_COLUMNS, MovieContract.MovieEntry.COLUMN_MOVIE_ID + "= ?", null, null, null, null);

        selectTrailer = new SQLiteQueryBuilder();
        selectTrailer.setTables(MovieContract.TrailerEntry.TABLE_NAME);
        trailerQuery = selectTrailer.buildQuery(TRAILER_COLUMNS, MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, "LIMIT 1");


        selectReview = new SQLiteQueryBuilder();
        selectReview.setTables(MovieContract.ReviewEntry.TABLE_NAME);
        reviewQuery = selectReview.buildQuery(REVIEW_COLUMNS, MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?",null,null,null,null);

    }
    public void testSample(){
        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,null,null,null,null,null);
        assertTrue(cursor.getCount()>0);
        assertEquals(mContext,getContext());
        assertEquals(1,1);
        Log.d("HELLOD",movieQuery);
        Log.d("HELLOD",trailerQuery);
        Log.d("HELLOD",reviewQuery);
        assertTrue(1==1);

    }
}
