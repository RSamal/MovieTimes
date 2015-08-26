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

    public static final String CERT_COUNTRY_PARM = "certification_country";
    public static final String CERT_PARM = "certification";

    /**
     * Below is for to fetch the Image of a movie
     */
    public static final String MOVIE_IMAGE_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    /**
     * To Fetch the Vedio trailer of Movie
     */
    public static final String MOVIE_VEDIO_BASE_URL = "http://api.themoviedb.org/3/movie/";
    public static final String VEDIO_PATH = "vedios";


}
