package com.example.dishdiscover;

public class Recipe {

    String recipeName;
    int recipeImage;

    public Recipe(String recipeName, int recipeImage) {
        this.recipeName = recipeName;
        this.recipeImage = recipeImage;
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
}
