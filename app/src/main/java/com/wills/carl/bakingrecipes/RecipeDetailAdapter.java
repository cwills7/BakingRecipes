package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wills.carl.bakingrecipes.model.Ingredient;
import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.model.Step;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder>{

    private final LayoutInflater inflater;
    private final ArrayList<Step> steps;
    private final ArrayList<Ingredient> ingredients;


    public RecipeDetailAdapter(Context c, ArrayList<Step> stepList, ArrayList<Ingredient> ingredients){
        this.inflater = LayoutInflater.from(c);
        this.steps = stepList;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.recipe_detail_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (position > 0) {
            final Step step = steps.get(position-1);
            Log.d("DEBUG", "Adding Step! " + step.getShortDesc());
            holder.ingredientTv.setText(step.getId() + ". " + step.getShortDesc());
            holder.ingredientTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailIntent = new Intent(v.getContext(), StepDetail.class);
                    detailIntent.putExtra("stepList", steps);
                    detailIntent.putExtra("currentStep", step);
                    v.getContext().startActivity(detailIntent);
                }
            });
        } else{
            holder.ingredientTv.setText(printIngredients());
            Log.d("DEBUG", "Adding Ingreds! ");
        }

    }

    @Override
    public int getItemCount() {
        return steps.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView ingredientTv;
        private ViewHolder(final View v){
            super(v);
            ingredientTv = v.findViewById(R.id.ingredients_tv);
        }
    }

    private String printIngredients(){
        String result = "INGREDIENTS: \n";
        for(Ingredient i : ingredients){
            result = result + "  " + i.getQuantity() + " " + i.getMeasure() + "  " +i.getName()+ "\n";
        }
        return result;
    }


}
