package com.wills.carl.bakingrecipes;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.wills.carl.bakingrecipes.model.Step;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;

public class StepDetailFragment extends Fragment{

    @Nullable
    @BindView(R.id.step_instructions) TextView instructions;
    @BindView(R.id.exo_pv) SimpleExoPlayerView playerView;
    @BindView(R.id.step_image) ImageView imageView;
    @Nullable
    @BindView(R.id.back_button) Button backButton;
    @Nullable
    @BindView(R.id.next_button) Button nextButton;

    Step step;
    ArrayList<Step> stepList;
    SimpleExoPlayer player;
    int currentId;
    boolean playWhenReady = false;
    long playerPos = 0;
    boolean twoPane;
    View root;

    public StepDetailFragment() {

    }


    @Override
    public void onCreate (Bundle state){
        super.onCreate(state);

        if (getArguments() != null){
            step = (Step) getArguments().getSerializable("step");
            stepList = (ArrayList<Step>) getArguments().getSerializable("stepList");
            playWhenReady = (Boolean) getArguments().getBoolean("playWhenReady");
            playerPos = (Long) getArguments().getLong("playerPosition");
            twoPane = (Boolean) getArguments().getBoolean("twoPane");
            currentId = step.getId();
        }

        Log.e("FRAG", "Creating Detail Fragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.step_detail_fragment, container, false);
           ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        Log.e("CREATION", "OnViewCreated in StepDetailFragment");
        playerView.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("step")) {
                step = (Step) savedInstanceState.getSerializable("step");
                stepList = (ArrayList<Step>) savedInstanceState.getSerializable("stepList");
                playWhenReady = (Boolean) savedInstanceState.getBoolean("playWhenReady");
                playerPos = (Long) savedInstanceState.getLong("playerPosition");
                currentId = step.getId();
            } else {
                playWhenReady = false;
                playerPos = 0;
            }
        } else {
            if (getArguments().getSerializable("step") != null){
                step = (Step) getArguments().getSerializable("step");
                stepList = (ArrayList<Step>) getArguments().getSerializable("stepList");
                playWhenReady = (Boolean) getArguments().getBoolean("playWhenReady");
                playerPos = (Long) getArguments().getLong("playerPosition");
                currentId = step.getId();
            }
        }
//        if (twoPane) {
//            backButton.setVisibility(View.INVISIBLE);
//            nextButton.setVisibility(View.INVISIBLE);
//        }

        if(backButton != null) {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    step = stepList.get(currentId - 1);
                    currentId--;
                    checkBounds(currentId);
                    handleMedia();
                }
            });

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    step = stepList.get(currentId + 1);
                    currentId++;
                    checkBounds(currentId);
                    handleMedia();
                }
            });
        }

        handleMedia();
    }

    private void handleMedia() {
        if(step.getVideoUrl() != null && !step.getVideoUrl().isEmpty()) {
            preparePlayer();
        } else if (step.getThumbnailUrl() != null && !step.getThumbnailUrl().isEmpty()) {
            prepareIV(step.getThumbnailUrl());
        } else{
            Log.d("EXO:", "Video URL is empty!!");
        }


        if(instructions != null) {
            instructions.setText(step.getDescription());
        }
    }

    @Override
    public void onStop(){
        super.onStop();


        if (player !=null){
            player.release();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putSerializable("step", step);
        state.putSerializable("stepList", stepList);
        if (player != null) {
            state.putLong("playerPosition", player.getCurrentPosition());
            state.putBoolean("playWhenReady", player.getPlayWhenReady());
        }
    }

    private void preparePlayer() {
       try {
           Log.e("PLAYER", "Preparing Player");
           playerView.setVisibility(View.VISIBLE);
           Uri uri = Uri.parse(step.getVideoUrl());

           BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
           TrackSelection.Factory videoTrackFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
           TrackSelector trackSelector = new DefaultTrackSelector(videoTrackFactory);

           player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl());

           playerView.requestFocus();
           playerView.setPlayer(player);

           DataSource.Factory dsf = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingRecipes"));
           MediaSource source = new ExtractorMediaSource.Factory(dsf)
                   .createMediaSource(uri);

           player.seekTo(playerPos);
           player.setPlayWhenReady(playWhenReady);


           player.prepare(source, false, false);



       } catch(Exception e){
           //In case the url wasn't to a video
           player.release();
           playerView.setVisibility(View.GONE);
           prepareIV(step.getVideoUrl());
       }
    }

    public void setStep(Step step){
        this.step = step;
    }

    private void prepareIV(String urlString){
        try {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(this.getContext())
                    .load(urlString)
                    .into(imageView);
        }catch (Exception e){
            //In case the url wasn't to an image
            imageView.setVisibility(View.GONE);
            Log.e("Step", "Cannot parse step media");
        }
    }

    private void checkBounds(int currentId){
        backButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        if (currentId <= 0){
            backButton.setVisibility(View.INVISIBLE);
        } else if (currentId == stepList.size() -1) {
            nextButton.setVisibility(View.INVISIBLE);
        }

    }
}
