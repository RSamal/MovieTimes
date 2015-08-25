package com.udacity.movietimes.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.webservices.ConnectionManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by ramakantasamal on 8/24/15.
 */
public class MovieRecycleviewAdapter extends RecyclerView.Adapter<MovieRecycleviewAdapter.MovieViewHolder> {

    private static final String TAG = MovieRecycleviewAdapter.class.getSimpleName();

    private List<Movie> movieList;
    private Context context;

    public MovieRecycleviewAdapter(Context context, List<Movie> movieList) {

        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //inflate the movie row of recycler view
        View view = LayoutInflater.from(context).inflate(R.layout.moviee_row, parent, false);

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
                .append(movieList.get(position).getmPosterPath());


        // Set the Image url using Volley
        holder.poster.buildDrawingCache();
        Picasso.with(context).load(imagePath.toString()).into(holder.poster);
        /**
         * Set the title of the Movie
         */
        holder.title.setText(movieList.get(position).getmTitle());

        /**
         * Set the Rating of the Movie
         */
        holder.rating.setText(movieList.get(position).getmVoteAvg());

        /**
         * Setting the Palete color of card view
         */


        Bitmap bitmap = loadBitmapFromView(holder.poster);
        Palette palette = Palette.generate(bitmap,24);


        // Getting the different types of colors from the Image
        Palette.Swatch vibrantSwatch = palette.getDarkMutedSwatch();


        float[] vibrant = vibrantSwatch.getHsl();

        holder.cardView.setBackgroundColor(Color.HSVToColor(vibrant));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.layout(0, 0, v.getLayoutParams().width, v.getLayoutParams().height);
        v.draw(canvas);
        return bitmap;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        CardView cardView = null;
        ImageView poster = null;
        TextView title = null;
        TextView rating = null;

        public MovieViewHolder(View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.card_view);
            poster = (ImageView) itemView.findViewById(R.id.movie_row_poster);
            title = (TextView) itemView.findViewById(R.id.movie_row_title);
            rating = (TextView) itemView.findViewById(R.id.movie_row_rating);
        }
    }



}
