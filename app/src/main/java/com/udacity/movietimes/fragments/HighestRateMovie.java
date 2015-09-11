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
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieListAdapter;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.activities.DetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This fragment is a part of ViewPager and responsible to load the HighRated Movie from MovieDb.
 * It contents are loaded dynamically through network call, which uses Google Volley Api for it.
 * <p/>
 * A simple {@link Fragment} subclass.
 */
public class HighestRateMovie extends Fragment{

    private static final String TAG = HighestRateMovie.class.getSimpleName();
    private static final String MOVIE_MESSG = "com.udacity.movietimes.MESSAGE";
    private static final String MOVIE_KEY = "highrate";

    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
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
            outState.putParcelableArrayList(MOVIE_KEY, (ArrayList<? extends Parcelable>) movieList);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);


        return view;
    }


}
