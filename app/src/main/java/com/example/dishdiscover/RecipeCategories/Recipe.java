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
    MealFacts mealFacts;
    List<Ingredient> ingredients = new ArrayList<Ingredient>();
    List<Step> steps = new ArrayList();
    Nutrition nutrition;

    public Recipe(JSONObject newRecipe, android.content.res.Resources resources,String packagename) {
        try {
                setMealFacts(newRecipe.getJSONObject("MealFacts"),resources,packagename);
                setIngredients(newRecipe.getJSONArray("Ingredients"));
                setSteps(newRecipe.getJSONArray("Steps"));
                setNutrition(newRecipe.getJSONObject("Nutrition"));
            } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    protected Recipe(Parcel in) {
        this.nutrition = in.readParcelable(Nutrition.class.getClassLoader());
        this.mealFacts = in.readParcelable(MealFacts.class.getClassLoader());
        this.steps = in.readArrayList(Step.class.getClassLoader());
        this.ingredients = in.readArrayList(Ingredient.class.getClassLoader());
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

    public String getRecipeName() {
        return mealFacts.getMealName();
    }


    public int getRecipeImage() {
        return this.mealFacts.getMealImage();
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(JSONArray ingredientArray) {
        Ingredient ingredient;
        for(int i=0; i<ingredientArray.length(); i++){
            try {
                ingredient = new Ingredient(ingredientArray.getJSONObject(i));
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            this.ingredients.add(ingredient);
        }
    }
    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(JSONArray stepsRaw) {
        try {

            JSONObject stepsRaw = reciperaw.getJSONObject("Steps");
            Iterator<String> keys = stepsRaw.keys();
            Step tempStep;
            for (int i=0;i<stepsRaw.length();i++) {
                tempStep = new Step();
                tempStep.number = stepsRaw.getJSONObject(i).getInt("Number");
                tempStep.action = stepsRaw.getJSONObject(i).getString("Action");
                tempStep.stepImage = stepsRaw.getJSONObject(i).getString("StepImage");
                this.steps.add(tempStep);
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public MealFacts getMealFacts() {return this.mealFacts;}

    public void setMealFacts(JSONObject mealFactsRaw, android.content.res.Resources resources,String packagename){
        this.mealFacts = new MealFacts(mealFactsRaw,resources,packagename);
    }
    public Nutrition getNutrition() {return this.nutrition;}

    public void setNutrition(JSONObject nutritionRaw){
        this.nutrition = new Nutrition(nutritionRaw);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(nutrition,flags);
        dest.writeParcelable(mealFacts,flags);
        dest.writeList(steps);
        dest.writeList(ingredients);
    }
}


