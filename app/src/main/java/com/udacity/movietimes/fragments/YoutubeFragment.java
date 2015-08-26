package com.udacity.movietimes.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.udacity.movietimes.R;
import com.udacity.movietimes.utils.MovieConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class YoutubeFragment extends YouTubePlayerFragment implements YouTubeThumbnailView.OnInitializedListener{

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


        mYoutubeVideo = (RelativeLayout) view.findViewById(R.id.youtube_fragment_vedio_play);
        mYoutubeThumbnail = (YouTubeThumbnailView) view.findViewById(R.id.youtube_fragment_youtube_tn);
        mYoutubeThumbnail.initialize(MovieConfig.GOOGLE_API_KEY,this);


        return view;
    }


    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        Toast.makeText(getActivity(),
                "onInitializationSuccess", Toast.LENGTH_SHORT).show();

        mYoutubeThumbnailLoader = youTubeThumbnailLoader;
        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailListener());

        youTubeThumbnailLoader.setVideo(mViedoId    );

    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

        String errorMessage =
                String.format("onInitializationFailure",
                        youTubeInitializationResult.toString());
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();

    }


    private final class ThumbnailListener implements
            YouTubeThumbnailLoader.OnThumbnailLoadedListener {

        @Override
        public void onThumbnailLoaded(YouTubeThumbnailView thumbnail, String videoId) {
            Toast.makeText(getActivity(),
                    "onThumbnailLoaded", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onThumbnailError(YouTubeThumbnailView thumbnail,
                                     YouTubeThumbnailLoader.ErrorReason reason) {
            Toast.makeText(getActivity(),
                    "onThumbnailError", Toast.LENGTH_SHORT).show();
        }
    }




}
