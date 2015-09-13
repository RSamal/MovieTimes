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
package com.udacity.movietimes.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieSelectAdapter;
import com.udacity.movietimes.database.MovieContract;
import com.udacity.movietimes.fragments.Callback;
import com.udacity.movietimes.fragments.DetailFragment;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.sync.MovieSyncAdapter;

public class MainActivity extends AppCompatActivity implements Callback {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";

    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private boolean mTwoPane;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_refresh) {
            updateMovie();
        }

        return super.onOptionsItemSelected(item);
    }

    public void updateMovie() {
        MovieSyncAdapter.syncImmediately(getApplicationContext());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the MovieSyncAdapter
        MovieSyncAdapter.initializeSyncAdapter(this);

        if (findViewById(R.id.detail_fragment_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, new DetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

        /** Setup Toolbar as an AppBar */
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitle(R.string.app_name);
        setSupportActionBar(mToolBar);

        /** Setup the ViewPager*/
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new MovieSelectAdapter(getSupportFragmentManager()));

        /** Setup the Sliding Tab*/
        mTabLayout = (TabLayout) findViewById(R.id.sliding_tab);
        mTabLayout.setupWithViewPager(mViewPager);

        /** Implement Tab Listner for page selection */

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    @Override
    public void onItemSelected(String movieId) {
        if (mTwoPane) {

            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.DETAIL_MOVIE_ID, movieId);

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment, DETAILFRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Intent.EXTRA_STREAM, movieId);
            startActivity(intent);
        }
    }
}
