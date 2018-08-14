package com.wills.carl.bakingrecipes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetail extends AppCompatActivity {

    int currentId;
    ArrayList<Step> stepList;
    Step step;

    boolean twoPane;
    StepDetailFragment stepDetailFragment;
    RecipeDetailFragment recipeDetailFragment;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);
        ButterKnife.bind(this);

        Log.e("CREATION", "Creating Step Detail Class");

        bundle = savedInstanceState;

        twoPane = false;

        if (findViewById(R.id.step_detail_frag) != null){
            twoPane = true;
        }

        stepList = (ArrayList<Step>) getIntent().getSerializableExtra("stepList");
        step = (Step) getIntent().getSerializableExtra("currentStep");
        currentId = step.getId();
        bundle = new Bundle();
        bundle.putSerializable("stepList",stepList);
        bundle.putSerializable("step", step);
        bundle.putBoolean("playWhenReady", false);
        bundle.putLong("playerPosition", 0);
        bundle.putBoolean("twoPane", twoPane);



    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.e("DESTRUCTION", "Step Detail Activity Destroyed");
    }


    @Override
    public void onStart(){
        super.onStart();

        //Don't want to create the fragments again if we hit the back button
            stepDetailFragment = new StepDetailFragment();
            recipeDetailFragment = new RecipeDetailFragment();
            updateFragments(bundle);



        //Create Fragments and add them.
        //Check to make sure the screen orientation.

    }

    @Override
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putSerializable("currentStep", stepList.get(currentId));
        state.putInt("currentId", currentId);
    }



    private void updateFragments(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();


        stepDetailFragment.setArguments(bundle);
        if(!twoPane){
            if (fragmentManager.findFragmentByTag("StepDetail") == null) {
                Log.e("FRAG", "Replacing Detail Container");

                fragmentManager
                        .beginTransaction()
                        .replace(R.id.step_container, stepDetailFragment, "StepDetail")
                        .commit();
            }
        } else {
            Log.e("FRAG", "Replacing Two Pane Fragments");
            fragmentManager
                    .beginTransaction()
                    .add(R.id.step_container, recipeDetailFragment, "RecipeDetail")
                    .add(R.id.step_detail_frag, stepDetailFragment, "StepDetail")
                    .commit();
        }
    }




}
