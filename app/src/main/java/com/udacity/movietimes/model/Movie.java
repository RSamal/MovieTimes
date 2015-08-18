package com.udacity.movietimes.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramakant samal on 8/17/15.
 *
 * This class will contain the structure of JSON movie data which will be use to Parse using GSON library.
 */
public class Movie {

    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("vote_average")
    private String mVoteAvg;

    /**
     *  All Getters for above feilds
     */

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public String getmPosterPath() {
        return mPosterPath;
    }

    public String getmVoteAvg() {
        return mVoteAvg;
    }
}
