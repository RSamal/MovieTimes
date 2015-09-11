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
package com.udacity.movietimes.Utils;

/**
 * Created by ramakant on 9/6/2015.
 */

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.test.AndroidTestCase;


import com.udacity.movietimes.Utils.PollingCheck;
import com.udacity.movietimes.database.MovieContract;

import java.util.Map;
import java.util.Set;

public class TestUtilites extends AndroidTestCase {


    static public final int BULK_INSERT_RECORDS_TO_INSERT = 10;

    /* Create a record for the Movie Database using ContentValues */
    static ContentValues createMovieRecord() {
        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, 1111);
        testValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Test Movie");
        testValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-15-20");
        testValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/path");
        testValues.put(MovieContract.MovieEntry.COLUMN_RATING, 7.8);
        testValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Sample description");
        testValues.put(MovieContract.MovieEntry.COLUMN_POPULAR, "Y");
        testValues.put(MovieContract.MovieEntry.COLUMN_HIGH_RATE, "Y");
        testValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, "Y");

        return testValues;
    }


    static ContentValues[] createBulkInsertMovieValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues values = new ContentValues();
            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, 1001 + i);
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, "Test" + i);
            values.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-05-15");
            values.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/path");
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Overview");
            values.put(MovieContract.MovieEntry.COLUMN_RATING, 7.8);
            values.put(MovieContract.MovieEntry.COLUMN_FAVORITE, "Y");
            values.put(MovieContract.MovieEntry.COLUMN_POPULAR, "Y");
            values.put(MovieContract.MovieEntry.COLUMN_HIGH_RATE, "Y");

            returnContentValues[i] = values;
        }
        return returnContentValues;
    }

    /* Create a record for the Movie Database using ContentValues */
    static ContentValues createTrailerRecord() {
        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, 1111);
        testValues.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, "Sample");
        testValues.put(MovieContract.TrailerEntry.COLUMN_KEY, "xyz");

        return testValues;
    }

    static ContentValues[] createBulkInsertTrailerValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues values = new ContentValues();
            values.put(MovieContract.TrailerEntry.COLUMN_MOVIE_ID, 1001 + i);
            values.put(MovieContract.TrailerEntry.COLUMN_KEY, "Key-" + i);
            values.put(MovieContract.TrailerEntry.COLUMN_TRAILER_ID, "Id-" + i);

            returnContentValues[i] = values;
        }
        return returnContentValues;
    }

    /* Create a record for the Movie Database using ContentValues */
    static ContentValues createReviewRecord() {
        ContentValues testValues = new ContentValues();

        testValues.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, 1111);
        testValues.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID, "Sample");
        testValues.put(MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME, "autor");
        testValues.put(MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT, "Content");

        return testValues;
    }

    static ContentValues[] createBulkInsertReviewValues() {

        ContentValues[] returnContentValues = new ContentValues[BULK_INSERT_RECORDS_TO_INSERT];

        for (int i = 0; i < BULK_INSERT_RECORDS_TO_INSERT; i++) {
            ContentValues values = new ContentValues();
            values.put(MovieContract.ReviewEntry.COLUMN_MOVIE_ID, 1001 + i);
            values.put(MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME, "Author");
            values.put(MovieContract.ReviewEntry.COLUMN_REVIEW_ID, "Id-" + i);
            values.put(MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT, "Sample Content");

            returnContentValues[i] = values;
        }
        return returnContentValues;
    }


    /* validate the record from cursor with original Contentvalues */
    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {

        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column " + columnName + " not found " + error, idx == -1);

            String expectedValue = entry.getValue().toString();
            assertTrue("Value " + valueCursor.getString(idx) + " did not match the expected value " + expectedValue, expectedValue.equals(valueCursor.getString(idx)));

        }
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }
}
