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
    int ingNum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addingredient);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");
        recipe = getIntent().getParcelableExtra("RECIPE");
        ingNum = getIntent().getIntExtra("INGNUM",1);
        Button next = findViewById(R.id.NextAddRecipe);
        Button nextIng = findViewById(R.id.nextIngredient);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                try {
                    done();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        });
        nextIng.setOnClickListener(new View.OnClickListener() {
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
        Button prevIng = findViewById(R.id.previousIngredient);
        //disable the previous step on the first step as there is no previous step from the first
        if (ingNum < 2) {
            prevIng.setEnabled(false);
        }
        prevIng.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                prevIng();
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
        if(valid) {
            newIngredient = new Ingredient(ing1.getText().toString(), Integer.parseInt(amt1.getText().toString()), unit1.getText().toString());
            recipe.addIngredient(newIngredient);
            Intent intent = new Intent(AddIngredientActivity.this, AddIngredientActivity.class);
            intent.putExtra("RECIPE", recipe);
            intent.putExtra("RECIPEBOOK", recipeBook);
            intent.putExtra("INGNUM", ingNum+1);
            startActivity(intent);
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
    }
    public void done() throws JSONException {
        Ingredient newIngredient;
        boolean valid = true;
        String er = "Field required";
        EditText ing1 = findViewById(R.id.Ingredient1);
        EditText amt1 = findViewById(R.id.amt1);
        EditText unit1 = findViewById(R.id.unit1);
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
        if(valid) {
            newIngredient = new Ingredient(ing1.getText().toString(), Integer.parseInt(amt1.getText().toString()), unit1.getText().toString());
            recipe.addIngredient(newIngredient);
            Intent intent = new Intent(AddIngredientActivity.this, AddStepActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
    }

    public void OnHomeClicked(){
        Intent intent = new Intent(AddIngredientActivity.this,AddStepActivity.class);
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
    public void prevIng(){
        Intent intent = new Intent(AddIngredientActivity.this, AddIngredientActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        intent.putExtra("INGNUM", ingNum - 1);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
