package com.wills.carl.bakingrecipes;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.wills.carl.bakingrecipes.model.Step;


import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetailFragment extends Fragment{

    @BindView(R.id.step_instructions) TextView instructions;
    @BindView(R.id.exo_pv) SimpleExoPlayerView playerView;

    Step step;
    SimpleExoPlayer player;

    public StepDetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (getArguments() != null){
            step = (Step) getArguments().getSerializable("step");
        }


        View root = inflater.inflate(R.layout.step_detail_fragment, container, false);
        ButterKnife.bind(this, root);

        if(step.getVideoUrl() != null) {
            preparePlayer();
        } else {
            Log.d("EXO:", "Video URL is empty!!");
        }




        instructions.setText(step.getDescription());

        return root;
    }

    @Override
    public void onStop(){
        super.onStop();
        if (player !=null){
            player.release();
        }
    }

    private void preparePlayer() {

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackFactory);

        player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, new DefaultLoadControl());

        playerView.requestFocus();
        playerView.setPlayer(player);

        DataSource.Factory dsf = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "BakingRecipes"));
        Uri uri = Uri.parse(step.getVideoUrl());
        MediaSource source = new ExtractorMediaSource.Factory(dsf)
                    .createMediaSource(uri);

        player.prepare(source);
        //player.setPlayWhenReady(true);

       // player.release();
    }



}
