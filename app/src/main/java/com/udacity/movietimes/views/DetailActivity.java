package com.udacity.movietimes.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieTrailer;
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
    private RelativeLayout mVedio;
    private ImageView mVedioImage;

    private RequestQueue mRequestQueue;
    private Trailer mTrailer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        /**
         * Setup Toolbar as an AppBar
         */
        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
        mToolBar.setTitle("");
        mToolBar.setBackgroundColor(Color.TRANSPARENT);
        //mToolBar.setBackgroundColor(Color.TRANSPARENT);
        setSupportActionBar(mToolBar);

        /**
         * Get the movie data from intent passed through MainActivity
         */
        mMovie = (Movie) getIntent().getParcelableExtra(MOVIE_MESSG);

        /**
         * Update the UI components of Movie Detail
         */
        updateMovieDetailUi();

    }

    public void updateMovieDetailUi() {

        mPoster = (ImageView) findViewById(R.id.detail_activity_poster);
        mTitle = (TextView) findViewById(R.id.detail_activity_title);
        mReleaseDate = (TextView) findViewById(R.id.detail_activity_release_date);
        mRatingBar = (RatingBar) findViewById(R.id.detail_activity_rating);
        mOverview = (TextView) findViewById(R.id.detail_activity_overview);
        mVedio = (RelativeLayout) findViewById(R.id.detail_activity_vedio);
        mVedioImage = (ImageView) findViewById(R.id.detail_activity_vedio_img);


        /**
         * Set the Vedio Trailer of the movie from youtube
         */
        final String mMovieTrailerId = getMovieTrailerId(mMovie.getmId());

        Uri.Builder mTrailerUrl = Uri.parse(MovieUrl.MOVIE_VEDIO_BASE_URL).buildUpon()
                .appendPath(mMovieTrailerId)
                .appendPath(MovieUrl.VEDIO_TN_SIZE);
        Picasso.with(this).load(mTrailerUrl.toString()).into(mVedioImage);

        mVedio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = YouTubeStandalonePlayer.createVideoIntent(DetailActivity.this, MovieConfig.GOOGLE_API_KEY, mMovieTrailerId);
                startActivity(intent);
            }
        });


        /**
         *  Set the Poster of the Movie
         */

        // Get the IMAGE url
        StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                .append(mMovie.getmPosterPath());

        // Set the Image url using Picasso
        Picasso.with(this).load(imagePath.toString()).into(mPoster);

        /**
         * Set the title of the movie
         */
        mTitle.setText(mMovie.getmTitle());

        /**
         * Set the release Date of the movie
         */

        SimpleDateFormat mInputFormat = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat mOutputFormat = new SimpleDateFormat("MMM yyyy");
        try {
            Date mDate = mInputFormat.parse(mMovie.getmReleaseDate());
            String formatDate = mOutputFormat.format(mDate);
            mReleaseDate.setText(formatDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        /**
         * Set the Rating of the Movie
         */
        mRatingBar.setRating(Float.valueOf(mMovie.getmVoteAvg()) / 2);

        /**
         * Setting up the overview
         */
        mOverview.setText(mMovie.getmOverview());


    }

    /**
     * This function will return the movie trailer Id fetched from MovieDb Api
     * @param movieId
     * @return
     */
    public String getMovieTrailerId(String movieId) {

        Uri.Builder mTrailerUrl = Uri.parse(MovieUrl.MOVIE_VEDIO_ID_URL).buildUpon()
                .appendPath(movieId)
                .appendPath(MovieUrl.VIDEOS)
                .appendQueryParameter(MovieUrl.API_KEY_PARM, MovieConfig.MOVIEDB_API_KEY);
        Log.d(TAG,mTrailerUrl.toString());

        mRequestQueue = ConnectionManager.getRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.GET, mTrailerUrl.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                mTrailer = new Gson().fromJson(response, Trailer.class);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        });

        mRequestQueue.add(request);


        // Always return the first Trailer Id
        return null;
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

        return super.onOptionsItemSelected(item);
    }
}
