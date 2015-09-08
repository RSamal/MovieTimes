/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public Movie(){}

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

    public void setmId(String mId) {
        this.mId = mId;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public void setmPosterPath(String mPosterPath) {
        this.mPosterPath = mPosterPath;
    }

    public void setmVoteAvg(String mVoteAvg) {
        this.mVoteAvg = mVoteAvg;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

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
