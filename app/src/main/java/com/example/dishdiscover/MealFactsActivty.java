package com.example.dishdiscover;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dishdiscover.RecipeCategories.Recipe;

public class MealFactsActivty extends AppCompatActivity {

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealfacts);

        recipe = getIntent().getParcelableExtra("RECIPE");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());

        TextView description = findViewById(R.id.descriptionTextView);
        description.setText(recipe.getMealFacts().getMealDescription());

        TextView difficulty = findViewById(R.id.difficultyTextView);
        difficulty.setText("Difficulty: " + recipe.getMealFacts().getDifficulty());

        TextView prep = findViewById(R.id.prepTextView);
        prep.setText("Prep Time: " + String.valueOf(recipe.getMealFacts().getPrep()) + " minutes");

        TextView cooktime = findViewById(R.id.cooktimeTextView);
        cooktime.setText("Cook Time: " + String.valueOf(recipe.getMealFacts().getCooktime()) + " minutes");

        TextView serves = findViewById(R.id.servesTextView);
        serves.setText("Serves: " + String.valueOf(recipe.getMealFacts().getServes()));

        TextView rating = findViewById(R.id.ratingTextView);
        rating.setText("Rating: " + String.valueOf(recipe.getMealFacts().getRating()));

        TextView url = findViewById(R.id.urlTextView);
        url.setText(recipe.getMealFacts().getWeburl());

        TextView comments = findViewById(R.id.commentsTextView);
        comments.setText(recipe.getMealFacts().getComments());

        TextView cal = findViewById(R.id.calTextView);
        cal.setText("Calories: " + String.valueOf(recipe.getNutrition().getCal()));

        TextView fat = findViewById(R.id.fatTextView);
        fat.setText("Fat: " + String.valueOf(recipe.getNutrition().getFat()) + " mg");

        TextView sugar = findViewById(R.id.sugarTextView);
        sugar.setText("Sugar: " + String.valueOf(recipe.getNutrition().getSugar()) + " mg");

        TextView salt = findViewById(R.id.saltTextView);
        salt.setText("Salt: " + String.valueOf(recipe.getNutrition().getSalt()) + " mg");

        Button ingred = findViewById(R.id.ingredientview);

        ingred.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnIngredClicked();
            }
        });
    }

    public void OnIngredClicked() {
        Intent intent = new Intent(MealFactsActivty.this, RecipeActivity.class);
        intent.putExtra("RECIPE", recipe);
        startActivity(intent);
        Toast.makeText(this, "Ingredients", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
        return true;
    }
}
