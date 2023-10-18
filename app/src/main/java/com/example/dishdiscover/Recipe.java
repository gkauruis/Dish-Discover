package com.example.dishdiscover;
import java.util.ArrayList;
import java.util.List;
public class Recipe {

    String recipeName;
    int recipeImage;
    List<Ingredient> ingredients= new ArrayList<Ingredient>();
    List<String> category = new ArrayList<String>();
    Nutrition nutrition = null;

    MealFacts facts = null;
    List<List<String>> steps = new ArrayList<List<String>>();
    public Recipe(String recipeName, int recipeImage) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void add_ingredient(String name,int amt,String unit)
    {
        Ingredient ing = new Ingredient(name,amt,unit);
        this.ingredients.add(ing);
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
}

class Ingredient {
    String name;
    double amt;
    String unit;

    public String getAmount() {return amt + " " + unit;}

    public Ingredient(String name, double amt, String unit){
        this.name = name;
        this.amt = amt;
        this.unit = unit;
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