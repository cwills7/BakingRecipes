package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wills.carl.bakingrecipes.model.Recipe;

import java.util.ArrayList;

public class RecipeItemAdapter extends RecyclerView.Adapter<RecipeItemAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final ArrayList<Recipe> recipes;


    public RecipeItemAdapter(Context c, ArrayList<Recipe> recipeList){
        this.inflater = LayoutInflater.from(c);
        recipes = recipeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        
        private ViewHolder(final View v){
            super(v);

        }
    }


}
