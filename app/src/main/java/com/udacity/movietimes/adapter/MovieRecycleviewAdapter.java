package com.udacity.movietimes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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
 */
public class MovieRecycleviewAdapter extends RecyclerView.Adapter<MovieRecycleviewAdapter.MovieViewHolder> {

    private static final String TAG = MovieRecycleviewAdapter.class.getSimpleName();

    private List<Movie> mMovieList;
    private Context mContext;
    protected MovieItemClickListner mListner;

    public MovieRecycleviewAdapter(Context context, MovieItemClickListner listner,List<Movie> movieList) {

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

        /**
         *  Set the Poster of the Movie
         */

        // Get the IMAGE url
        StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                .append(mMovieList.get(position).getmPosterPath());


        // Set the Image url using Volley
        holder.poster.buildDrawingCache();
        Picasso.with(mContext).load(imagePath.toString()).into(holder.poster);
        /**
         * Set the title of the Movie
         */
        holder.title.setText(mMovieList.get(position).getmTitle());

        /**
         * Set the Rating of the Movie
         */
        holder.rating.setRating(Float.valueOf(mMovieList.get(position).getmVoteAvg()) / 2);

        /**
         * Setting the Palete color of card view
         */

        holder.cardView.setCardBackgroundColor(new ColorGenerator(mContext).getBackgroundColor());

    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
        return bitmap;
    }

    public  class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

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

    /**
     * This interface will be use to enable the CardView listner for movie items.
     * This follows the Observer Design pattern
     */
    public static interface MovieItemClickListner{
        public void onItemClicked(Movie movie);

    }
}

