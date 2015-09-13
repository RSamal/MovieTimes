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
package com.udacity.movietimes.database;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Intent;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import com.udacity.movietimes.database.MovieContract.*;
import com.udacity.movietimes.model.Movie;


/**
 * This is MovieProvider class of type ContentProvider, which wll be use to interact with SQLite DB MovieDb. This class
 * is a Helper class which works on top of SQLite DB with veriety of its Helper functions
 * Created by ramakant on 9/7/2015.
 */
public class MovieProvider extends ContentProvider {

    private static final String LOG_TAG = MovieProvider.class.getSimpleName();
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper;

    public static final int MOVIE = 100;
    public static final int MOVIE_WITH_ID = 101;

    public static final int TRAILER = 200;
    public static final int TRAILER_WITH_ID = 201;

    public static final int REVIEW = 300;
    public static final int REVIEW_WITH_ID = 301;

    public static final int MOVIE_WITH_TRAILER_AND_REVIEW = 400;


    /**
     * Join Parameters :
     * (SELECT MOVIE) LEFT JOIN (SELECT TRAILER) LEFT JOIN (SELEC REVIEWS)  --- JOIN PREDICATE CONDITION is movieId for all tables
     */
    private static SQLiteQueryBuilder resultSet = null;
    private static SQLiteQueryBuilder selectMovie = null;
    private static SQLiteQueryBuilder selectTrailer = null;
    private static SQLiteQueryBuilder selectReview = null;

    private static final String[] MOVIE_COLUMNS = {
            MovieContract.MovieEntry._ID,
//            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_OVERVIEW
    };


    private static final String[] TRAILER_COLUMNS = {
            MovieContract.TrailerEntry._ID,
//            MovieContract.TrailerEntry.COLUMN_MOVIE_ID,
            MovieContract.TrailerEntry.COLUMN_TRAILER_KEY
    };

