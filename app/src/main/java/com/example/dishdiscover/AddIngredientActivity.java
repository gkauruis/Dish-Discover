package com.example.dishdiscover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddIngredientActivity extends AppCompatActivity{

    RecipeBook recipeBook;
    Recipe recipe;
    JSONObject recipeJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingredient);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");
        recipe = getIntent().getParcelableExtra("RECIPE");

        Button next = findViewById(R.id.NextAddRecipe);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                try {
                    addNutrition();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        });
        Button home = findViewById(R.id.HomeAddRecipe);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnHomeClicked();
            }

        });
        Button prev = findViewById(R.id.PreviousAddRecipe);
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                prev();
            }

        });
    }

    public void addxml(){
        try {
            this.recipeJson = recipeBook.getRecipeBookJSON();
            EditText name = findViewById(R.id.Ingredient1);
            EditText desc = findViewById(R.id.MealDescription);
            Recipe recipe = recipeBook.getBook().get(0);
            this.recipeJson = recipeBook.getRecipeBookJSON();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        save();
        Intent intent = new Intent(AddIngredientActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }

    public void  save()  // SAVE
    {;
        File file= null;
        FileOutputStream fileOutputStream = null;
        try {
            file = getFilesDir();
            fileOutputStream = openFileOutput("recipe.json", Context.MODE_PRIVATE); //MODE PRIVATE
            fileOutputStream.write(this.recipeJson.toString().getBytes());
            Toast.makeText(this, "Saved \n" + "Path --" + file + "\trecipe.json", Toast.LENGTH_SHORT).show();
            return;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void addNutrition() throws JSONException {
        Ingredient newIngredient;
        boolean valid = true;
        String er = "Field required";
        EditText ing1 = findViewById(R.id.Ingredient1);
        EditText amt1 = findViewById(R.id.amt1);
        EditText unit1 = findViewById(R.id.unit1);
        EditText ing2 = findViewById(R.id.Ingredient2);
        EditText amt2 = findViewById(R.id.amt2);
        EditText unit2 = findViewById(R.id.unit2);
        EditText ing3 = findViewById(R.id.Ingredient3);
        EditText amt3 = findViewById(R.id.amt3);
        EditText unit3 = findViewById(R.id.unit3);
        EditText ing4 = findViewById(R.id.Ingredient4);
        EditText amt4 = findViewById(R.id.amt4);
        EditText unit4 = findViewById(R.id.unit4);
        if(ing1.length() == 0) {
            ing1.setError(er);
            valid = false;
        }
        if(amt1.length() == 0) {
            amt1.setError(er);
            valid = false;
        }
        if(unit1.length() == 0) {
            unit1.setError(er);
            valid = false;
        }
        if(ing2.length() == 0) {
            ing2.setError(er);
            valid = false;
        }
        if(amt2.length() == 0) {
            amt2.setError(er);
            valid = false;
        }
        if(unit2.length() == 0) {
            unit2.setError(er);
            valid = false;
        }
        if(valid) {
            newIngredient = new Ingredient(ing1.getText().toString(), Integer.parseInt(amt1.getText().toString()), unit1.getText().toString());
            recipe.addIngredient(newIngredient);
            newIngredient = new Ingredient(ing2.getText().toString(), Integer.parseInt(amt2.getText().toString()), unit2.getText().toString());
            recipe.addIngredient(newIngredient);
            Intent intent = new Intent(AddIngredientActivity.this, AddStepActivity.class);
            intent.putExtra("RECIPE", recipe);
            intent.putExtra("RECIPEBOOK", recipeBook);
            intent.putExtra("STEPNUM", 1);
            startActivity(intent);
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
    }
    public void OnHomeClicked(){
        Intent intent = new Intent(AddIngredientActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void prev() {

        Intent intent = new Intent(AddIngredientActivity.this, AddNutritionActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
