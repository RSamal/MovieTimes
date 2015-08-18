package com.udacity.movietimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This class will contain the objects for list of movies and will be used to parse JSON data using GSON library.
 */
public class Movies {

    @SerializedName("results")
    private List<Movie> movies;

    public List<Movie> getMovies() {
        return movies;
    }
}
