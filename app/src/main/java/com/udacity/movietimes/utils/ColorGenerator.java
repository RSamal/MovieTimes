package com.udacity.movietimes.utils;

import android.content.Context;

import com.udacity.movietimes.R;

import java.util.Random;

/**
 * Created by ramakant on 8/25/2015.
 * <p/>
 * This is a color generator class will be use for setting up the background color for CardViews. This class will generate random colors from
 * list of selected colors and return the int value.
 */
public class ColorGenerator {

    private  int[] mColorArray;
    private  Context mContext;
    private  Random mRandom;


    public ColorGenerator(Context context) {
        mContext = context;
        mColorArray = context.getResources().getIntArray(R.array.backgroundColors);
        mRandom = new Random();

    }

    public  int getBackgroundColor(){

        int index = mRandom.nextInt(mColorArray.length);
        return mColorArray[index];

    }


}
