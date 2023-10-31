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

    public JSONObject getRecipeJson() throws JSONException {
        JSONObject recipejson = new JSONObject();
        JSONObject reciperaw = new JSONObject();
        reciperaw.put("MealFacts",this.mealFacts.getMealFactsJSON());
        reciperaw.put("Ingredients",getIngredientsJSON());
        reciperaw.put("Nutrition",nutrition.getNutritionJSON());
        reciperaw.put("Steps",getStepsJSON());
        recipejson.put("Recipe",reciperaw);
        return recipejson;

    }
    public int getRecipeImage() {
        return this.mealFacts.getMealImage();
    }

    // ---------------------------Ingredients---------------------------------------------------
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
    public JSONArray getIngredientsJSON() throws JSONException {
        JSONArray ingredientarray = new JSONArray();
        JSONObject ingredient;
        List<Ingredient> ingredients = getIngredients();
        for (int i=0; i<ingredients.size();i++) {
            ingredient = new JSONObject();
            ingredient.put("Ingredient",ingredients.get(i).getName());
            ingredient.put("Amount",ingredients.get(i).amt);
            ingredient.put("Unit",ingredients.get(i).getUnit());
            ingredientarray.put(ingredient);
        }
        return ingredientarray;
    }

    //-----------------------------Steps-----------------------------------------------
    public List<Step> getSteps() {
        return steps;
    }

    public Step getStep(int stepNum) {
        return steps.get(stepNum);
    }
    public int getStepAmt(){return steps.size();}
    public void setSteps(JSONArray stepsRaw) {
        try {
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
    public JSONArray getStepsJSON() throws JSONException {
        JSONArray steparray = new JSONArray();
        JSONObject step;
        List<Step> steps = getSteps();
        for (int i=0; i<steps.size();i++) {
            step = new JSONObject();
            step.put("Number",steps.get(i).getNumber());
            step.put("Action",steps.get(i).getAction());
            step.put("StepImage",steps.get(i).getStepImage());
            steparray.put(step);
        }
        return steparray;
    }

    //-------------------------------MealFacts--------------------------------------------
    public MealFacts getMealFacts() {return this.mealFacts;}

    public void setMealFacts(MealFacts mealfacts){
        this.mealFacts = mealfacts;
    }

    public void setMealFacts(JSONObject mealFactsRaw, android.content.res.Resources resources,String packagename){
        this.mealFacts = new MealFacts(mealFactsRaw,resources,packagename);
    }

    //-------------------------------------Nutrition---------------------------------------------
    public Nutrition getNutrition() {return this.nutrition;}

    public void setNutrition(JSONObject nutritionRaw){
        this.nutrition = new Nutrition(nutritionRaw);
    }


    //-------------------------------------Parcel--------------------------------------------------
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


