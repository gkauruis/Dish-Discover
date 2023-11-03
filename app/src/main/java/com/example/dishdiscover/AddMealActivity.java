package com.example.dishdiscover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdiscover.RecipeCategories.MealFacts;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddMealActivity extends AppCompatActivity{

    RecipeBook recipeBook;
    Recipe recipe = new Recipe();
    JSONObject recipeJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmeal);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");
        try {
            recipe = getIntent().getParcelableExtra("RECIPE");
        } catch (Exception e) {
            recipe = new Recipe();
        }

        Button next = findViewById(R.id.NextAddRecipe);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                try {
                    addMeal();
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
    public void addMeal() throws JSONException {
        MealFacts newMeal;
        recipe = new Recipe();
        JSONObject mealfacts = new JSONObject();
        EditText mealName = findViewById(R.id.MealName);
        EditText mealDescription = findViewById(R.id.Description);
        EditText mealUrl = findViewById(R.id.url);
        EditText mealComment = findViewById(R.id.Comment);
        EditText mealPrep = findViewById(R.id.Prep);
        EditText mealCooktime = findViewById(R.id.Cooktime);
        RadioGroup difficulty = findViewById(R.id.Difficutly);
        EditText category = findViewById(R.id.Category);
        EditText serves = findViewById(R.id.Serves);
        EditText rating = findViewById(R.id.Rating);
        String diff;
        if (difficulty.getCheckedRadioButtonId() ==  R.id.Hard) {
            diff = "Hard";
        } else if (difficulty.getCheckedRadioButtonId() ==  R.id.Medium) {
            diff = "Medium";
        } else {
            diff = "Easy";
        }

        mealfacts.put("MealName",mealName.getText());
        mealfacts.put("MealDescription",mealDescription.getText());
        mealfacts.put("Difficulty",diff);
        mealfacts.put("Prep",Integer.parseInt(mealPrep.getText().toString()));
        mealfacts.put("Cooktime",Integer.parseInt(mealCooktime.getText().toString()));
        mealfacts.put("Serves",Integer.parseInt(serves.getText().toString()));
        mealfacts.put("Rating",Integer.parseInt(rating.getText().toString()));
        mealfacts.put("Weburl",mealUrl.getText());
        mealfacts.put("Comments",mealComment.getText() );
        mealfacts.put("MealImage","dosa.jpg");
        JSONArray cat = new JSONArray();
        cat.put(category.getText());
        mealfacts.put("Category",cat);

        newMeal = new MealFacts(mealfacts);
        recipe.setMealFacts(newMeal);

        Intent intent = new Intent(AddMealActivity.this,AddNutritionActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        startActivity(intent);
    }

    public void OnHomeClicked(){
        Intent intent = new Intent(AddMealActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
