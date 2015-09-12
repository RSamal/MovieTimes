package com.udacity.movietimes.webservices;

import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.model.Reviews;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.utils.MovieConfig;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * This interface contain the END point for Movie DB Api to get Movie , Trailer and Review Details
 * Created by ramakant on 9/10/2015.
 */
public interface MovieApiEndPoint {

    /**
     * This method sends a GET request to MovieDb and gets the list of Movies in a JSON format. The movie selection can be sorted by
     * Popular or highrate category. There are veriety of options , please refer to MovieDb API documentation for more details.
     * @param sortOrder
     * @param callback
     */
    @GET("/discover/movie?api_key=" + MovieConfig.MOVIEDB_API_KEY)
    void getTopMovies(@Query("sort_by") String sortOrder, Callback<Movies> callback);

    /**
     * This method sends a GET request to MovieDb with a movie Id and get ths response back in JSON format.
     * The response gets transformed to Traile POJO using GSON
     * @param id
     * @param callback
     */
    @GET("/movie/{id}/videos?api_key=" + MovieConfig.MOVIEDB_API_KEY)
    void getMovieTrailers(@Path("id") String id, Callback<Trailer> callback);

    /**
     * This method sends a GET request to MovieDb with movie id to fetch the review details in JSON format
     * @param id
     * @param callback
     */
    @GET("/movie/{id}/reviews?api_key=" + MovieConfig.MOVIEDB_API_KEY)
    void getMovieReviews(@Path("id") String id, Callback<Reviews> callback);
}
