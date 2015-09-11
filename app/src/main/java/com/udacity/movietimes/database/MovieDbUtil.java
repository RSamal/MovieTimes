//package com.udacity.movietimes.database;
//
//import android.content.ContentValues;
//import android.content.Context;
//
//import com.udacity.movietimes.model.Movie;
//import com.udacity.movietimes.database.MovieContract.MovieEntry;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Vector;
//
///**
// * Created by ramakant on 9/7/2015.
// */
//public class MovieDbUtil {
//
//    private static Vector<ContentValues> mCvVector = null;
//
//    public static void loadMovieDatabase(List<Movie> movies, String choice,Context context){
//        mCvVector = new Vector<ContentValues>(movies.size());
//
//        for (int i = 0; i < movies.size(); i++) {
//
//            ContentValues value = new ContentValues();
//
//            value.put(MovieEntry.COLUMN_MOVIE_ID,movies.get(i).getmId());
//            value.put(MovieEntry.COLUMN_TITLE,movies.get(i).getmTitle());
//            value.put(MovieEntry.COLUMN_RELEASE_DATE ,movies.get(i).getmReleaseDate());
//            value.put(MovieEntry.COLUMN_POSTER_PATH ,movies.get(i).getmPosterPath());
//            value.put(MovieEntry.COLUMN_RATING ,movies.get(i).getmVoteAvg());
//            value.put(MovieEntry.COLUMN_TRAILER_ID  , "TEST");
//            value.put(MovieEntry.COLUMN_OVERVIEW ,movies.get(i).getmOverview());
//            switch (choice){
//                case MovieEntry.POPULAR_MOVIE:
//                    value.put(MovieEntry.COLUMN_POPULAR ,"Y");
//                    value.put(MovieEntry.COLUMN_HIGH_RATE  ,"N");
//                    value.put(MovieEntry.COLUMN_FAVORITE   ,"N");
//                    break;
//                case MovieEntry.HIGH_RATE_MOVIE:
//                    value.put(MovieEntry.COLUMN_POPULAR ,"N");
//                    value.put(MovieEntry.COLUMN_HIGH_RATE  ,"Y");
//                    value.put(MovieEntry.COLUMN_FAVORITE   ,"N");
//                    break;
//                default:
//                    value.put(MovieEntry.COLUMN_POPULAR ,"N");
//                    value.put(MovieEntry.COLUMN_HIGH_RATE  ,"N");
//                    value.put(MovieEntry.COLUMN_FAVORITE   ,"N");
//
//            }
//
//            mCvVector.add(value);
//        }
//
//        // add to the database
//        if (mCvVector.size() > 0){
//            ContentValues[] cvArray = new ContentValues[mCvVector.size()];
//            mCvVector.toArray(cvArray);
//            context.getContentResolver().bulkInsert(MovieEntry.CONTENT_URI,cvArray);
//        }
//    }
//
//    public static List<Movie> convertContentValuesToMovie(Vector<ContentValues> values) {
//
//        List<Movie> movies = new ArrayList<Movie>(values.size());
//
//        for (int i = 0; i < values.size(); i++) {
//            ContentValues value = values.get(i);
//            Movie movie = new Movie();
//            movie.setmId(value.getAsString(MovieEntry.COLUMN_MOVIE_ID));
//            movie.setmTitle(value.getAsString(MovieEntry.COLUMN_TITLE));
//            movie.setmReleaseDate(value.getAsString(MovieEntry.COLUMN_RELEASE_DATE));
//            movie.setmPosterPath(value.getAsString(MovieEntry.COLUMN_POSTER_PATH));
//            movie.setmVoteAvg(value.getAsString(MovieEntry.COLUMN_RATING));
//            movie.setmOverview(value.getAsString(MovieEntry.COLUMN_OVERVIEW));
//            movies.add(movie);
//        }
//
//        return movies;
//    }
//}
