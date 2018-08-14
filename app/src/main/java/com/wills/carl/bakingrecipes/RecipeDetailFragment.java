package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

public class RecipeDetailFragment extends Fragment {

    RecyclerView recipeDetailRv;
    Recipe recipe;
    RecipeDetailAdapter recipeDetailAdapter;
    boolean twoPane = false;
    OnStepClick mCallback;


    public interface OnStepClick {
        void onClick(Step step, ArrayList<Step> stepList);
        }

    public RecipeDetailFragment(){
        //Empty constructor for fragment creation
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        //Make sure we have implemented callback
        try {
            mCallback = (OnStepClick) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onStepClick");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        if (getArguments() != null){
            recipe = (Recipe) getArguments().getSerializable("recipe");
            twoPane = getArguments().getBoolean("twoPane");
        }



        View root = inflater.inflate(R.layout.recipe_detail_fragment, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.VERTICAL, false);

        recipeDetailRv = (RecyclerView) root.findViewById(R.id.recipe_detail_rv);
        layoutManager.canScrollVertically();
        recipeDetailRv.setLayoutManager(layoutManager);
        recipeDetailAdapter = new RecipeDetailAdapter(root.getContext(), recipe.getSteps(), recipe.getIngredients(), twoPane, mCallback);
        recipeDetailRv.setAdapter(recipeDetailAdapter);


        return root;
    }

}
