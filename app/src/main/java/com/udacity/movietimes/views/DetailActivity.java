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
package com.udacity.movietimes.views;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.webservices.ConnectionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This activity class contain the detail about a selected movie and shows all details
 * like trailer, rating , description and so on
 */

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = DetailActivity.class.getSimpleName();
    private static final String MOVIE_MESSG = "com.udacity.movietimes.MESSAGE";

    private Toolbar mToolBar;
    private Movie mMovie;
    private ImageView mPoster;
    private TextView mTitle;
    private TextView mReleaseDate;
    private RatingBar mRatingBar;
    private TextView mOverview;
    private TextView mNoTrailerMessage;
    private RelativeLayout mVedio;
    private ImageView mVedioImage;

    private RequestQueue mRequestQueue;
    private Trailer mTrailer;
    private String mVedioId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /** Setup Toolbar as an AppBar */
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitle("");
        mToolBar.setBackgroundColor(Color.TRANSPARENT);
        mToolBar.setBackgroundColor(Color.TRANSPARENT);
        setSupportActionBar(mToolBar);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** Get the movie data from intent passed through MainActivity*/
        mMovie = getIntent().getParcelableExtra(MOVIE_MESSG);

        /** Update the UI components of Movie Detail */
        updateMovieDetailUi();

    }

    /**
     * This method is used to update the UI components of the Movie Detail Activity
     *
     * @return Null
     */
    public void updateMovieDetailUi() {

        mPoster = (ImageView) findViewById(R.id.detail_activity_poster);
        mTitle = (TextView) findViewById(R.id.detail_activity_title);
        mReleaseDate = (TextView) findViewById(R.id.detail_activity_release_date);
        mRatingBar = (RatingBar) findViewById(R.id.detail_activity_rating);
        mOverview = (TextView) findViewById(R.id.detail_activity_overview);
        mVedio = (RelativeLayout) findViewById(R.id.detail_activity_vedio);
        mVedioImage = (ImageView) findViewById(R.id.detail_activity_vedio_img);
        mNoTrailerMessage = (TextView) findViewById(R.id.detail_activity_msg_tv);


        /** Set the Vedio Trailer of the movie from youtube */
        setMovieTrailer(mMovie.getmId());

        /** Set the poster of the movie using Picasso */
        // Get the IMAGE url
        StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                .append(mMovie.getmPosterPath());
        Picasso.with(this).load(imagePath.toString()).into(mPoster);

        /** Set the title of the movie */
        mTitle.setText(mMovie.getmTitle());

        /** Set the release Date of the movie */

        SimpleDateFormat mInputFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat mOutputFormat = new SimpleDateFormat("MMM yyyy");
        try {
            Date mDate = mInputFormat.parse(mMovie.getmReleaseDate());
            String formatDate = mOutputFormat.format(mDate);
            mReleaseDate.setText(formatDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**Set the Rating of the Movie*/
        mRatingBar.setRating((float) (Float.valueOf(mMovie.getmVoteAvg()) / 2.0));

        /** Setting up the overview */
        mOverview.setText(mMovie.getmOverview());
    }

    /**
     * This function will return the movie trailer Id fetched from MovieDb Api
     *
     * @param movieId
     * @return null
     */
    public void setMovieTrailer(final String movieId) {

        /** To get the Movie Trailer Id from Movie Db */
        final Uri.Builder mTrailerUrl = Uri.parse(MovieUrl.MOVIE_VEDIO_ID_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MovieUrl.VIDEOS)
                .appendQueryParameter(MovieUrl.API_KEY_PARM, MovieConfig.MOVIEDB_API_KEY);

        mRequestQueue = ConnectionManager.getRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, mTrailerUrl.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                mTrailer = new Gson().fromJson(response, Trailer.class);

                /**To get the Thumbnail from Youtube and set it on Image throug Picasso */
                if (mTrailer.getmVedios().size() != 0) {
                    mVedioId = mTrailer.getmVedios().get(0).getmKey();

                    Uri.Builder mVedioUrl = Uri.parse(MovieUrl.MOVIE_VEDIO_BASE_URL).buildUpon()
                            .appendPath(mVedioId)
                            .appendPath(MovieUrl.VEDIO_TN_SIZE);

                    Picasso.with(getApplicationContext()).load(mVedioUrl.toString()).into(mVedioImage);

                    mVedio.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent intent = YouTubeStandalonePlayer.createVideoIntent(DetailActivity.this, MovieConfig.GOOGLE_API_KEY, mVedioId);
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + mVedioId));
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    mNoTrailerMessage.setText(R.string.noTrailer);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mNoTrailerMessage.setText(R.string.noTrailer);
            }

        });

        mRequestQueue.add(request);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
