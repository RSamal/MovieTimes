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
import android.widget.TextView;


import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieDetailAdapter;
import com.udacity.movietimes.database.MovieContract;


/**
 * This Frafment will be responsible to deisplay the details about a Movie , which includes Trailer, Movie Detail and User reviews.
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public static final String DETAIL_MOVIE_ID = "movieId";
    private static final String LOG_TAG = DetailFragment.class.getSimpleName();
    private static final String FORECAST_SHARE_HASHTAG = " #MovieTimesApp";
    private static final int DETAIL_LOADER = 0;
    private MovieDetailAdapter movieDetailAdapter;
    private ListView mListView;
    private TextView favorite;


    private String movieId;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        if (arguments != null) {
            movieId = arguments.getString(DetailFragment.DETAIL_MOVIE_ID);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);



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
        if (movieId != null) {
            Uri movieDetailUri = MovieContract.MovieEntry.buildMovieDetailUri(Integer.valueOf(movieId));
            return new CursorLoader(getActivity(),
                    movieDetailUri,
                    null,
                    null,
                    null,
                    null);
        }
        return null;

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
