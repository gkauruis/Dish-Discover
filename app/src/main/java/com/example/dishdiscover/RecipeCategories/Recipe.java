package com.example.dishdiscover.RecipeCategories;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Recipe implements Parcelable {

    String recipeName;
    int recipeImage;
    List<Ingredient> ingredients = new ArrayList<Ingredient>();
    List<Step> steps = new ArrayList();

    public Recipe(String recipeName, int recipeImage) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
        this.ingredients = ingredients;
    }

    protected Recipe(Parcel in) {
        recipeName = in.readString();
        recipeImage = in.readInt();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(JSONObject reciperaw) {
        try {
            JSONObject stepsRaw = reciperaw.getJSONObject("Steps");
            Iterator<String> keys = stepsRaw.keys();
            Step tempStep = new Step();
            while(keys.hasNext()) {
                String key = keys.next();
                tempStep.number = key;
                if(keys.hasNext()) {
                    key = keys.next();
                    tempStep.action = key;
                }
                if(keys.hasNext()) {
                    key = keys.next();
                    tempStep.stepImage = key;
                }
                }
                steps.add(tempStep);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(int recipeImage) {
        this.recipeImage = recipeImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipeName);
        dest.writeInt(recipeImage);
    }
}


class Nutrition {
    double cal;
    double fat;
    double sugar;
    double salt;

    public Nutrition(double cal, double fat, double sugar, double salt){
        this.cal = cal;
        this.fat = fat;
        this.sugar = sugar;
        this.salt = salt;
    }
}

class MealFacts {
    String mealName;
    String mealImage;
    String mealDescription;
    String difficulty;
    double prep;
    double cooktime;
    int serves;
    double rating;
    String weburl;
    String comments;

    public MealFacts(String mealName,String mealImage,
                     String mealDescription, String difficulty,
                     double prep, double cooktime, int serves,
                     double rating, String weburl, String comments)
    {
        this.mealName = mealName;
        this.mealImage = mealImage;
        this.mealDescription = mealDescription;
        this.difficulty = difficulty;
        this.prep = prep;
        this.cooktime = cooktime;
        this.serves = serves;
        this.rating = rating;
        this.weburl = weburl;
        this.comments = comments;
    }
}