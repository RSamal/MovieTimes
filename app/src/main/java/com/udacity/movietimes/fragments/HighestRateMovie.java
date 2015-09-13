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


import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.udacity.movietimes.R;
import com.udacity.movietimes.activities.DetailActivity;
import com.udacity.movietimes.adapter.MovieListAdapter;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.utils.MovieUtility;


import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is a part of ViewPager and responsible to load the Popular Movie from MovieDb.
 * It contents are loaded dynamically through network call, which uses Google Volley Api for it.
 */

public class HighestRateMovie extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private static final String TAG = HighestRateMovie.class.getSimpleName();
    private static final int HIGH_RATE_MOVIE_LOADER = 0;

    private GridView mGridView;
    private ProgressBar progressBar;
    private MovieListAdapter mMovieListAdapter;
    private List<Movie> movieList;


    public HighestRateMovie() {
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

        progressBar = (ProgressBar) view.findViewById(R.id.progressbar);
        mGridView = (GridView) view.findViewById(R.id.movie_fragment_gridview);
        mGridView.setAdapter(mMovieListAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) mMovieListAdapter.getItem(position);
                if (cursor != null) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), DetailActivity.class);
                    intent.putExtra(Intent.EXTRA_STREAM, cursor.getString(MovieUtility.COL_MOVIE_ID));
                    startActivity(intent);

                }
            }
        });


        return view;

    }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        progressBar.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(HIGH_RATE_MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] selectionArgs = {"Y"};

        return new CursorLoader(getActivity(),
                MovieContract.MovieEntry.CONTENT_URI,
                MovieUtility.MOVIE_COLUMNS,
                MovieContract.MovieEntry.COLUMN_HIGH_RATE + " = ?",
                selectionArgs,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.getCount() > 0) {
            progressBar.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
        }
        mMovieListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMovieListAdapter.swapCursor(null);
    }
}
