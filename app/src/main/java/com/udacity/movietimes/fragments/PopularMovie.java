package com.udacity.movietimes.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieRecycleviewAdapter;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.views.DetailActivity;
import com.udacity.movietimes.webservices.ConnectionManager;

/**
 * This fragment Class will show the details of popular movie for the user Tab selection. Upon selecting the tab
 * Popular , user will view the details fetched from network or database
 */

public class PopularMovie extends Fragment implements MovieRecycleviewAdapter.MovieItemClickListner {


    private static final String TAG = PopularMovie.class.getSimpleName();
    private static final String MOVIE_MESSG = "com.udacity.movietimes.MESSAGE";

    private RequestQueue mRequestQueue;
    private RecyclerView mRecyclerView;
    private MovieRecycleviewAdapter mMovieRecycleviewAdapter;
    private CardView mCardView;


    public PopularMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie, container, false);


        /**
         *  Setup for the RecyclerView
         */

        //instantiate the recycler view
        mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_popular_movie_rv);
        mRecyclerView.setHasFixedSize(true);

        // Set the LinearLayout Manager and DefaultAnimator
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //Set the recycler view adapter


        /**
         * Fetch the popular movie from MovieDB and update it on UI
         */
        getPopularMovies();


        return view;

    }

    /**
     * This method will be use to get the popular movie details using MovieDb API from MovieDb.
     * It uses google Volley for the networking call
     *
     * @return null
     */
    public void getPopularMovies() {

        /**
         * Build the URL for the popular movie using Uri.Builder
         */
        final String popularity_desc = "popularity.desc";

        final Uri.Builder movieUrl = Uri.parse(MovieUrl.BASE_URL).buildUpon()
                .appendQueryParameter(MovieUrl.SORT_BY_PARM, popularity_desc)
                .appendQueryParameter(MovieUrl.API_KEY_PARM, MovieConfig.MOVIEDB_API_KEY);


        /**
         *  Do the network call for request/response using Volley
         */
        Movies movies = null;
        mRequestQueue = ConnectionManager.getRequestQueue(getActivity().getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.GET, movieUrl.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Movies movies = new Gson().fromJson(response, Movies.class);

                //Set the view adapter
                mMovieRecycleviewAdapter = new MovieRecycleviewAdapter(getActivity().getApplicationContext(), PopularMovie.this, movies.getMovieList());
                mRecyclerView.setAdapter(mMovieRecycleviewAdapter);

                //TODO : Put the Movie list into a Database

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO : Get the list from Database
            }

        });

        mRequestQueue.add(request);


    }

    @Override
    public void onItemClicked(Movie movie) {

        //TODO Start the detail activity

        Intent mIntent = new Intent(getActivity(), DetailActivity.class);
        Bundle mBundle = new Bundle();
        mBundle.putParcelable(MOVIE_MESSG,movie);
        mIntent.putExtras(mBundle);
        startActivity(mIntent);

    }
}
