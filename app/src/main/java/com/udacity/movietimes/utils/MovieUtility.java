/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.udacity.movietimes.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.udacity.movietimes.R;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Reviews;
import com.udacity.movietimes.model.Trailer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

/**
 * Created by ramakant on 9/10/2015.
 */
public class MovieUtility {

    public static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_FAVORITE
    };

    // These indices are tied to MOVIE_COLUMNS.  If MOVIE_COLUMNS changes, these
    // must change.
    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_RELEASE_DATE = 3;
    public static final int COL_POSTER_PATH = 4;
    public static final int COL_RATING = 5;
    public static final int COL_OVERVIEW = 6;

    // To do bulk insert into Movie Table
    public static void storeMovies(Context context, List<Movie> movies, String sortOrder) {

        List<ContentValues> contentValues = new ArrayList<ContentValues>(movies.size());

        for (int i = 0; i < movies.size(); i++) {


            ContentValues values = new ContentValues();
            Movie movie = movies.get(i);

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getmId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, movie.getmTitle());
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getmReleaseDate());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getmPosterPath());
            values.put(MovieContract.MovieEntry.COLUMN_RATING, movie.getmVoteAvg());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getmOverview());
            if (sortOrder.equals(context.getString(R.string.sort_popularity))) {
                values.put(MovieContract.MovieEntry.COLUMN_POPULAR, "Y");
                values.put(MovieContract.MovieEntry.COLUMN_FAVORITE, "N");
                values.put(MovieContract.MovieEntry.COLUMN_HIGH_RATE, "N");
            } else {
                values.put(MovieContract.MovieEntry.COLUMN_POPULAR, "N");
                values.put(MovieContract.MovieEntry.COLUMN_FAVORITE, "N");
                values.put(MovieContract.MovieEntry.COLUMN_HIGH_RATE, "Y");
            }

            contentValues.add(values);

            context.getContentResolver().bulkInsert(MovieContract.MovieEntry.CONTENT_URI, contentValues.toArray(new ContentValues[contentValues.size()]));
        }
    }

    // To do bulk insert into Trailer table
    public static void storeTrailers(Context context, String movieId, List<Trailer.MovieTrailer> trailerList) {
        List<ContentValues> contentValues = new ArrayList<ContentValues>(trailerList.size());
        for (int i = 0; i < trailerList.size(); i++) {
            ContentValues values = new ContentValues();
            Trailer.MovieTrailer trailer = trailerList.get(i);
            values.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, movieId);
            values.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, trailer.getId());
            values.put(MovieContract.TrailerEntry.COLUMN_TRAILER_KEY, trailer.getKey());
            contentValues.add(values);
            int rowCount = context.getContentResolver().bulkInsert(MovieContract.TrailerEntry.CONTENT_URI, contentValues.toArray(new ContentValues[contentValues.size()]));
        }
    }

    // To do bulk insert into Review table
    public static void storeReviews(Context context, String movieId, List<Reviews.Review> reviewList) {
        List<ContentValues> contentValues = new ArrayList<ContentValues>(reviewList.size());
        for (int i = 0; i < reviewList.size(); i++) {
            ContentValues values = new ContentValues();
            Reviews.Review review = reviewList.get(i);
            values.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, movieId);
            values.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID, review.getId());
            values.put(MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME, review.getAuthor());
            values.put(MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT, review.getContent());

            contentValues.add(values);
            context.getContentResolver().bulkInsert(MovieContract.ReviewEntry.CONTENT_URI, contentValues.toArray(new ContentValues[contentValues.size()]));
        }
    }

    // Date Formatter
    public static String formatDate(String date){

        SimpleDateFormat mInputFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat mOutputFormat = new SimpleDateFormat("MMM yyyy");
        try {
            Date mDate = mInputFormat.parse(date);
            String formatDate = mOutputFormat.format(mDate);
            return  formatDate;

        } catch (ParseException e) {
            return  null;
        }
    }


}
