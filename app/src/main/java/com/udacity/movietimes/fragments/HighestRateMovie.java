package com.udacity.movietimes.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.movietimes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighestRateMovie extends Fragment {




    public HighestRateMovie() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_highest_rate_movie, container, false);



        return view;
    }


}
