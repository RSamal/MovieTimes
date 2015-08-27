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
package com.udacity.movietimes.utils;

import android.content.Context;

import com.udacity.movietimes.R;

import java.util.Random;

/**
 * Created by ramakant on 8/25/2015.
 *
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

    /**
     * This method will select colors randomly from the list of colors and return its int value.
     *
     * @return The random color selected from the Array of colors
     */
    public  int getBackgroundColor(){

        int index = mRandom.nextInt(mColorArray.length);
        return mColorArray[index];

    }


}
