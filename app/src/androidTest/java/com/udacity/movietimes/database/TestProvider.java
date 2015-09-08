package com.udacity.movietimes.database;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;

import com.udacity.movietimes.database.MovieContract.MovieEntry;

/**
 * Created by ramakant on 9/7/2015.
 */
public class TestProvider extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void deleteAllRecords(){
        mContext.getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                null
        );
    }

    public void testGetType(){
        //Test the content Type of Content Uri
        String type = mContext.getContentResolver().getType(MovieEntry.CONTENT_URI);
        assertEquals("Error: The movie CONTENT_URI should return MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE,type);

        //Test Content Type of Content Uri with movie
        int movieid = 111;
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieWithMovieId(movieid));
        assertEquals("Error: The movie CONTENT_URI with movie id should return MovieEntry.CONTENT_ITEM_TYPE", MovieEntry.CONTENT_ITEM_TYPE,type);

        // Test Content type of Content Uri with user choice
        String choice = "popular";
        type = mContext.getContentResolver().getType(MovieEntry.buildMovieWithChoice(choice));
        assertEquals("Error: The movie CONTENT_URI with choice should return MovieEntry.CONTENT_TYPE", MovieEntry.CONTENT_TYPE, type);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void testMovieQuery(){

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

        TestUtilites.validateCurrentMovieRecord("TestMovieRecord", cursor, testValues);
        cursor.close();
    }

    public void testInsertMovie(){

        ContentValues testValues = TestUtilites.createMovieRecord();

        //Register a conetent observer for our insert
        TestUtilites.TestContentObserver tco =  TestUtilites.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI, true, tco);
        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, testValues);

        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);

        // Verify we got a valid row id back
        long movieRowId = ContentUris.parseId(movieUri);
        assertTrue(movieRowId!=-1);

        // verify the cursor data are correct
        Cursor cursor = mContext.getContentResolver().query(
                MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();
        TestUtilites.validateCurrentMovieRecord("Error : Error in validating movie entry", cursor, testValues);
    }

//    public void testDeleteRecord(){
//        //Regester a ContentObserver for the movie record delete
//        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
//        mContext.getContentResolver().registerContentObserver(MovieEntry.CONTENT_URI,true,tco);
//
//        deleteAllRecords();
//
//        tco.waitForNotificationOrFail();
//        mContext.getContentResolver().unregisterContentObserver(tco);
//    }

    public void testUpdateRecord(){
        ContentValues values = TestUtilites.createMovieRecord();
        
        Uri movieUri = mContext.getContentResolver().insert(MovieEntry.CONTENT_URI, values);
        long movieRowId = ContentUris.parseId(movieUri);
        
        assertTrue(movieRowId != -1);
        
        ContentValues updatedValues = new ContentValues(values);
        updatedValues.put(MovieEntry._ID,movieRowId);
        updatedValues.put(MovieEntry.COLUMN_TITLE, "Sample Changed");
        
        Cursor cursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,null,null,null,null);
        TestUtilites.TestContentObserver tco = TestUtilites.TestContentObserver.getTestContentObserver();
        cursor.registerContentObserver(tco);
        
        int count = mContext.getContentResolver().update(MovieEntry.CONTENT_URI,updatedValues, MovieEntry._ID + " = ? ",
                new String[] {Long.toString(movieRowId)});
        
        assertEquals(count, 1);
        
        tco.waitForNotificationOrFail();
        
        cursor.unregisterContentObserver(tco);
        cursor.close();
        
        Cursor updatedCursor = mContext.getContentResolver().query(MovieEntry.CONTENT_URI,null,MovieEntry._ID + " = " + movieRowId,null,null);
        updatedCursor.moveToFirst();
        TestUtilites.validateCurrentMovieRecord("Error: Error validating movie entry udpate",updatedCursor,updatedValues);
        updatedCursor.close();

    }
}
