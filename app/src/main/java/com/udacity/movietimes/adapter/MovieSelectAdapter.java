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
package com.udacity.movietimes.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.movietimes.fragments.HighestRateMovie;
import com.udacity.movietimes.fragments.PopularMovie;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This is an adapter program to support the pager view.This program will return
 * the selected fragments based up on user select the tabs for Highest rated or popular,
 */
public class MovieSelectAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;
    private String[] mTabTitle = {"HIGH RATE","POPULAR"};

    public MovieSelectAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0){
            return new HighestRateMovie();
        }
        else{
            return new PopularMovie();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitle[position];
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }
}
