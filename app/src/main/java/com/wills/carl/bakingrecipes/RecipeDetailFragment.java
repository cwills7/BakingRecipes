package com.wills.carl.bakingrecipes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wills.carl.bakingrecipes.model.Recipe;

public class RecipeDetailFragment extends Fragment {

    RecyclerView recipeDetailRv;
    Recipe recipe;
    RecipeDetailAdapter recipeDetailAdapter;
    public RecipeDetailFragment(){
        //Empty constructor for fragment creation
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if (getArguments() != null){
            recipe = (Recipe) getArguments().getSerializable("recipe");
        }

        View root = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        recipeDetailRv = (RecyclerView) root.findViewById(R.id.recipe_detail_rv);
        layoutManager.canScrollVertically();
        recipeDetailRv.setLayoutManager(layoutManager);
        recipeDetailAdapter = new RecipeDetailAdapter(root.getContext(), recipe.getSteps(), recipe.getIngredients());
        recipeDetailRv.setAdapter(recipeDetailAdapter);

        return root;
    }

}
