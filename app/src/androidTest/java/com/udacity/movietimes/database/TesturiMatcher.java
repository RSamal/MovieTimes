package com.udacity.movietimes.database;

import android.content.UriMatcher;
import android.net.Uri;
import android.test.AndroidTestCase;

/**
 *
 * This is a test class to verify the Uri matcher of MovieProvider
 * Created by ramakant on 9/10/2015.
 */
public class TesturiMatcher extends AndroidTestCase {

    private static final long _ID = 1;

    // content://com.udacity.movietimes/movie"
    private static final Uri TEST_MOVIE_DIR = MovieContract.MovieEntry.CONTENT_URI;
    // content://com.udacity.movietimes/movie/#"
    private static final Uri TEST_MOVIE_WITH_ID_DIR = MovieContract.MovieEntry.buildMovieWithMovieId(_ID);

    // content://com.udacity.movietimes/trailer"
    private static final Uri TEST_TRAILER_DIR = MovieContract.TrailerEntry.CONTENT_URI;
    // content://com.udacity.movietimes/trailer/#"
    private static final Uri TEST_TRAILER_WITH_ID_DIR = MovieContract.TrailerEntry.buildTrailerWithMovieId(_ID);

    // content://com.udacity.movietimes/review"
    private static final Uri TEST_REVIEW_DIR = MovieContract.ReviewEntry.CONTENT_URI;
    // content://com.udacity.movietimes/review/#"
    private static final Uri TEST_REVIEW_WITH_ID_DIR = MovieContract.ReviewEntry.buildReviewWithMovieId(_ID);

    // content://com.udacity.movietimes/#/details
    private static final Uri TEST_MOVIE_WITH_TRAILER_AND_REVIEW = MovieContract.MovieEntry.buildMovieDetailUri(1111);
    private static final Uri Sample = Uri.parse("content://com.udacity.movietimes/movie/details/76341");

    Uri movieDetailUri = MovieContract.MovieEntry.buildMovieDetailUri(Integer.valueOf("76341"));



    public void testUriMatcher() {
        UriMatcher testMatcher = MovieProvider.buildUriMatcher();

        assertEquals("Error: The Movie URI was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_DIR), MovieProvider.MOVIE);
        assertEquals("Error: The Movie with id was matched incorrectly.",
                testMatcher.match(TEST_MOVIE_WITH_ID_DIR), MovieProvider.MOVIE_WITH_ID);

        assertEquals("Error: The Trailer URI was matched incorrectly.",
                testMatcher.match(TEST_TRAILER_DIR), MovieProvider.TRAILER);
        assertEquals("Error: The Trailer with id was matched incorrectly.",
                testMatcher.match(TEST_TRAILER_WITH_ID_DIR), MovieProvider.TRAILER_WITH_ID);

        assertEquals("Error: The Review URI was matched incorrectly.",
                testMatcher.match(TEST_REVIEW_DIR), MovieProvider.REVIEW);
        assertEquals("Error: The Review with id was matched incorrectly.",
                testMatcher.match(TEST_REVIEW_WITH_ID_DIR), MovieProvider.REVIEW_WITH_ID);

        assertEquals("Error : The movie detail URL was matched incorrectly",
                testMatcher.match(TEST_MOVIE_WITH_TRAILER_AND_REVIEW), MovieProvider.MOVIE_WITH_TRAILER_AND_REVIEW);
        assertEquals("Error : The movie detail URL was matched incorrectly",
                testMatcher.match(movieDetailUri), MovieProvider.MOVIE_WITH_TRAILER_AND_REVIEW);

    }

}
