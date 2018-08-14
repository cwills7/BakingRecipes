package com.wills.carl.bakingrecipes;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

    BakingWidgetProvider.WidgetBroadcastReceiver broadcastReceiver;
    boolean broadcastReceiverIsRegistered;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);
        ButterKnife.bind(this);

        broadcastReceiver = new BakingWidgetProvider.WidgetBroadcastReceiver();

        if (stepDetailFrag != null){
            twoPane = true;
        }


        recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        storeIngredsInSharedPref(recipe);
        Intent i = new Intent("android.appwidget.action.APPWIDGET_UPDATE");
        sendBroadcast(i);

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);
        bundle.putBoolean("twoPane", twoPane);

        //Create Fragments and add them.
        //Check to make sure the screen orientation.
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        Log.e("FRAG", "Creating RecipeDetailFragment");
        fragmentManager.beginTransaction()
                .replace(recipeDetailFrag.getId(), recipeDetailFragment)
                .commit();

        if (twoPane){
            Log.e("FRAG", "Replacing Two Pane Fragments From RecipeDetail.java");
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            bundle.putSerializable("step", recipe.getSteps().get(0));
            stepDetailFragment.setArguments(bundle);
            fragmentManager.beginTransaction()
                    .replace(stepDetailFrag.getId(), stepDetailFragment)
                    .commit();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        if (!broadcastReceiverIsRegistered){
            registerReceiver(broadcastReceiver, new IntentFilter("android.appwidget.action.APPWIDGET_UPDATE"));
            broadcastReceiverIsRegistered = true;
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        if(broadcastReceiverIsRegistered){
            unregisterReceiver(broadcastReceiver);
            broadcastReceiverIsRegistered = false;
        }
    }

    @Override
    public void onClick(Step step, ArrayList<Step> stepList) {
        if (twoPane) {
            Log.e("FRAG", "Replacing Two Pane Fragments from RecipeDetail onClick");

            Bundle bundle = new Bundle();
            bundle.putSerializable("step", step);
            bundle.putBoolean("twoPane", twoPane);
            FragmentManager fm = getSupportFragmentManager();
            StepDetailFragment stepDetailFragment = new StepDetailFragment();
            stepDetailFragment.setArguments(bundle);
            fm.beginTransaction()
                    .add(stepDetailFrag.getId(), stepDetailFragment)
                    .commit();

        } else {
//            Log.e("FRAG", "Creating StepDetail.class");
//
//            Intent detailIntent = new Intent(this, StepDetail.class);
//            detailIntent.putExtra("stepList", stepList);
//            detailIntent.putExtra("currentStep", step);
//            this.startActivity(detailIntent);
        }

    }

    public void storeIngredsInSharedPref(Recipe recipe){
        //Used for Widget
        SharedPreferences prefs = getSharedPreferences("Prefs", 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("ingreds", recipe.printIngredients());
        edit.apply();



    }
}
