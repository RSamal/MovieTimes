package com.udacity.movietimes.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.udacity.movietimes.R;
import com.udacity.movietimes.utils.MovieConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragment extends YouTubePlayerSupportFragment implements YouTubeThumbnailView.OnInitializedListener{

    private static final String TAG = YoutubeFragment.class.getSimpleName();

    private RelativeLayout mYoutubeVideo;
    private YouTubeThumbnailView mYoutubeThumbnail;
    private YouTubeThumbnailLoader mYoutubeThumbnailLoader;
    private String mViedoId;

    public YoutubeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube, container, false);

        Log.d(TAG,"1");
        mYoutubeVideo = (RelativeLayout) view.findViewById(R.id.youtube_fragment_vedio_play);
        mYoutubeThumbnail = (YouTubeThumbnailView) view.findViewById(R.id.youtube_fragment_youtube_tn);
        mYoutubeThumbnail.initialize("AIzaSyDYA5kvFYOnznjuTxsC8btURc4Y90IQ_iE",this);
        Log.d(TAG,"2");



        return view;
    }


    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {

        Log.d(TAG,"3");
        mYoutubeThumbnailLoader = youTubeThumbnailLoader;
        mYoutubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener());

        mYoutubeThumbnailLoader.setVideo("szEW6J1rPV4");
        Log.d(TAG, "4");

    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

        Log.d(TAG,"5");
        String errorMessage =
                String.format("onInitializationFailure",
                        youTubeInitializationResult.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        Log.d(TAG, "6");

    }


    public final class ThumbnailListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String videoId) {
            Log.d(TAG,"7");
            Toast.makeText(getActivity(),
                    "onThumbnailLoaded", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "8");
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView thumbnail,
                                     YouTubeThumbnailLoader.ErrorReason reason) {
            Log.d(TAG,"9");
            Toast.makeText(getActivity(),
                    "onThumbnailError", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "10");
        }
    }




}
