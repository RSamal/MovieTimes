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


import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieRecycleviewAdapter;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.utils.SpacesItemDecoration;
import com.udacity.movietimes.activities.DetailActivity;
import com.udacity.movietimes.webservices.ConnectionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This fragment is a part of ViewPager and responsible to load the HighRated Movie from MovieDb.
 * It contents are loaded dynamically through network call, which uses Google Volley Api for it.
 * <p/>
 * A simple {@link Fragment} subclass.
 */
public class HighestRateMovie extends Fragment implements MovieRecycleviewAdapter.MovieItemClickListner {

    private static final String TAG = HighestRateMovie.class.getSimpleName();
    private static final String MOVIE_MESSG = "com.udacity.movietimes.MESSAGE";
    private static final String MOVIE_KEY = "highrate";

    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private MovieRecycleviewAdapter mMovieRecycleviewAdapter;

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

//        /** Setup for the RecyclerView */
//        //instantiate the recycler view
//        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_popular_movie_rv);
//        mRecyclerView.setHasFixedSize(true);
//
//        // Set the GridLayout Manager and DefaultAnimator
//        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 2));
//        } else {
//            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 4));
//        }
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new SpacesItemDecoration(50));
//
//        /**Check if the network call is already done and the data has been saved earlier*/
//        if (savedInstanceState == null) {
//
//            /** Fetch the popular movie from MovieDB and update it on UI */
//            getHighRateMovies();
//
//        } else {
//
//            movieList = (List<Movie>) savedInstanceState.get(MOVIE_KEY);
//            //Set the view adapter
//            mMovieRecycleviewAdapter = new MovieRecycleviewAdapter(getActivity().getApplicationContext(), HighestRateMovie.this, movieList);
//            mRecyclerView.setAdapter(mMovieRecycleviewAdapter);
//        }

        return view;
    }

//    /**
//     * This method will be use to get the popular movie details using MovieDb API from MovieDb.
//     * It uses google Volley for the networking call
//     *
//     * @return List<Movie>
//     */
//    public void getHighRateMovies() {
//
//        /** Build the URL for the high rate movie using Uri.Builder */
//        final String certificationCountry = "us";
//        final String certification = "R";
//        final String voteAvg = "vote_average.desc";
//
//        final Uri.Builder movieUrl = Uri.parse(MovieUrl.BASE_URL).buildUpon()
//                .appendQueryParameter(MovieUrl.CERT_COUNTRY_PARM, certificationCountry)
//                .appendQueryParameter(MovieUrl.CERT_PARM, certification)
//                .appendQueryParameter(MovieUrl.SORT_BY_PARM, voteAvg)
//                .appendQueryParameter(MovieUrl.API_KEY_PARM, MovieConfig.MOVIEDB_API_KEY);
//
//
//        /** Do the network call for request/response using Volley */
//        Movies movies = null;
//        mRequestQueue = ConnectionManager.getRequestQueue(getActivity().getApplicationContext());
//
//        StringRequest request = new StringRequest(Request.Method.GET, movieUrl.toString(), new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String response) {
//                Movies movies = new Gson().fromJson(response, Movies.class);
//
//                //Set the view adapter
//                mMovieRecycleviewAdapter = new MovieRecycleviewAdapter(getActivity().getApplicationContext(), HighestRateMovie.this, movies.getMovieList());
//                mRecyclerView.setAdapter(mMovieRecycleviewAdapter);
//
//                // Store the list of movies in the outer class variable , which will be use in onSaveInstanceState
//                setMovieList(movies.getMovieList());
//
//                //TODO : Put the Movie list into a Database
//                MovieDbUtil.loadMovieDatabase(movies.getMovieList(), MovieContract.MovieEntry.HIGH_RATE_MOVIE, getActivity().getApplicationContext());
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO : Get the list from Database
//                Cursor cursor = getActivity().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
//
//                Vector<ContentValues> values = new Vector<ContentValues>(cursor.getCount());
//                if (cursor.moveToFirst()) {
//                    do {
//                        ContentValues value = new ContentValues();
//                        DatabaseUtils.cursorRowToContentValues(cursor, value);
//                        values.add(value);
//                    } while (cursor.moveToNext());
//                }
//
//                List<Movie> movies = MovieDbUtil.convertContentValuesToMovie(values);
//                //Set the view adapter
//                mMovieRecycleviewAdapter = new MovieRecycleviewAdapter(getActivity().getApplicationContext(), HighestRateMovie.this, movies);
//                mRecyclerView.setAdapter(mMovieRecycleviewAdapter);
//
//                // Store the list of movies in the outer class variable , which will be use in onSaveInstanceState
//                setMovieList(movies);
//            }
//
//        });
//        mRequestQueue.add(request);
//
//    }

    /**
     * This is a listner method which gets called when the user click on a movie item from the list.
     *
     * @param movie
     */
    @Override
    public void onItemClicked(Movie movie) {

        Intent mIntent = new Intent(getActivity(), DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(MOVIE_MESSG, movie);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);
    }
}
