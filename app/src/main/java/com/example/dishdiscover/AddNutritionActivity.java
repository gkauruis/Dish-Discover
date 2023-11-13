package com.example.dishdiscover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.MealFacts;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;
import com.example.dishdiscover.RecipeCategories.Nutrition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddNutritionActivity extends AppCompatActivity{

    RecipeBook recipeBook;
    Recipe recipe;
    JSONObject recipeJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnutrition);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");
        recipe = getIntent().getParcelableExtra("RECIPE");

        Button next = findViewById(R.id.NextAddRecipe);

        /*if(recipe.getNutrition().exists()) {
            EditText cal = findViewById(R.id.Calories);
            cal.setText(String.valueOf(recipe.getNutrition().getCal()));
            EditText sugar = findViewById(R.id.Sugar);
            sugar.setText(String.valueOf(recipe.getNutrition().getSugar()));
            EditText salt = findViewById(R.id.Salt);
            salt.setText(String.valueOf(recipe.getNutrition().getSalt()));
            EditText fat = findViewById(R.id.Fat);
            fat.setText(String.valueOf(recipe.getNutrition().getFat()));
        }*/
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
            MealFacts mealfacts = recipe.getMealFacts();
            mealfacts.setMealName(name.getText().toString());
            mealfacts.setMealDescription(desc.getText().toString());
            recipe.setMealFacts(mealfacts);
            this.recipeJson = recipeBook.getRecipeBookJSON();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        save();
        Intent intent = new Intent(AddNutritionActivity.this,MainActivity.class);
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
        Nutrition newNutrition;
        EditText cal = findViewById(R.id.Calories);
        EditText sugar = findViewById(R.id.Sugar);
        EditText salt = findViewById(R.id.Salt);
        EditText fat = findViewById(R.id.Fat);
        if(cal.length() == 0) {
            cal.setText("0");
        }
        if(sugar.length() == 0) {
            sugar.setText("0");
        }
        if(salt.length() == 0) {
            salt.setText("0");
        }
        if(fat.length() == 0) {
            fat.setText("0");
        }
        newNutrition = new Nutrition(Integer.parseInt(cal.getText().toString()),
                Integer.parseInt(fat.getText().toString()),
                Integer.parseInt(sugar.getText().toString()),
                Integer.parseInt(salt.getText().toString()));
        recipe.setNutrition(newNutrition);
        Intent intent = new Intent(AddNutritionActivity.this, AddIngredientActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void OnHomeClicked(){
        Intent intent = new Intent(AddNutritionActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void prev(){

        Intent intent = new Intent(AddNutritionActivity.this, AddMealActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
