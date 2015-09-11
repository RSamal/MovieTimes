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

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.udacity.movietimes.Utils.TestUtilites;


/**
 * Created by ramakant on 9/6/2015.
 */
public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();


    // Each Test starts with a clean database
    void deleteMovieDatabae() {
        mContext.deleteDatabase(MovieContract.DATABASE_NAME);
    }


    public void testMovieTable() {

        // First Step : Delete the database to create an empty database for the testcase
        deleteMovieDatabae();

        // Second Step : Get a reference to WritableDatabase
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        // Third Step : Create a movie record using Contentvalues
        ContentValues testValue = TestUtilites.createMovieRecord();

        // Fourth Step : Insert the ContentValue into the database and get a Row Id back
        long movieRowId;
        movieRowId = database.insert(MovieContract.MovieEntry.TABLE_NAME, null, testValue);

        // Verify we got a row back
        assertTrue("Error: Failure to insert the record", movieRowId != -1);

        // Fifith Step : Querry the database to receive a cursor back
        Cursor cursor = database.query(
                MovieContract.MovieEntry.TABLE_NAME, // Table to query
                null, // all columns
                null, // columns for the where clause
                null, // values for the where clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order

        );


        // Move the cursor to valid database row and check to see if we get any record back
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        // Sixth step : validate the record in Cursor with original content values

        TestUtilites.validateCurrentRecord("Error: Movie querry validation failed", cursor, testValue);

        // Move the cursor to the next record to demonstrate there is only one record in the database
        assertFalse("Error: More than one record returned from the querry", cursor.moveToNext());

        cursor.close();
        database.close();

    }

    public void testTrailerTable() {

        // First Step : Delete the database to create an empty database for the testcase
        deleteMovieDatabae();

        // Second Step : Get a reference to WritableDatabase
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        // Third Step : Create a movie record using Contentvalues
        ContentValues testValue = TestUtilites.createTrailerRecord();

        // Fourth Step : Insert the ContentValue into the database and get a Row Id back
        long movieRowId;
        movieRowId = database.insert(MovieContract.TrailerEntry.TABLE_NAME, null, testValue);

        // Verify we got a row back
        assertTrue("Error: Failure to insert the record", movieRowId != -1);

        // Fifith Step : Querry the database to receive a cursor back
        Cursor cursor = database.query(
                MovieContract.TrailerEntry.TABLE_NAME, // Table to query
                null, // all columns
                null, // columns for the where clause
                null, // values for the where clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order

        );


        // Move the cursor to valid database row and check to see if we get any record back
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        // Sixth step : validate the record in Cursor with original content values

        TestUtilites.validateCurrentRecord("Error: Movie querry validation failed", cursor, testValue);

        // Move the cursor to the next record to demonstrate there is only one record in the database
        assertFalse("Error: More than one record returned from the querry", cursor.moveToNext());

        cursor.close();
        database.close();

    }

    public void testReviewTable() {

        // First Step : Delete the database to create an empty database for the testcase
        deleteMovieDatabae();

        // Second Step : Get a reference to WritableDatabase
        // If there's an error in those massive SQL table creation Strings,
        // errors will be thrown here when you try to get a writable database.
        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        // Third Step : Create a movie record using Contentvalues
        ContentValues testValue = TestUtilites.createReviewRecord();

        // Fourth Step : Insert the ContentValue into the database and get a Row Id back
        long movieRowId;
        movieRowId = database.insert(MovieContract.ReviewEntry.TABLE_NAME, null, testValue);

        // Verify we got a row back
        assertTrue("Error: Failure to insert the record", movieRowId != -1);

        // Fifith Step : Querry the database to receive a cursor back
        Cursor cursor = database.query(
                MovieContract.ReviewEntry.TABLE_NAME, // Table to query
                null, // all columns
                null, // columns for the where clause
                null, // values for the where clause
                null, // columns to group by
                null, // columns to filter by row groups
                null  // sort order

        );


        // Move the cursor to valid database row and check to see if we get any record back
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        // Sixth step : validate the record in Cursor with original content values

        TestUtilites.validateCurrentRecord("Error: Movie querry validation failed", cursor, testValue);

        // Move the cursor to the next record to demonstrate there is only one record in the database
        assertFalse("Error: More than one record returned from the querry", cursor.moveToNext());

        cursor.close();
        database.close();

    }



}
