package com.example.dishdiscover.RecipeCategories;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecipeBook {
    ArrayList<Recipe> book = new ArrayList<Recipe>();
    public RecipeBook(String jsonString,android.content.res.Resources resource,String packagename) {
        JSONArray recipesraw;
        JSONObject recipe;
        Recipe newRecipe;
        try {
            recipesraw = new JSONObject(jsonString).getJSONArray("Recipes");
            for (int i=0; i < recipesraw.length(); i++) {
                recipe = recipesraw.getJSONObject(i).getJSONObject("Recipe");
                newRecipe = new Recipe(recipe, resource,packagename);
                this.book.add(newRecipe);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Recipe> getBook() {
        return book;
    }
}
