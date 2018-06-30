package com.wills.carl.bakingrecipes;

import android.content.Context;
import android.util.Log;

import com.wills.carl.bakingrecipes.model.Ingredient;
import com.wills.carl.bakingrecipes.model.Recipe;
import com.wills.carl.bakingrecipes.model.Step;


import org.json.simple.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ParseJSON {

    public static ArrayList<Recipe> parseJson(Context c){
     ArrayList<Recipe> recipeList = new ArrayList<>();

        JSONParser parser = new JSONParser();
        try {
            BufferedReader inputData =  new BufferedReader(new InputStreamReader(c.getResources().openRawResource(R.raw.baking)));
            Writer out = new StringWriter();
            String line = inputData.readLine();
            while(line !=null){
                out.write(line);
                line = inputData.readLine();
            }
            JSONArray inputJson = (JSONArray) parser.parse(out.toString());

            for (Object obj: inputJson) {
                JSONObject rec = (JSONObject) obj;

                int id = Math.toIntExact((long) rec.get("id"));
                String name = (String) rec.get("name");
                int servings = Math.toIntExact((long) rec.get("servings"));
                String image = (String) rec.get("image");
                Recipe recipe = new Recipe(id, name, servings, image);
                JSONArray ingredients = (JSONArray) rec.get("ingredients");
                for (Object ingred : ingredients) {
                    JSONObject ing = (JSONObject) ingred;
                    double quantity;
                    try{
                         quantity = Long.valueOf((long) ing.get("quantity")).doubleValue();
                    }catch (ClassCastException e){
                        quantity  = (double) ing.get("quantity");
                    }
                    //double quantity = Long.valueOf(longQuantity).doubleValue();
                    String measure = (String) ing.get("measure");
                    String ingredName = (String) ing.get("ingredient");
                    recipe.addIngredient(new Ingredient(quantity, measure, ingredName));
                }
                JSONArray steps = (JSONArray) rec.get("steps");
                for (Object s : steps) {
                    JSONObject step = (JSONObject) s;
                    int stepId = Math.toIntExact((long) step.get("id"));
                    String shortDescription = (String) step.get("shortDescription");
                    String description = (String) step.get("description");
                    String videoUrl = (String) step.get("videoURL");
                    String thumbnailUrl = (String) step.get("thumbnailURL");
                    recipe.addStep(new Step(stepId, shortDescription, description, videoUrl, thumbnailUrl));
                }

                recipeList.add(recipe);

            }
        }catch (IOException e){
            Log.e("EXCEPTION", e.getMessage());

        } catch (ParseException e){
            Log.e("EXCEPTION", e.getMessage());

        }

     return recipeList;
    }

}
