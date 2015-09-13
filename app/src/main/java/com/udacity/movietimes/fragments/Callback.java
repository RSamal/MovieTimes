package com.udacity.movietimes.fragments;

/**
 * Created by ramakant on 9/13/2015.
 */

import android.net.Uri;

/**
 * A callback interface that all activities containing this fragment must
 * implement. This mechanism allows activities to be notified of item
 * selections.
 */
public interface Callback {
    /**
     * DetailFragmentCallback for when an item has been selected.
     */
    public void onItemSelected(String movieId);
}
