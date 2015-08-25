package com.udacity.movietimes.utils;

/**
 * Created by ramakantasamal on 8/24/15.
 *
 * This class contain all the URL parameter for the MovieDb Api which will be use to fetch the data.
 */
public abstract class MovieUrl {

    /**
     * Below Parameter is to fetch the movie details
     */
    public static final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";

    public static final String SORT_BY_PARM = "sort_by";
    public static final String API_KEY_PARM = "api_key";

    /**
     * Below is for to fetch the Image of a movie
     */
    public static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";


}
