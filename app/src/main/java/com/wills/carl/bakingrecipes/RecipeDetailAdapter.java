package com.wills.carl.bakingrecipes;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableLayout;
import android.widget.TextView;

import com.wills.carl.bakingrecipes.model.Ingredient;
import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.model.Step;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ViewHolder>{
    private final ArrayList<Step> steps;
    private final ArrayList<Ingredient> ingredients;
    private boolean twoPane = false;
    private Context c;
    private final LayoutInflater inflater;
    RecipeDetailFragment.OnStepClick mCallback;

    private TextView ingredientTv;


    public RecipeDetailAdapter(Context c, ArrayList<Step> stepList, ArrayList<Ingredient> ingredients, boolean twoPane, RecipeDetailFragment.OnStepClick mCallback){
        this.c = c;
        this.steps = stepList;
        this.ingredients = ingredients;
        this.twoPane = twoPane;
        this.inflater = LayoutInflater.from(c);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ingredientTv;
        private ViewHolder(final View v){
            super(v);
            ingredientTv = v.findViewById(R.id.ingredients_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = inflater.inflate(R.layout.recipe_detail_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,int position) {
        if (holder.ingredientTv == null){
            holder.ingredientTv = new TextView(c);
            holder.ingredientTv.setLayoutParams(new TableLayout.LayoutParams(R.dimen.fragment_width, TableLayout.LayoutParams.WRAP_CONTENT, 1f));
            holder.ingredientTv.setBackground(c.getResources().getDrawable(R.drawable.textview_border));
            holder.ingredientTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, c.getResources().getDimension(R.dimen.standard_caption));
        }



        if (position > 0) {
            final Step step = steps.get(position - 1);
            holder.ingredientTv.setText(step.getId() + ". " + step.getShortDesc());
            if (!twoPane) {
                Log.d("Debug", "Doesn't think we are two pane!");
                holder.ingredientTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent detailIntent = new Intent(v.getContext(), StepDetail.class);
                        detailIntent.putExtra("stepList", steps);
                        detailIntent.putExtra("currentStep", step);
                        v.getContext().startActivity(detailIntent);
                        mCallback.onClick(step, steps);
                    }
                });
            }
        } else{
            holder.ingredientTv.setText(printIngredients());
        }
    }


    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public int getItemCount() {
        return steps.size()+1;
    }


    private String printIngredients(){
        String result = "INGREDIENTS: \n";
        for(Ingredient i : ingredients){
            result = result + "  " + i.getQuantity() + " " + i.getMeasure() + "  " +i.getName()+ "\n";
        }
        return result;
    }


}
