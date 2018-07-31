package com.wills.carl.bakingrecipes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.GridView;

import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.ParseJSON;

import java.util.ArrayList;
import android.support.test.espresso.IdlingResource;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_card_rv) RecyclerView mrecipeCardRv;

    private RecipeItemAdapter recipeItemAdapter;
    private ArrayList<Recipe> recipeList;
    boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        twoPane = false;
        if (findViewById(R.id.twoPaneInd) != null){
            twoPane = true;
        }

        recipeList = ParseJSON.parseJson(this);

        mrecipeCardRv.setHasFixedSize(true);
        GridLayoutManager layoutManager;
        if (twoPane){
            layoutManager = new GridLayoutManager(this, 3);
        } else {
            layoutManager = new GridLayoutManager(this, 1);
        }
        layoutManager.canScrollVertically();
        mrecipeCardRv.setLayoutManager(layoutManager);
        recipeItemAdapter = new RecipeItemAdapter(this, recipeList);
        mrecipeCardRv.setAdapter(recipeItemAdapter);


    }

}
