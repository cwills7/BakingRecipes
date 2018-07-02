package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.carl.bakingrecipes.model.Recipe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

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
        View v = inflater.inflate(R.layout.recipe_card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Recipe recipe = recipes.get(position);
        holder.cardNameTv.setText(recipe.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(v.getContext(), RecipeDetail.class);
                detailIntent.putExtra("recipe", recipe);
                v.getContext().startActivity(detailIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.recipe_card_name) TextView cardNameTv;
        @BindView(R.id.recipe_card_rv) CardView cardView;
        private ViewHolder(final View v){
            super(v);
            ButterKnife.bind(this, v);

        }
    }


}
