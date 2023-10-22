package com.example.dishdiscover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toast;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    List<Recipe> recipes = new ArrayList<Recipe>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jsonString;
        JSONObject reciperaw;
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        StringBuilder stringBuilder;
        try {
            AssetManager assetFiles = getAssets();
            String[] files = assetFiles.list("");
            InputStream inputStream = getAssets().open("recipes.json");

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        jsonString = stringBuilder.toString();
        try {
            reciperaw = new JSONObject(jsonString).getJSONObject("Recipe");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        try {
            String mealName = reciperaw.getJSONObject("Mealfacts").getString("MealName");
            String mealImageResource = reciperaw.getJSONObject("Mealfacts").getString("MealImage");
            List<Ingredient> ingredientList = new ArrayList<>();
            JSONArray ingredientsArray = reciperaw.getJSONArray("Ingredients");

            for(int i=0; i<ingredientsArray.length(); i++){
                String ingredientName = ingredientsArray.getJSONObject(i).getString("Ingredient");
                double ingredientAmount = ingredientsArray.getJSONObject(i).getDouble("Amount");
                String ingredientUnit = ingredientsArray.getJSONObject(i).getString("Unit");

                Ingredient ingredient = new Ingredient(ingredientName, ingredientAmount, ingredientUnit);
                ingredientList.add(ingredient);
            }
            Recipe recipe = new Recipe(mealName, getResources().getIdentifier(mealImageResource, "drawable", getPackageName()));
            recipes.add(recipe);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
//        recipes.add(new Recipe("Dosa", R.drawable.dosa));

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), recipes, this));

    }

    @Override
    public void OnRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra("RECIPE", recipe);
        startActivity(intent);
        Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
    }
}