package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecipeBook implements Parcelable {
    ArrayList<Recipe> book = new ArrayList<Recipe>();
    public RecipeBook(String jsonString) {
        JSONArray recipesraw;
        JSONObject recipe;
        Recipe newRecipe;
        try {
            recipesraw = new JSONObject(jsonString).getJSONArray("Recipes");
            for (int i=0; i < recipesraw.length(); i++) {
                recipe = recipesraw.getJSONObject(i).getJSONObject("Recipe");
                newRecipe = new Recipe(recipe);
                this.book.add(newRecipe);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Creator<RecipeBook> CREATOR = new Creator<RecipeBook>() {
        @Override
        public RecipeBook createFromParcel(Parcel in) {
            return new RecipeBook(in);
        }

        @Override
        public RecipeBook[] newArray(int size) {
            return new RecipeBook[size];
        }
    };

    public JSONObject getRecipeBookJSON() throws JSONException {
        JSONObject recipeBook = new JSONObject();
        JSONArray recipes = new JSONArray();
        JSONObject recipe;
        ArrayList<Recipe> book = getBook();
        for (int i=0; i < book.size(); i++) {
            recipe = book.get(i).getRecipeJson();
            recipes.put(recipe);
        }
        recipeBook.put("Recipes",recipes);
        return recipeBook;
    }
    public ArrayList<Recipe> getBook() {
        return book;
    }
    public int bookSize(){
        return book.size();
    }
    protected RecipeBook(Parcel in) {
        this.book = in.readArrayList(RecipeBook.class.getClassLoader());
    }

    public void addRecipe(Recipe recipe){
        this.book.add(recipe);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(book);
    }
}
