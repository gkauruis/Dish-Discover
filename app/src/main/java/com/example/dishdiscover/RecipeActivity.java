package com.example.dishdiscover;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.Recipe;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = getIntent().getParcelableExtra("RECIPE");

        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());
//
//        TextView ingredientsTitle = findViewById(R.id.ingredientsLabelTextView);
//        ingredientsTitle.setText("Ingredients");
//
//        ListView ingredientsListView = findViewById(R.id.ingredientsListView);
//        ArrayList<String> ingredientsList = new ArrayList<>();
//
//        for (Ingredient ingredient : recipe.getIngredients()){
//            String ingredientsInfo = ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getName();
//            ingredientsList.add(ingredientsInfo);
//        }
//
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientsList);
//
//        ingredientsListView.setAdapter(arrayAdapter);
    }
}