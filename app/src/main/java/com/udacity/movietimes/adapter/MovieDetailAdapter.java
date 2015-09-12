package com.udacity.movietimes.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.movietimes.R;
import com.udacity.movietimes.activities.DetailActivity;
import com.udacity.movietimes.fragments.DetailFragment;
import com.udacity.movietimes.utils.MovieUrl;

/**
 * This Adapter will be used to display the Movie details returned from JOIN operation of Movie, Trailer and Review
 * Created by ramakant on 9/11/2015.
 */
public class MovieDetailAdapter extends CursorAdapter {

    private static final String LOG_TAG = MovieDetailAdapter.class.getSimpleName();
    private static final int VIEW_TYPE_COUNT = 2;
    private static final int VIEW_TYPE_MOVIE_HEADER = 0;
    private static final int VIEW_TYPE_MOVIE_REVIEW = 1;

    public MovieDetailAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Cache of the children views for a forecast list item.
     */
    public static class ViewHolder {

        ImageView trailerImg;
        TextView trailerMsg;
        ImageView poster;
        TextView title;
        TextView favorite;
        TextView releaseDate;
        RatingBar rating;
        TextView overview;

        TextView author;
        TextView review;


        public ViewHolder(View view) {

            if (view.getId() == R.id.movie_fragment_header) {
                trailerImg = (ImageView) view.findViewById(R.id.movie_fragment_header_vedio_img);
                trailerMsg = (TextView) view.findViewById(R.id.movie_fragment_header_vedio_msg);
                poster = (ImageView) view.findViewById(R.id.movie_fragment_header_poster);
                title = (TextView) view.findViewById(R.id.movie_fragment_header_title);
                favorite = (TextView) view.findViewById(R.id.movie_fragment_header_favorite);
                releaseDate = (TextView) view.findViewById(R.id.movie_fragment_header_release_date);
                rating = (RatingBar) view.findViewById(R.id.movie_fragment_header_rating);
                overview = (TextView) view.findViewById(R.id.movie_fragment_header_overview);

            } else {
                author = (TextView) view.findViewById(R.id.movie_fragment_review_Author);
                review = (TextView) view.findViewById(R.id.movie_fragment_review_content);
            }
        }
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.d(LOG_TAG,Integer.toString(cursor.getCount()));
        // Choose the layout type
        int viewType = getItemViewType(cursor.getPosition());
        int layoutId = -1;
        switch (viewType) {
            case VIEW_TYPE_MOVIE_HEADER: {
                layoutId = R.layout.movie_fragment_header;
                break;
            }
            case VIEW_TYPE_MOVIE_REVIEW: {
                layoutId = R.layout.movie_fragment_review;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Log.d(LOG_TAG,cursor.getString(0));
        Log.d(LOG_TAG,cursor.getString(1));
        Log.d(LOG_TAG,cursor.getString(2));
        Log.d(LOG_TAG,cursor.getString(3));
        Log.d(LOG_TAG,cursor.getString(4));
        Log.d(LOG_TAG,cursor.getString(5));
        Log.d(LOG_TAG,cursor.getString(6));
        Log.d(LOG_TAG,cursor.getString(7));

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        int viewType = getItemViewType(cursor.getPosition());
        switch (viewType) {
            case VIEW_TYPE_MOVIE_HEADER: {


                StringBuilder imagePath = new StringBuilder(MovieUrl.MOVIE_IMAGE_BASE_URL)
                        .append(cursor.getString(DetailFragment.COL_POSTER_PATH));
                Log.d(LOG_TAG,imagePath.toString());
                Picasso.with(context).load(imagePath.toString()).into(viewHolder.poster);

                break;
            }
            case VIEW_TYPE_MOVIE_REVIEW: {
                // Get weather icon

                viewHolder.author.setText(cursor.getString(DetailFragment.COL_AUTHOR_NAME));
                viewHolder.review.setText(cursor.getString(DetailFragment.COL_REVIEW_CONTENT));

                break;
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0) ? VIEW_TYPE_MOVIE_HEADER : VIEW_TYPE_MOVIE_REVIEW;
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }
}
