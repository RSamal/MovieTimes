package com.udacity.movietimes.webservices;

import android.util.Log;

import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieUrl;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * This class is responsible for fetching the Movie details from MovieDbApi and updating the Movie, Trailer and Review tables.
 * It uses the Retrofit Api to make the REST call and store the result in Movie, Trailer and Review POJO.
 * Created by ramakant on 9/10/2015.
 */
public class FetchMovieDetails {

    public void callMovieDbRest(final String choice){

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(MovieUrl.BASE_URL).build();
        MovieApiEndPoint apiService = restAdapter.create(MovieApiEndPoint.class);

        apiService.getTopMovies(choice, new Callback<Movies>() {
            int count = 0;
            @Override
            public void success(Movies movies, Response response) {
                FetchMovieDetails details = new FetchMovieDetails();
                details.callMovieDbRest(choice);

                List<Movie> movieList = movies.getMovieList();

                for (Movie movie : movieList){
                    Log.d("SAMPLE",movie.getmTitle());
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
