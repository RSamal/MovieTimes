package com.udacity.movietimes.activities;
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
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;



import com.udacity.movietimes.R;
import com.udacity.movietimes.fragments.DetailFragment;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();


    private Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {

            Bundle bundle = new Bundle();
            bundle.putString(DetailFragment.DETAIL_MOVIE_ID, getIntent().getStringExtra(Intent.EXTRA_STREAM));

            DetailFragment fragment = new DetailFragment();
            fragment.setArguments(bundle);



            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_fragment_container, fragment)
                    .commit();
        }

        /** Setup Toolbar as an AppBar */
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitle("");
        mToolBar.setBackgroundColor(Color.TRANSPARENT);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


}