    private static final String[] REVIEW_COLUMNS = {
            MovieContract.ReviewEntry._ID,
//            MovieContract.ReviewEntry.COLUMN_MOVIE_ID,
            MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    private static final String[] MOVIE_DETAIL_COLUMNS = {
            "T1." + MovieContract.MovieEntry._ID + " AS _id",
            "T1." + MovieContract.MovieEntry.COLUMN_TITLE,
            "T1." + MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            "T1." + MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            "T1." + MovieContract.MovieEntry.COLUMN_RATING,
            "T1." + MovieContract.MovieEntry.COLUMN_OVERVIEW,
            "T2." + MovieContract.TrailerEntry.COLUMN_TRAILER_KEY,
            "T3." + MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            "T3." + MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    private static String movieQuery;
    private static String trailerQuery;
    private static String reviewQuery;
    private static String joinQuery;

    // Build the Join Query
    static {

        // Build Sub querry to fetch data from Movie table based on movieId
        selectMovie = new SQLiteQueryBuilder();
        selectMovie.setTables(MovieContract.MovieEntry.TABLE_NAME);
        movieQuery = selectMovie.buildQuery(MOVIE_COLUMNS, MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, null);

        // Build the sub querry for Trailer Table based on movieId
        selectTrailer = new SQLiteQueryBuilder();
        selectTrailer.setTables(MovieContract.TrailerEntry.TABLE_NAME);
        trailerQuery = selectTrailer.buildQuery(TRAILER_COLUMNS, MovieContract.TrailerEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, "1");

        // Builed the sub querry for Review table based on movieId
        selectReview = new SQLiteQueryBuilder();
        selectReview.setTables(MovieContract.ReviewEntry.TABLE_NAME);
        reviewQuery = selectReview.buildQuery(REVIEW_COLUMNS, MovieContract.ReviewEntry.COLUMN_MOVIE_ID + " = ?", null, null, null, null);

        // Final JOIN Querry
        resultSet = new SQLiteQueryBuilder();
        resultSet.setTables("(" + movieQuery + ") T1 LEFT JOIN (" + trailerQuery + ") T2 ON " +
                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T2." + MovieContract.TrailerEntry.COLUMN_MOVIE_ID
                        + " LEFT JOIN (" + reviewQuery + ") T3 ON " +
                        "T1." + MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = T3." + MovieContract.ReviewEntry.COLUMN_MOVIE_ID
        );

        joinQuery = resultSet.buildQuery(MOVIE_DETAIL_COLUMNS, null, null, null, null, null, null);
    }


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_TRAILER, TRAILER);
        matcher.addURI(authority, MovieContract.PATH_TRAILER + "/#", TRAILER_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_REVIEW, REVIEW);
        matcher.addURI(authority, MovieContract.PATH_REVIEW + "/#", REVIEW_WITH_ID);

        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/details/*", MOVIE_WITH_TRAILER_AND_REVIEW);


        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_WITH_ID:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            case TRAILER:
                return MovieContract.TrailerEntry.CONTENT_TYPE;
            case TRAILER_WITH_ID:
                return MovieContract.TrailerEntry.CONTENT_ITEM_TYPE;
            case REVIEW:
                return MovieContract.ReviewEntry.CONTENT_TYPE;
            case REVIEW_WITH_ID:
                return MovieContract.ReviewEntry.CONTENT_ITEM_TYPE;
            case MOVIE_WITH_TRAILER_AND_REVIEW:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri:" + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {

            // "movie"
            case MOVIE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "movie/#"
            case MOVIE_WITH_ID: {
                long _id = MovieContract.MovieEntry.getIdFromUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        MovieContract.MovieEntry._ID + " = ?",
                        new String[]{Long.toString(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            }

            // "trailer"
            case TRAILER: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "trailer/#"
            case TRAILER_WITH_ID: {
                long _id = MovieContract.TrailerEntry.getIdFromUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.TrailerEntry.TABLE_NAME,
                        projection,
                        MovieContract.TrailerEntry._ID + " = ?",
                        new String[]{Long.toString(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            }

            // "review"
            case REVIEW: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            // "review/#"
            case REVIEW_WITH_ID: {
                long _id = MovieContract.ReviewEntry.getIdFromUri(uri);
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.ReviewEntry.TABLE_NAME,
                        projection,
                        MovieContract.ReviewEntry._ID + " = ?",
                        new String[]{Long.toString(_id)},
                        null,
                        null,
                        sortOrder);
                break;
            }
            // "movie/details/#"
            case MOVIE_WITH_TRAILER_AND_REVIEW: {

                String movieId = MovieEntry.getMovieIdFromUri(uri);
                String args[] = {movieId};
//                retCursor = mOpenHelper.getReadableDatabase().rawQuery(joinQuery, args);
                Cursor movieCursor = mOpenHelper.getReadableDatabase().rawQuery(movieQuery,args);
                Cursor trailerCurosor = mOpenHelper.getReadableDatabase().rawQuery(trailerQuery,args);
                Cursor reviewCursor = mOpenHelper.getReadableDatabase().rawQuery(reviewQuery,args);

                // A movie may not have any trailer. Inorder to help the Merge Cursor result , create a dummy cursor for trailer
                MatrixCursor helperCursor = new MatrixCursor(TRAILER_COLUMNS );
                helperCursor.addRow(new Object[]{null, null});



                if(trailerCurosor.getCount() <= 0) {

                    return new MergeCursor(new Cursor[]{helperCursor,movieCursor,reviewCursor});
                }
                else {
                    return new MergeCursor(new Cursor[]{trailerCurosor,movieCursor,reviewCursor});
                }
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;
        long _id;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.MovieEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert a row " + uri);
                break;

            case TRAILER:
                _id = db.insert(MovieContract.TrailerEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.TrailerEntry.buildTrailerUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert a row " + uri);
                break;

            case REVIEW:
                _id = db.insert(MovieContract.ReviewEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = MovieContract.ReviewEntry.buildReviewUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert a row " + uri);
                break;

            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowDeleted;

        //delete all rows and return the number of records deleted
        if (selection == null) selection = "1";

        switch (sUriMatcher.match(uri)) {

            case MOVIE:
                rowDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case TRAILER:
                rowDeleted = db.delete(MovieContract.TrailerEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case REVIEW:
                rowDeleted = db.delete(MovieContract.ReviewEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }

        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowUpdated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                rowUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case TRAILER:
                rowUpdated = db.update(MovieContract.TrailerEntry.TABLE_NAME, values, selection, selectionArgs);
                break;

            case REVIEW:
                rowUpdated = db.update(MovieContract.ReviewEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }

        if (rowUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        db.close();
        return rowUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase database = mOpenHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case MOVIE: {
                database.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = database.insert(MovieEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            case TRAILER: {
                database.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = database.insert(MovieContract.TrailerEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            case REVIEW: {
                database.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = database.insert(MovieContract.ReviewEntry.TABLE_NAME, null, value);
                        if (_id != -1)
                            returnCount++;
                    }
                    database.setTransactionSuccessful();
                } finally {
                    database.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            }

            default:
                return super.bulkInsert(uri, values);

        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
