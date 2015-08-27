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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.utils.ColorGenerator;
import com.udacity.movietimes.utils.MovieUrl;

import java.util.List;

/**
 * Created by ramakantasamal on 8/24/15.
 * <p/>
 * This is a RecyclerViewAdapter class which will be responsible for creating movie list at runtime. It gets the data comes back from MovieDB API
 * and uses recyclerview and cardview to list the popular and highest rated movie.
 */
public class MovieRecycleviewAdapter extends RecyclerView.Adapter<MovieRecycleviewAdapter.MovieViewHolder> {

    //Log TAG for this class
    private static final String TAG = MovieRecycleviewAdapter.class.getSimpleName();
    protected MovieItemClickListner mListner;
    private List<Movie> mMovieList;
    private Context mContext;

    public MovieRecycleviewAdapter(Context context, MovieItemClickListner listner, List<Movie> movieList) {

        mMovieList = movieList;
        mContext = context;
        mListner = listner;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the movie row of recycler view
        View view = LayoutInflater.from(mContext).inflate(R.layout.moviee_row, parent, false);

        //instantiate the view holder object
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);


        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, int position) {

        /** Set the Poster of the Movie */
        // Get the IMAGE url
        StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                .append(mMovieList.get(position).getmPosterPath());

        // Set the Image url using Volley
        holder.poster.buildDrawingCache();
        Picasso.with(mContext).load(imagePath.toString()).into(holder.poster);

        /** Set the title of the Movie */
        holder.title.setText(mMovieList.get(position).getmTitle());

        /** Set the Rating of the Movie */
        holder.rating.setRating((float) (Float.valueOf(mMovieList.get(position).getmVoteAvg()) / 2.0));

        /**Setting the Pallete color of card view . As the Pallete color does not work , hence using dynamic color generation routine*/
        holder.cardView.setCardBackgroundColor(new ColorGenerator(mContext).getBackgroundColor());

    }

    /**
     * To calculate total movies and returning its size
     *
     * @return Returns the size of Movie list
     */
    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    /**
     * This interface will be use to enable the CardView listener for movie items.
     * This follows the Observer Design pattern
     */
    public interface MovieItemClickListner {
        void onItemClicked(Movie movie);

    }

    /**
     * ViewHolder for movie row items.
     * <p/>
     * This is an inner class of ViewHolder type which will be responsible to create the hold the view items at runtime. This also implements
     * View.OnClickListener interface which is help full to make the CardView clickable event.
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView = null;
        ImageView poster = null;
        TextView title = null;
        RatingBar rating = null;

        public MovieViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            poster = (ImageView) itemView.findViewById(R.id.movie_row_poster);
            title = (TextView) itemView.findViewById(R.id.movie_row_title);
            rating = (RatingBar) itemView.findViewById(R.id.movie_row_rating);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListner.onItemClicked(mMovieList.get(getPosition()));
        }
    }
}

