package com.udacity.movietimes.views;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.utils.MovieConfig;
import com.udacity.movietimes.utils.MovieUrl;

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
    private TextView  mTitle;
    private TextView mReleaseDate;
    private RatingBar mRatingBar;
    private TextView mOverview;
  


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
