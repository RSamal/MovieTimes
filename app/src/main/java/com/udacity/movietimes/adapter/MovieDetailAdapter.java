package com.udacity.movietimes.adapter;
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

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.utils.MovieUrl;

/**
 * This Adapter will be used to display the Movie details returned from JOIN operation of Movie, Trailer and Review
 * Created by ramakant on 9/11/2015.
 */
public class MovieDetailAdapter extends CursorAdapter {

    private static final String LOG_TAG = MovieDetailAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_COUNT = 3;
    private static final int VIEW_TYPE_MOVIE_TRAILER = 0;
    private static final int VIEW_TYPE_MOVIE_BODY = 1;
    private static final int VIEW_TYPE_MOVIE_REVIEW = 2;
    protected ListView mListView;

    // This CursorAdapter will handle view from Three deifferent table i.e Movie, Trailer and Review.
    // The Cursor to this adapter is a MergeCursor and got the reesult merged from the above mentiond table as mentioned in the below sequence of the Column Index. Any changes in the
    // MovieContentProvider MergeCursor will impact the result. Hence these index needs to be verify.

    /**
     * Cursor Data From Movie Table for a movieId. Ignoring the First Index 0 as it is _id
     */
    public static final int COL_TITLE = 1;
    public static final int COL_RELEASE_DATE = 2;
    public static final int COL_POSTER = 3;
    public static final int COL_RATING = 4;
    public static final int COL_OVERVIEW = 5;

    /**
     * Cursor Data From Trailer Table for a movieId. Ignoring the First Index 0 as it is _id
     */
    public static final int COL_TRAILER_KEY = 1;

    /**
     * Cursor Data From Trailer Table for a movieId. Ignoring the First Index 0 as it is _id
     */
    public static final int COL_AUTHOR_NAME = 1;
    public static final int COL_REVIEW_CONTENT = 2;


    public MovieDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        View view = null;

        switch (viewType) {
            case VIEW_TYPE_MOVIE_BODY: {
                layoutId = R.layout.movie_fragment_movie;
                view = LayoutInflater.from(context).inflate(layoutId, parent, false);
                ViewHolder viewHolder = new MovieViewHolder(view);
                view.setTag(viewHolder);
                break;
            }
            case VIEW_TYPE_MOVIE_REVIEW: {
                layoutId = R.layout.movie_fragment_review;
                view = LayoutInflater.from(context).inflate(layoutId, parent, false);
                ViewHolder viewHolder = new ReviewVeiwHolder(view);
                view.setTag(viewHolder);
                break;
            }
            case VIEW_TYPE_MOVIE_TRAILER: {
                layoutId = R.layout.movie_fragment_trailer;
                view = LayoutInflater.from(context).inflate(layoutId, parent, false);
                ViewHolder viewHolder = new TrailerVeiwHolder(view);
                view.setTag(viewHolder);
                break;
            }
        }

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_MOVIE_BODY: {
                final MovieViewHolder viewHolder = (MovieViewHolder) view.getTag();

                // Set the poster of the movie
                StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                        .append(cursor.getString(COL_POSTER));
                Picasso.with(context).load(imagePath.toString()).into(viewHolder.poster, new Callback.EmptyCallback() {
                    @Override
                    public void onError() {
                        viewHolder.poster.setImageResource(R.drawable.no_poster);
                    }
                });

                // Set the title of the movie
                viewHolder.title.setText(cursor.getString(COL_TITLE));

                // set the favorite button of the movie
                Typeface fontFamily = Typeface.createFromAsset(context.getAssets(), "fonts/fontawesome.ttf");
                viewHolder.favorite.setTypeface(fontFamily);
                viewHolder.favorite.setText("\uf196");

                // Set the Release date of the movie

                // Set the Rating of the Movie
                viewHolder.rating.setRating((float) (Float.valueOf(cursor.getString(COL_RATING)) / 2.0));

                // Set the Overview of the Movie
                viewHolder.overview.setText(cursor.getString(COL_OVERVIEW));

                break;
            }
            case VIEW_TYPE_MOVIE_REVIEW: {
                ReviewVeiwHolder viewHolder = (ReviewVeiwHolder) view.getTag();
                viewHolder.author.setText(cursor.getString(COL_AUTHOR_NAME));
                viewHolder.review.setText(cursor.getString(COL_REVIEW_CONTENT));
                break;
            }
            case VIEW_TYPE_MOVIE_TRAILER: {
                final TrailerVeiwHolder viewHolder = (TrailerVeiwHolder) view.getTag();

                // Set the Trailer Image
                Uri.Builder mVedioUrl = Uri.parse(MovieUrl.MOVIE_VEDIO_BASE_URL).buildUpon()
                        .appendPath(cursor.getString(COL_TRAILER_KEY))
                        .appendPath(MovieUrl.VEDIO_TN_SIZE);

                Picasso.with(mContext).load(mVedioUrl.toString()).into(viewHolder.trailerPic, new Callback.EmptyCallback() {
                    @Override
                    public void onError() {
                        super.onError();
                        viewHolder.trailerMsg.setText(R.string.noTrailer);
                    }
                });

                break;
            }


        }
    }

    @Override
    public int getItemViewType(int position) {

        switch (position) {
            case 0:
                return VIEW_TYPE_MOVIE_TRAILER;

            case 1:
                return VIEW_TYPE_MOVIE_BODY;

            default:
                return VIEW_TYPE_MOVIE_REVIEW;

        }

    }



    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    // This is Markup interface for Movie Data structures
    public interface ViewHolder {
    }

    /**
     * Cache of the Movie views for a forecast list item.
     */
    public static class MovieViewHolder implements ViewHolder {
        ImageView poster;
        TextView title;
        TextView favorite;
        TextView releaseDate;
        RatingBar rating;
        TextView overview;

        public MovieViewHolder(View view) {

            poster = (ImageView) view.findViewById(R.id.movie_fragment_movie_poster);
            title = (TextView) view.findViewById(R.id.movie_fragment_movie_title);
            favorite = (TextView) view.findViewById(R.id.movie_fragment_movie_favorite);
            releaseDate = (TextView) view.findViewById(R.id.movie_fragment_movie_release_date);
            rating = (RatingBar) view.findViewById(R.id.movie_fragment_movie_rating);
            overview = (TextView) view.findViewById(R.id.movie_fragment_movie_overview);
        }
    }

    /**
     * Cache of the Review views for a forecast list item.
     */
    public static class ReviewVeiwHolder implements ViewHolder {
        TextView author;
        TextView review;

        public ReviewVeiwHolder(View view) {
            author = (TextView) view.findViewById(R.id.movie_fragment_review_Author);
            review = (TextView) view.findViewById(R.id.movie_fragment_review_content);
        }
    }

    /**
     * Cache of the Trailer views for a forecast list item.
     */
    public static class TrailerVeiwHolder implements ViewHolder {
        ImageView trailerPic;
        TextView trailerMsg;

        public TrailerVeiwHolder(View view) {
            trailerPic = (ImageView) view.findViewById(R.id.movie_fragment_trailer_vedio_img);
            trailerMsg = (TextView) view.findViewById(R.id.movie_fragment_trailer_vedio_msg);
        }
    }


}
