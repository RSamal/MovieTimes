package com.udacity.movietimes.webservices;

import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.utils.MovieConfig;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * This interface contain the END point for Movie DB Api to get Movie , Trailer and Review Details
 * Created by ramakant on 9/10/2015.
 */
public interface MovieApiEndPoint {

    @GET("/discover/movie?api_key=" + MovieConfig.MOVIEDB_API_KEY)
    void getTopMovies(@Query("sortby") String sortOrder, Callback<Movies> callback);

}
