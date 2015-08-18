package com.udacity.movietimes.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.movietimes.R;

/**
 * This fragment Class will show the details of popular movie for the user Tab selection. Upon selecting the tab
 * Popular , user will view the details fetched from network or database
 */
public class PopularMovie extends Fragment {


    public PopularMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_popular_movie, container, false);
    }


}
