/*
 * Copyright (C) 2014 The Android Open Source Project
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

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * This is contract class for movie database having table and column names. This class will be use by DbHelper class to
 * interact with the SQLite database.
 * Tables : Movie, Trailer, Review
 * Created by ramakant on 9/6/2015.
 */
public final class MovieContract {

    // For DbHelper
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "movie.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String NOT_NULL = " NOT NULL";
    private static final String COMMA_SEP = ",";


    // For ContentProvider and UriMatcher
    public static final String CONTENT_AUTHORITY = "com.udacity.movietimes";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "movie";
    public static final String PATH_TRAILER = "trailer";
    public static final String PATH_REVIEW = "reviews";


    //To prevent instantiating this class
    private MovieContract() {
    }


    //Abstract inner class the defines the constant of the movie table
    public static abstract class MovieEntry implements BaseColumns {

        // Movie table name
        public static final String TABLE_NAME = "movie";

        // Movie table column name
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_POPULAR = "popular";
        public static final String COLUMN_HIGH_RATE = "high_rate";
        public static final String COLUMN_FAVORITE = "favorite";

        //Create statement for the movie table
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_MOVIE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_TITLE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_RELEASE_DATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_POSTER_PATH + TEXT_TYPE + COMMA_SEP +
                COLUMN_RATING + REAL_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                COLUMN_POPULAR + TEXT_TYPE + COMMA_SEP +
                COLUMN_HIGH_RATE + TEXT_TYPE + COMMA_SEP +
                COLUMN_FAVORITE + TEXT_TYPE + COMMA_SEP +
                " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        // Drop table statement for the movie table
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        // Uri matcher details for Movie table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        // This method will return the Uri for querying a specific movie detail with an movie id
        public static Uri buildMovieWithMovieId(Long _id) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(_id)).build();
        }

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return ContentUris.parseId(uri);
        }
    }

    //Abstract inner class the defines the constant of the Trailer table
    public static abstract class TrailerEntry implements BaseColumns {

        // Trailer table name
        public static final String TABLE_NAME = "trailer";

        // Trailer table column name
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_KEY = "title";
        public static final String COLUMN_TRAILER_ID = "release_date";


        //Create statement for the Trailer table
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_MOVIE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_KEY + TEXT_TYPE + COMMA_SEP +
                COLUMN_TRAILER_ID + TEXT_TYPE + COMMA_SEP +
                " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        // Drop table statement for the movie table
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        // Uri matcher details for Movie table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRAILER).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TRAILER;

        // This method will return the Uri for querying a specific movie detail with an movie id
        public static Uri buildTrailerWithMovieId(Long _id) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(_id)).build();
        }

        public static Uri buildTrailerUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return ContentUris.parseId(uri);
        }
    }

    //Abstract inner class the defines the constant of the Review table
    public static abstract class ReviewEntry implements BaseColumns {

        // Trailer table name
        public static final String TABLE_NAME = "review";

        // Trailer table column name
        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_AUTHOR_NAME = "author_name";
        public static final String COLUMN_REVIEW_CONTENT = "content";


        //Create statement for the Trailer table
        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + " (" +
                _ID + INTEGER_TYPE + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
                COLUMN_MOVIE_ID + INTEGER_TYPE + NOT_NULL + COMMA_SEP +
                COLUMN_REVIEW_ID + TEXT_TYPE + COMMA_SEP +
                COLUMN_AUTHOR_NAME + TEXT_TYPE + COMMA_SEP +
                COLUMN_REVIEW_CONTENT + TEXT_TYPE + COMMA_SEP +
                " UNIQUE (" + MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        // Drop table statement for the movie table
        public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


        // Uri matcher details for Movie table
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_REVIEW).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REVIEW;

        // This method will return the Uri for querying a specific movie detail with an movie id
        public static Uri buildReviewWithMovieId(Long _id) {
            return CONTENT_URI.buildUpon().appendPath(Long.toString(_id)).build();
        }

        public static Uri buildReviewUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long getIdFromUri(Uri uri) {
            return ContentUris.parseId(uri);
        }

    }


}
