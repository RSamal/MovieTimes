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
package com.udacity.movietimes.fragments;


import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieListAdapter;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.utils.MovieUtility;
import com.udacity.movietimes.webservices.FetchMovieDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is a part of ViewPager and responsible to load the Popular Movie from MovieDb.
 * It contents are loaded dynamically through network call, which uses Google Volley Api for it.
 */

public class PopularMovie extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = PopularMovie.class.getSimpleName();
    private static final int MOVIE_LOADER = 0;

    private GridView mGridView;
    private MovieListAdapter mMovieListAdapter;
    private List<Movie> movieList;


    public PopularMovie() {
        // Required empty public constructor
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieList != null) {
            outState.putParcelableArrayList("KEY", (ArrayList<? extends Parcelable>) movieList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Instantiate the cursor Adapater
        mMovieListAdapter = new MovieListAdapter(getActivity(), null, 0);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        mGridView = (GridView) view.findViewById(R.id.movie_fragment_gridview);
        mGridView.setAdapter(mMovieListAdapter);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        preferences.edit().putString(
                getActivity().getString(R.string.api_sort_key),
                getActivity().getString(R.string.api_sort_popularity)
        );

        // Fetch the Movie Details

        FetchMovieDetails details = new FetchMovieDetails(getActivity());
        details.callMovieDbRest();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] selectionArgs = {"Y"};

        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                MovieUtility.MOVIE_COLUMNS,
                MovieContract.MovieEntry.COLUMN_POPULAR + " = ?",
                selectionArgs,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mMovieListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieListAdapter.swapCursor(null);
    }
}
