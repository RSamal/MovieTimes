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

/**
 * Created by ramakant on 9/6/2015.
 */

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;


import java.util.Map;
import java.util.Set;

public class TestUtilites extends AndroidTestCase {


    /* Create a record for the Movie Database using ContentValues */
    static ContentValues createMovieRecord() {
        ContentValues testValues = new ContentValues();
        testValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, 1111);
        testValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Test Movie");
        testValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2015-15-20");
        testValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, "/path");
        testValues.put(MovieContract.MovieEntry.COLUMN_RATING, 7.8);
        testValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Sample description");
        testValues.put(MovieContract.MovieEntry.COLUMN_TRAILER_ID, "TEST-ID");
        // For user review I am using a JSON object
        testValues.put(MovieContract.MovieEntry.COLUMN_USER_REVIEW, "{\"id\":76341,\"page\":1,\"results\":[{\"id\":\"55660928c3a3687ad7001db1\",\"author\":\"Phileas Fogg\",\"content\":\"Fabulous action movie. Lots of interesting characters. They don't make many movies like this. The whole movie from start to finish was entertaining I'm looking forward to seeing it again. I definitely recommend seeing it.\",\"url\":\"http://j.mp/1HLTNzT\"},{\"id\":\"55732a53925141456e000639\",\"author\":\"Andres Gomez\",\"content\":\"Good action movie with a decent script for the genre. The photography is really good too but, in the end, it is quite repeating itself from beginning to end and the stormy OST is exhausting.\",\"url\":\"http://j.mp/1dUnvpG\"}],\"total_pages\":1,\"total_results\":2}");
        testValues.put(MovieContract.MovieEntry.COLUMN_POPULAR, "Y");
        testValues.put(MovieContract.MovieEntry.COLUMN_HIGH_RATE, "Y");
        testValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, "Y");

        return testValues;
    }

    /* validate the record from cursor with original Contentvalues */
    static void validateCurrentMovieRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();

        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column " + columnName + " not found " + error, idx == -1);

            String expectedValue = entry.getValue().toString();
            assertTrue("Value " + valueCursor.getString(idx) + " did not match the expected value " + expectedValue, expectedValue.equals(valueCursor.getString(idx)));

        }
    }

}
