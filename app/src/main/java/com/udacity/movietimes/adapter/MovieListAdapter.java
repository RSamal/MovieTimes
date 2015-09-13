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

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.utils.ColorGenerator;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.utils.MovieUtility;

/**
 * Created by ramakantasamal on 8/24/15.
 * <p/>
 * This is a RecyclerViewAdapter class which will be responsible for creating movie list at runtime. It gets the data comes back from MovieDB API
 * and uses recyclerview and cardview to list the popular and highest rated movie.
 */
public class MovieListAdapter extends CursorAdapter {

    //Log TAG for this class
    private static final String TAG = MovieListAdapter.class.getSimpleName();



    public MovieListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }



    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {


        View view = LayoutInflater.from(context).inflate(R.layout.moviee_grid, parent, false);

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        MovieViewHolder holder = (MovieViewHolder) view.getTag();

        // Set the Poster of the Movie
        // Get the IMAGE url
        StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                .append(cursor.getString(MovieUtility.COL_POSTER_PATH));

        /**Set the Image url using Picasso */
        Picasso.with(context).load(imagePath.toString()).into(holder.poster);

        /** Set the title of the Movie */
        holder.title.setText(cursor.getString(MovieUtility.COL_TITLE));

        /** Set the Rating of the Movie */
        int backGround = new ColorGenerator(context).getBackgroundColor();
        holder.rating.setRating((float) (Float.valueOf(cursor.getString(MovieUtility.COL_RATING)) / 2.0));
        LayerDrawable stars = (LayerDrawable) holder.rating.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(backGround,PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1).setColorFilter(backGround,PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(2).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);


        /**Setting the Pallete color of card view . As the Pallete color does not work , hence using dynamic color generation routine*/
        holder.cardView.setCardBackgroundColor(backGround);

    }


    /**
     * ViewHolder for movie row items.
     * <p/>
     * This is an inner class of ViewHolder type which will be responsible to create the hold the view items at runtime. This also implements
     * View.OnClickListener interface which is help full to make the CardView clickable event.
     */
    public class MovieViewHolder {

        CardView cardView = null;
        ImageView poster = null;
        TextView title = null;
        TextView favorite = null;
        RatingBar rating = null;


        public MovieViewHolder(View itemView) {

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            poster = (ImageView) itemView.findViewById(R.id.movie_grid_poster);
            title = (TextView) itemView.findViewById(R.id.movie_grid_title);
            rating = (RatingBar) itemView.findViewById(R.id.movie_grid_rating);



        }
    }
}

