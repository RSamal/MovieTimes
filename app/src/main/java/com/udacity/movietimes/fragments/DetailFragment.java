package com.udacity.movietimes.fragments;


import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.widget.ListView;

import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieDetailAdapter;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.utils.MovieUtility;


/**
 * This Frafment will be responsible to deispla the details about a Movie , which includes Trailer, Movie Detail and User reviews.
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailFragment.class.getSimpleName();

    private static final String FORECAST_SHARE_HASHTAG = " #MovieTimesApp";
    private static final int DETAIL_LOADER = 0;
    private MovieDetailAdapter movieDetailAdapter;
    private ListView mListView;

    public static final String[] MOVIE_WITH_TRAILER_AND_REVIEW_COLUMNS = {
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER_PATH,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.TrailerEntry.COLUMN_KEY,
            MovieContract.ReviewEntry.COLUMN_AUTHOR_NAME,
            MovieContract.ReviewEntry.COLUMN_REVIEW_CONTENT
    };

    // Please note that the below contants belongs to the above querry column. Any change in the column need a change in the below index values
    public static final int COL_MOVIE_ID = 2;
    public static final int COL_TITLE = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_POSTER_PATH = 5;
    public static final int COL_RATING = 6;
    public static final int COL_OVERVIEW = 7;
    public static final int COL_KEY = 8;
    public static final int COL_AUTHOR_NAME = 9;
    public static final int COL_REVIEW_CONTENT = 10;


    private String movieId;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        movieId = getActivity().getIntent().getStringExtra(Intent.EXTRA_STREAM);
        Log.d(LOG_TAG,movieId);
        movieDetailAdapter = new MovieDetailAdapter(getActivity(), null, 0);

        mListView = (ListView) view.findViewById(R.id.detail_fragment_listview);
        mListView.setAdapter(movieDetailAdapter);


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri movieDetailUri = MovieContract.MovieEntry.buildMovieDetailUri(Integer.valueOf(movieId));
        return new CursorLoader(getActivity(),
                movieDetailUri,
                MOVIE_WITH_TRAILER_AND_REVIEW_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        movieDetailAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        movieDetailAdapter.swapCursor(null);
    }


}
