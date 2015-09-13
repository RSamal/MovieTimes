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
package com.udacity.movietimes.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.udacity.movietimes.R;
import com.udacity.movietimes.model.Movie;
import com.udacity.movietimes.model.Movies;
import com.udacity.movietimes.model.Reviews;
import com.udacity.movietimes.model.Trailer;
import com.udacity.movietimes.utils.MovieUrl;
import com.udacity.movietimes.utils.MovieUtility;
import com.udacity.movietimes.webservices.MovieApiEndPoint;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = MovieSyncAdapter.class.getSimpleName();
    // Interval at which to sync with the weather, in milliseconds.
    // 60 seconds (1 minute) * 180 = 3 hours
    public static final int SYNC_INTERVAL = 60 * 180;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    public MovieSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(MovieUrl.BASE_URL).build();
        final MovieApiEndPoint apiService = restAdapter.create(MovieApiEndPoint.class);
        final MovieApiEndPoint simpleService = restAdapter.create(MovieApiEndPoint.class);

        // Fetch the High rated movie record from MovieDb Api and load them to all the tables
        final String highRate = getContext().getResources().getString(R.string.sort_votes);

        apiService.getTopMovies(highRate, new Callback<Movies>() {
            @Override
            public void success(Movies movies, Response response) {

                List<Movie> movieList = movies.getMovieList();
                MovieUtility.storeMovies(getContext(), movieList, highRate);

                for (final Movie movie : movieList) {

                    Log.d(LOG_TAG, "Movie Id: " + movie.getmId());

                    apiService.getMovieTrailers(movie.getmId(), new Callback<Trailer>() {
                                @Override
                                public void success(Trailer trailer, Response response) {
                                    MovieUtility.storeTrailers(getContext(), movie.getmId(), trailer.getTrailerList());

                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            }

                    );

                    try {
                        Thread.currentThread().sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    apiService.getMovieReviews(movie.getmId(), new Callback<Reviews>()

                            {
                                @Override
                                public void success(Reviews reviews, Response response) {
                                    MovieUtility.storeReviews(getContext(), movie.getmId(), reviews.getReviewList());
                                }

                                @Override
                                public void failure(RetrofitError error) {

                                }
                            }

                    );
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        // Fetch the popular movie record from MovieDb Api and load them to all the tables
        final String popular = getContext().getResources().getString(R.string.sort_popularity);

        simpleService.getTopMovies(popular, new Callback<Movies>() {

            @Override
            public void success(Movies movies, Response response) {

                List<Movie> movieList = movies.getMovieList();
                MovieUtility.storeMovies(getContext(), movieList, popular);
                ;
                for (final Movie movie : movieList) {


                    apiService.getMovieTrailers(movie.getmId(), new Callback<Trailer>() {
                        @Override
                        public void success(Trailer trailer, Response response) {
                            MovieUtility.storeTrailers(getContext(), movie.getmId(), trailer.getTrailerList());

                        }

                        @Override
                        public void failure(RetrofitError error) {
                        }
                    });

                    apiService.getMovieReviews(movie.getmId(), new Callback<Reviews>() {
                        @Override
                        public void success(Reviews reviews, Response response) {
                            MovieUtility.storeReviews(getContext(), movie.getmId(), reviews.getReviewList());
                        }

                        @Override
                        public void failure(RetrofitError error) {

                        }
                    });
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });


        return;
    }


    /**
     * Helper method to have the sync adapter sync immediately
     *
     * @param context The context used to access the account service
     */
    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }

    /**
     * Helper method to get the fake account to be used with SyncAdapter, or make a new one
     * if the fake account doesn't exist yet.  If we make a new account, we call the
     * onAccountCreated method so we can initialize things.
     *
     * @param context The context used to access the account service
     * @return a fake account.
     */
    public static Account getSyncAccount(Context context) {
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        // If the password doesn't exist, the account doesn't exist
        if (null == accountManager.getPassword(newAccount)) {

        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
             * here.
             */
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    /**
     * Helper method to schedule the sync adapter periodic execution
     */
    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        /*
         * Since we've created an account
         */
        MovieSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}