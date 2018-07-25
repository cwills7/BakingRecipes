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
    //@BindView(R.id.subtitle) TextView subtitle;
    //@BindView(R.id.footer) TextView footer;

    @Nullable
    private SimpleIdlerResource mIdlingResource;
    private RecipeItemAdapter recipeItemAdapter;
    private ArrayList<Recipe> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeList = ParseJSON.parseJson(this);

        mrecipeCardRv.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        layoutManager.canScrollVertically();
        mrecipeCardRv.setLayoutManager(layoutManager);
        recipeItemAdapter = new RecipeItemAdapter(this, recipeList);
        mrecipeCardRv.setAdapter(recipeItemAdapter);


    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlerResource();
        }
        return mIdlingResource;
    }
}
