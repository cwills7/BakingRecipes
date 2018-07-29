package com.wills.carl.bakingrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetail extends AppCompatActivity implements RecipeDetailFragment.OnStepClick{

    boolean twoPane = false;
    Recipe recipe;
    @Nullable
    @BindView(R.id.step_detail_frag)
    FrameLayout stepDetailFrag;

    @BindView(R.id.recipe_detail_frag) FrameLayout recipeDetailFrag;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        ButterKnife.bind(this);


        if (stepDetailFrag != null){
            twoPane = true;
        }

        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        bundle.putBoolean("twoPane", twoPane);

        //Create Fragments and add them.
        //Check to make sure the screen orientation.


        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
                .add(recipeDetailFrag.getId(), recipeDetailFragment)
                .commit();

        if (twoPane){
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            bundle.putSerializable("step", recipe.getSteps().get(0));
            stepDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(stepDetailFrag.getId(), stepDetailFragment)
                    .commit();
        }


    }

    @Override
    public void onClick(Step step, ArrayList<Step> stepList) {

        if (twoPane) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("step", step);
            bundle.putBoolean("twoPane", twoPane);
            FragmentManager fm = getSupportFragmentManager();
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            fm.beginTransaction()
                    .replace(stepDetailFrag.getId(), stepDetailFragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, StepDetail.class);
            detailIntent.putExtra("stepList", stepList);
            detailIntent.putExtra("currentStep", step);
            this.startActivity(detailIntent);
        }

    }
}
