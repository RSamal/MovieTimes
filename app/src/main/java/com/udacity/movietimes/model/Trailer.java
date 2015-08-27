package com.udacity.movietimes.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ramakant on 8/26/2015.
 *
 * This class contains the model object return for vedios from MovieDb
 */
public class Trailer {

    @SerializedName("results")
    private List<Vedio> mVedios;


    public List<Vedio> getmVedios() {
        return mVedios;
    }
}
