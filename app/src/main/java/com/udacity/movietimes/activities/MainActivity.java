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
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.udacity.movietimes.R;
import com.udacity.movietimes.adapter.MovieSelectAdapter;
import com.udacity.movietimes.fragments.Callback;
import com.udacity.movietimes.fragments.DetailFragment;
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
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        // If the user wantes ever to refresh data manually, this option will help
        if (id == R.id.action_refresh) {
            updateMovie();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This function get called if the user click refresh button manually. This will help immidiate sync of data
     * with the server
     */
    public void updateMovie() {
        MovieSyncAdapter.syncImmediately(getApplicationContext());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize the MovieSyncAdapter
        MovieSyncAdapter.initializeSyncAdapter(this);

        //This conditon checks if we are in Bigger Layout like Tablet or not. If yes then inflate the fragment to the Tablet specific
        //layout other wise go with the detail activity fragment
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

    }


    /**
     * This is Callback interface interface method which helps to communicate between two different fragments by passing
     * movieId values.
     *
     * @param movieId
     */
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
