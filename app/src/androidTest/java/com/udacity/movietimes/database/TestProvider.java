package com.udacity.movietimes.database;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.test.AndroidTestCase;

import com.udacity.movietimes.Utils.TestUtilites;
import com.udacity.movietimes.database.MovieContract.*;

/**
 * This is a testing class to test the functionality of MoviepProvider.
 * Created by ramakant on 9/7/2015.
 */
public class TestProvider extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void deleteAllRecords() {
        deleteMovieRecords();
        deleteTrailerRecords();
        deleteReviewRecords();
    }

    public void deleteMovieRecords() {
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from movie table during delete", 0, cursor.getCount());
        cursor.close();
    }

    public void deleteTrailerRecords() {
        mContext.getContentResolver().delete(
                TrailerEntry.CONTENT_URI,
                null,
                null
        );
        Cursor cursor = mContext.getContentResolver().query(
                TrailerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from trailer table during delete", 0, cursor.getCount());
        cursor.close();
    }

    public void deleteReviewRecords() {
        mContext.getContentResolver().delete(
                ReviewEntry.CONTENT_URI,
                null,
                null
        );
        Cursor cursor = mContext.getContentResolver().query(
                ReviewEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        assertEquals("Error: Records not deleted from review table during delete", 0, cursor.getCount());
        cursor.close();
    }

    // Test the Type of Content URI for Movie , Trailer and Review tables
    public void testGetType() {

        String type;
        long _id = 111;

        //Test the content Type of movie Content Uri
        type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        assertEquals("Error: The movie CONTENT_URI should return MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE, type);

        //Test Content Type of Movie with ID Content Uri
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieWithMovieId(_id));
        assertEquals("Error: The movie CONTENT_URI with movie id should return MovieEntry.CONTENT_ITEM_TYPE", MovieEntry.CONTENT_ITEM_TYPE, type);

        //Test the content Type of trailer Content Uri
        type = mContext.getContentResolver().getType(TrailerEntry.CONTENT_URI);
        assertEquals("Error: The trailer CONTENT_URI should return MovieEntry.CONTENT_TYPE", TrailerEntry.CONTENT_TYPE, type);

        //Test Content Type of trailer with ID Content Uri
        type = mContext.getContentResolver().getType(TrailerEntry.buildTrailerWithMovieId(_id));
        assertEquals("Error: The trailer CONTENT_URI with movie id should return MovieEntry.CONTENT_ITEM_TYPE", TrailerEntry.CONTENT_ITEM_TYPE, type);

        //Test the content Type of review Content Uri
        type = mContext.getContentResolver().getType(ReviewEntry.CONTENT_URI);
        assertEquals("Error: The review CONTENT_URI should return MovieEntry.CONTENT_TYPE", ReviewEntry.CONTENT_TYPE, type);

        //Test Content Type of review with ID Content Uri
        type = mContext.getContentResolver().getType(ReviewEntry.buildReviewWithMovieId(_id));
        assertEquals("Error: The review CONTENT_URI with movie id should return MovieEntry.CONTENT_ITEM_TYPE", ReviewEntry.CONTENT_ITEM_TYPE, type);


    }

    // Test the ContentProvider Query function for movie table
    public void testMovieQuery() {

        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        ContentValues testValues = TestUtilites.createMovieRecord();
        long movieRowId = database.insert(MovieEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Unable to insert into the database", movieRowId != -1);
        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue("Error: Unsuccessful Querry", cursor.getCount() == 1);
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        TestUtilites.validateCurrentRecord("TestMovieRecord", cursor, testValues);
        cursor.close();
    }

    // Test the ContentProvider Query function for trailer table
    public void testTrailerQuery() {

        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        ContentValues testValues = TestUtilites.createTrailerRecord();
        long movieRowId = database.insert(TrailerEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Unable to insert into the database", movieRowId != -1);
        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                TrailerEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue("Error: Unsuccessful Querry", cursor.getCount() == 1);
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        TestUtilites.validateCurrentRecord("TestTrailerRecord", cursor, testValues);
        cursor.close();
    }

    // Test the ContentProvider Query function for review table
    public void testReviewQuery() {

        MovieDbHelper dbHelper = new MovieDbHelper(mContext);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        assertEquals(true, database.isOpen());

        ContentValues testValues = TestUtilites.createReviewRecord();
        long movieRowId = database.insert(ReviewEntry.TABLE_NAME, null, testValues);

        assertTrue("Error: Unable to insert into the database", movieRowId != -1);
        database.close();

        Cursor cursor = mContext.getContentResolver().query(
                ReviewEntry.CONTENT_URI,
                null,
                null,
                null,
                null,
                null
        );
        assertTrue("Error: Unsuccessful Querry", cursor.getCount() == 1);
        assertTrue("Error: No records return from the location query", cursor.moveToFirst());

        TestUtilites.validateCurrentRecord("TestReviewRecord", cursor, testValues);
        cursor.close();
    }


    // Test the MovieProviders Insert function for movie table
    public void testInsertMovie() {

        ContentValues testValues = TestUtilites.createMovieRecord();

        //Register a conetent observer for our insert
        TestUtilites.TestContentObserver tco = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        // Verify we got a valid row id back
        long movieRowId = ContentUris.parseId(movieUri);
        assertTrue(movieRowId != -1);

        // verify the cursor data are correct
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error : Error in validating movie entry", cursor, testValues);
    }

    // Test the MovieProviders Insert function for trailer table
    public void testInsertTrailer() {

        ContentValues testValues = TestUtilites.createTrailerRecord();

        //Register a conetent observer for our insert
        TestUtilites.TestContentObserver tco = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(TrailerEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(TrailerEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        // Verify we got a valid row id back
        long movieRowId = ContentUris.parseId(movieUri);
        assertTrue(movieRowId != -1);

        // verify the cursor data are correct
        Cursor cursor = mContext.getContentResolver().query(
                TrailerEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error : Error in validating trailer entry", cursor, testValues);
    }

    // Test the MovieProviders Insert function for review table
    public void testInsertReview() {

        ContentValues testValues = TestUtilites.createReviewRecord();

        //Register a conetent observer for our insert
        TestUtilites.TestContentObserver tco = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(ReviewEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(ReviewEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        // Verify we got a valid row id back
        long movieRowId = ContentUris.parseId(movieUri);
        assertTrue(movieRowId != -1);

        // verify the cursor data are correct
        Cursor cursor = mContext.getContentResolver().query(
                ReviewEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error : Error in validating movie entry", cursor, testValues);
    }

    public void testDeleteAllRecord() {
        testInsertMovie();
        testInsertTrailer();
        testInsertReview();
        //Regester a ContentObserver for the movie record delete
        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, tco);

        deleteAllRecords();

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);
    }

    // test the Udpate function for MovieProvider Movie table
    public void testUpdateMovieRecord() {
        ContentValues values = TestUtilites.createMovieRecord();

        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(movieUri);

        assertTrue(movieRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MovieEntry._ID, movieRowId);
        updatedValues.put(MovieEntry.COLUMN_TITLE, "Sample Changed");

        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, null, null, null);
        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
        cursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(MovieEntry.CONTENT_URI, updatedValues, MovieEntry._ID + " = ? ",
                new String[]{Long.toString(movieRowId)});

        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        cursor.unregisterContentObserver(tco);
        cursor.close();

        Cursor updatedCursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI, null, MovieEntry._ID + " = " + movieRowId, null, null);
        updatedCursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error: Error validating movie entry udpate", updatedCursor, updatedValues);
        updatedCursor.close();

    }

    // test the Udpate function for MovieProvider Teailer Table
    public void testUpdateTrailerRecord() {
        ContentValues values = TestUtilites.createTrailerRecord();

        Uri trailerUri = mContext.getContentResolver().insert(TrailerEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(trailerUri);

        assertTrue(movieRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(TrailerEntry._ID, movieRowId);
        updatedValues.put(TrailerEntry.COLUMN_TRAILER_ID, "Sample Changed");

        Cursor cursor = mContext.getContentResolver().query(TrailerEntry.CONTENT_URI, null, null, null, null);
        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
        cursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(TrailerEntry.CONTENT_URI, updatedValues, MovieEntry._ID + " = ? ",
                new String[]{Long.toString(movieRowId)});

        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        cursor.unregisterContentObserver(tco);
        cursor.close();

        Cursor updatedCursor = mContext.getContentResolver().query(TrailerEntry.CONTENT_URI, null, TrailerEntry._ID + " = " + movieRowId, null, null);
        updatedCursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error: Error validating trailer entry udpate", updatedCursor, updatedValues);
        updatedCursor.close();

    }

    // test the Udpate function for MovieProvider Review Table
    public void testUpdateReviweRecord() {
        ContentValues values = TestUtilites.createReviewRecord();

        Uri trailerUri = mContext.getContentResolver().insert(ReviewEntry.CONTENT_URI, values);
        long reviewRowId = ContentUris.parseId(trailerUri);

        assertTrue(reviewRowId != -1);

        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(ReviewEntry._ID, reviewRowId);
        updatedValues.put(ReviewEntry.COLUMN_AUTHOR_NAME, "Sample Changed");

        Cursor cursor = mContext.getContentResolver().query(ReviewEntry.CONTENT_URI, null, null, null, null);
        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
        cursor.registerContentObserver(tco);

        int count = mContext.getContentResolver().update(ReviewEntry.CONTENT_URI, updatedValues, ReviewEntry._ID + " = ? ",
                new String[]{Long.toString(reviewRowId)});

        assertEquals(count, 1);

        tco.waitForNotificationOrFail();

        cursor.unregisterContentObserver(tco);
        cursor.close();

        Cursor updatedCursor = mContext.getContentResolver().query(ReviewEntry.CONTENT_URI, null, MovieEntry._ID + " = " + reviewRowId, null, null);
        updatedCursor.moveToFirst();
        TestUtilites.validateCurrentRecord("Error: Error validating trailer entry udpate", updatedCursor, updatedValues);
        updatedCursor.close();

    }

    // Test bulk insert function of MovieProvider to Movie table
    public void testBulkMovieInsert() {


        ContentValues[] bulkInsertContentValues = TestUtilites.createBulkInsertMovieValues();

        // Register a content observer for our bulk insert.
        TestUtilites.TestContentObserver movieObserver = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, movieObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI, bulkInsertContentValues);


        movieObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(movieObserver);

        assertEquals(insertCount, TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for (int i = 0; i < TestUtilites.BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
            TestUtilites.validateCurrentRecord("testBulkInsert.  Error validating MovieEntry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

    // Test bulk insert function of MovieProvider to Trailer table
    public void testBulkTrailerInsert() {


        ContentValues[] bulkInsertContentValues = TestUtilites.createBulkInsertTrailerValues();

        // Register a content observer for our bulk insert.
        TestUtilites.TestContentObserver trailerObserver = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(TrailerEntry.CONTENT_URI, true, trailerObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(TrailerEntry.CONTENT_URI, bulkInsertContentValues);


        trailerObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(trailerObserver);

        assertEquals(insertCount, TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                TrailerEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for (int i = 0; i < TestUtilites.BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
            TestUtilites.validateCurrentRecord("testBulkInsert.  Error validating Trailer Entry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }


    // Test bulk insert function of MovieProvider to Trailer table
    public void testBulkReviewInsert() {


        ContentValues[] bulkInsertContentValues = TestUtilites.createBulkInsertReviewValues();

        // Register a content observer for our bulk insert.
        TestUtilites.TestContentObserver revieObserver = TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(ReviewEntry.CONTENT_URI, true, revieObserver);

        int insertCount = mContext.getContentResolver().bulkInsert(ReviewEntry.CONTENT_URI, bulkInsertContentValues);


        revieObserver.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(revieObserver);

        assertEquals(insertCount, TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // A cursor is your primary interface to the query results.
        Cursor cursor = mContext.getContentResolver().query(
                ReviewEntry.CONTENT_URI,
                null, // leaving "columns" null just returns all the columns.
                null, // cols for "where" clause
                null, // values for "where" clause
                null  // sort order == by DATE ASCENDING
        );

        // we should have as many records in the database as we've inserted
        assertEquals(cursor.getCount(), TestUtilites.BULK_INSERT_RECORDS_TO_INSERT);

        // and let's make sure they match the ones we created
        cursor.moveToFirst();
        for (int i = 0; i < TestUtilites.BULK_INSERT_RECORDS_TO_INSERT; i++, cursor.moveToNext()) {
            TestUtilites.validateCurrentRecord("testBulkInsert.  Error validating review Entry " + i,
                    cursor, bulkInsertContentValues[i]);
        }
        cursor.close();
    }

}
