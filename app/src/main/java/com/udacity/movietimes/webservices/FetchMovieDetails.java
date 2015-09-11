package com.udacity.movietimes.webservices;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.model.Reviews;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.utils.MovieUtility;
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

    private Context mContext;

    public FetchMovieDetails(Context context) {
        mContext = context;
    }

    public void callMovieDbRest() {

        String sortOrder = MovieUtility.getPreferedSortOrder(mContext);

        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(MovieUrl.BASE_URL).build();
        final MovieApiEndPoint apiService = restAdapter.create(MovieApiEndPoint.class);

        apiService.getTopMovies(sortOrder, new Callback<Movies>() {

            @Override
            public void success(Movies movies, Response response) {

                List<Movie> movieList = movies.getMovieList();
                MovieUtility.storeMovies(mContext, movieList);
                for (final Movie movie : movieList) {

                    apiService.getMovieTrailers(movie.getmId(), new Callback<Trailer>() {
                        @Override
                        public void success(Trailer trailer, Response response) {
                            MovieUtility.storeTrailers(mContext, movie.getmId(), trailer.getTrailerList());

                        }

                        @Override
                        public void failure(RetrofitError error) {
                        }
                    });

                    apiService.getMovieReviews(movie.getmId(), new Callback<Reviews>() {
                        @Override
                        public void success(Reviews reviews, Response response) {
                            MovieUtility.storeReviews(mContext, movie.getmId(), reviews.getReviewList());
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
