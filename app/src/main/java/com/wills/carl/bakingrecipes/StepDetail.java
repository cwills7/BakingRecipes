package com.wills.carl.bakingrecipes;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepDetail extends AppCompatActivity {

    @BindView(R.id.back_button) Button backBtn;
    @BindView(R.id.next_button) Button nextBtn;

    int currentId;
    ArrayList<Step> stepList;
    Step step;

    boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.step_detail);
        ButterKnife.bind(this);

        if (savedInstanceState != null){
            step = (Step) savedInstanceState.getSerializable("currentStep");
            currentId = (int) savedInstanceState.getInt("currentId");

        }
        else {
            step = (Step) getIntent().getSerializableExtra("currentStep");
            currentId = step.getId();

        }

        stepList = (ArrayList<Step>) getIntent().getSerializableExtra("stepList");
        twoPane = false;

        checkBounds(currentId);

        Bundle bundle = new Bundle();
        bundle.putSerializable("step", step);

        if (findViewById(R.id.step_detail_frag) != null){
            twoPane = true;
        }

        updateFragments(bundle);

        //Create Fragments and add them.
        //Check to make sure the screen orientation.

        if(twoPane){
            backBtn.setVisibility(View.INVISIBLE);
            nextBtn.setVisibility(View.INVISIBLE);
        } else {
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable("step", stepList.get(currentId - 1));
                    updateFragments(b);
                    currentId--;
                    checkBounds(currentId);
                }
            });

            nextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle b = new Bundle();
                    b.putSerializable("step", stepList.get(currentId + 1));
                    updateFragments(b);
                    currentId++;
                    checkBounds(currentId);
                }
            });
        }



    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onSaveInstanceState(Bundle state){
        super.onSaveInstanceState(state);
        state.putSerializable("currentStep", stepList.get(currentId));
        state.putInt("currentId", currentId);
    }



    private void updateFragments(Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        StepDetailFragment stepDetailFragment = new StepDetailFragment();
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        stepDetailFragment.setArguments(bundle);
        if(!twoPane) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.step_container, stepDetailFragment)
                    .commit();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.step_container, recipeDetailFragment)
                    .replace(R.id.step_detail_frag, stepDetailFragment)
                    .commit();
        }
    }

    private void checkBounds(int currentId){
        backBtn.setVisibility(View.VISIBLE);
        nextBtn.setVisibility(View.VISIBLE);
        if (currentId <= 0){
            backBtn.setVisibility(View.INVISIBLE);
        } else if (currentId == stepList.size() -1) {
            nextBtn.setVisibility(View.INVISIBLE);
        }

    }


}
