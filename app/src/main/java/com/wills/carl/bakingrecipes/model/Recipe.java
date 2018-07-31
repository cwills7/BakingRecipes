package com.wills.carl.bakingrecipes.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable{

    private int id;
    private String name;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Step> steps;
    private int servings;
    private String imageUrl;

    public Recipe(int id, String name, int servings, String imageUrl){
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
        this.ingredients = new ArrayList<>();
        this.steps = new ArrayList<>();
    }

    public Recipe(int id, String name, int servings, String imageUrl, ArrayList<Ingredient> ingredients, ArrayList<Step> steps){
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public void addIngredient (Ingredient ingredient){
        ingredients.add(ingredient);
    }

    public void addStep (Step step){
        steps.add(step);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public ArrayList<Step> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Step> steps) {
        this.steps = steps;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public String printIngredients(){
        String result = "Ingredients for " + name + ": \n";
        for(Ingredient i : ingredients){
            result = result + "  " + i.getQuantity() + " " + i.getMeasure() + "  " +i.getName()+ "\n";
        }
        return result;
    }

}
