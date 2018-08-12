package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.widget.GridView;

import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.ParseJSON;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import android.support.test.espresso.IdlingResource;


import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recipe_card_rv) RecyclerView mrecipeCardRv;

    private static RecipeItemAdapter recipeItemAdapter;
    private static ArrayList<Recipe> recipeList;
    boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BakingWidgetProvider.WidgetBroadcastReceiver br = new BakingWidgetProvider.WidgetBroadcastReceiver();
        IntentFilter intfil = new IntentFilter("android.appwidget.action.APPWIDGET_UPDATE");
        registerReceiver(br, intfil);

        twoPane = false;
        if (findViewById(R.id.twoPaneInd) != null){
            twoPane = true;
        }

        try{
            recipeList = new ArrayList<>();
            new RecipeAsyncTask().execute();
        } catch (NullPointerException e){
            Log.e("ERROR", e.getMessage());
        }

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



    private static class RecipeAsyncTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... params){
            URL url = NetworkUtils.buildRecipeUrl();
            String results = null;
            try{
                results = NetworkUtils.getHttpResponse(url);
            } catch(IOException e){
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(String queryResults){
            recipeList = ParseJSON.parseJson(queryResults);
            recipeItemAdapter.setData(recipeList);

        }
    }

}
