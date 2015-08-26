package com.udacity.movietimes.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ramakant samal on 8/17/15.
 *
 * This class will contain the structure of JSON movie data which will be use to Parse using GSON library.
 */
public class Movie implements Parcelable {

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

    @SerializedName("overview")
    private String mOverview;



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

    public String getmOverview() {
        return mOverview;
    }

    /**
     * Parcelble implementation to use in Intent
     */

    protected Movie(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
        mVoteAvg = in.readString();
        mOverview = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mReleaseDate);
        dest.writeString(mPosterPath);
        dest.writeString(mVoteAvg);
        dest.writeString(mOverview);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Movie) {
            Movie toCompare = (Movie) obj;
            return (this.getmId().equalsIgnoreCase(toCompare.getmId()));
        }

        return false;
    }

    @Override
    public int hashCode() {
        return (this.mId + this.mTitle).hashCode();
    }

}
