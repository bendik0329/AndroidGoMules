package com.amits.gomules;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.amits.gomules.Utils.YouTubeConfig;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

public class AboutUsFragment extends Fragment {

    Button btnPlay;

    YouTubePlayerSupportFragment youTubePlayerFragment;
    private static final String YoutubeAPI_KEY = YouTubeConfig.getApiKey();
    // Go to https://console.developers.google.com/ and Use your Google acc tounto lo gin
    // Use package name and SHA1 of your app to create Youtube API key
    //add the key here

    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);

        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtubeplayer, youTubePlayerFragment).commit();

        btnPlay = (Button) view.findViewById(R.id.btnPlay);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Setting initializer for loading and playing the video.
                youTubePlayerFragment.initialize(YoutubeAPI_KEY, new YouTubePlayer.OnInitializedListener() {

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                        if (!wasRestored) {
                            player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                            player.loadVideo("MOJRWYzevLg");
                            player.play();
                        }
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
                        // YouTube error
                        // String errorMessage = error.toString();
                    }
                });
            }
        });
        return view;
    }

}