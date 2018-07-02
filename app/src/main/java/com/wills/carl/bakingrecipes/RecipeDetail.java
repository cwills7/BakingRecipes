package com.wills.carl.bakingrecipes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.wills.carl.bakingrecipes.model.Recipe;

public class RecipeDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail_fragment);

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        Bundle bundle = new Bundle();
        bundle.putSerializable("recipe", recipe);

        //Create Fragments and add them.
        //Check to make sure the screen orientation.


        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeDetailFragment recipeDetailFragment = new RecipeDetailFragment();
        recipeDetailFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
            .add(R.id.detail_container, recipeDetailFragment).commit();


    }


}
