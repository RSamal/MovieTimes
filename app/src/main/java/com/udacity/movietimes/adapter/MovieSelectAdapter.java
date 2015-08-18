package com.udacity.movietimes.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.udacity.movietimes.fragments.HighestRateMovie;
import com.udacity.movietimes.fragments.PopularMovie;

/**
 * Created by ramakantasamal on 8/17/15.
 *
 * This is an adapter program to support the pager view.This programe will return
 * the selected fragments based up on user choice for Highest rated or popular
 */
public class MovieSelectAdapter extends FragmentPagerAdapter {

    private static final int TAB_COUNT = 2;
    private String[] mTabTitle = {"POPULAR","HIGH RATE"};

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
