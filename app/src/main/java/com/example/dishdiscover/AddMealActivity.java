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
        if(recipe == null) {
            recipe = new Recipe();
        }

        if(recipe.getMealFacts().exists()) {
            EditText mealName = findViewById(R.id.MealName);
            mealName.setText(recipe.getMealFacts().getMealName());
            EditText mealDescription = findViewById(R.id.Description);
            mealDescription.setText(recipe.getMealFacts().getMealDescription());
            EditText mealUrl = findViewById(R.id.url);
            mealUrl.setText(recipe.getMealFacts().getWeburl());
            EditText mealComment = findViewById(R.id.Comment);
            mealComment.setText(recipe.getMealFacts().getComments());
            EditText mealPrep = findViewById(R.id.Prep);
            mealPrep.setText(String.valueOf(recipe.getMealFacts().getPrep()));
            EditText mealCooktime = findViewById(R.id.Cooktime);
            mealCooktime.setText(String.valueOf(recipe.getMealFacts().getCooktime()));
            //RadioGroup difficulty = findViewById(R.id.Difficutly);

            //EditText category = findViewById(R.id.Category);

            EditText serves = findViewById(R.id.Serves);
            serves.setText(String.valueOf(recipe.getMealFacts().getServes()));
            EditText rating = findViewById(R.id.Rating);
            rating.setText(String.valueOf(recipe.getMealFacts().getRating()));
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
        boolean valid = true;
        String er = "Field required";
        JSONObject mealfacts = new JSONObject();
        if(recipe == null) {
            recipe = new Recipe();
        }
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

        if(mealName.length() == 0) {
            mealName.setError(er);
            valid = false;
        } else {
            mealfacts.put("MealName", mealName.getText());
        }
        if(mealDescription.length() == 0) {
            mealDescription.setError(er);
            valid = false;
        } else {
            mealfacts.put("MealDescription", mealDescription.getText());
        }
        mealfacts.put("Difficulty",diff);
        if(mealPrep.length() == 0) {
            mealPrep.setError(er);
            valid = false;
        } else {
            mealfacts.put("Prep", Double.parseDouble(mealPrep.getText().toString()));
        }
        if(mealCooktime.length() == 0) {
            mealCooktime.setError(er);
            valid = false;
        } else {
            mealfacts.put("Cooktime", Double.parseDouble(mealCooktime.getText().toString()));
        }
        if(serves.length() == 0) {
            serves.setError(er);
            valid = false;
        } else {
            mealfacts.put("Serves", Double.parseDouble(serves.getText().toString()));
        }
        if(rating.length() == 0) {
            mealfacts.put("Rating", 1);
        } else {
            mealfacts.put("Rating", Double.parseDouble(rating.getText().toString()));
        }
        if(mealUrl.length() == 0) {
            mealfacts.put("Weburl","Add URL");
        } else {
            mealfacts.put("Weburl", mealUrl.getText());
        }
        if(mealComment.length() == 0) {
            mealfacts.put("Comments","Add Comments");
        } else {
            mealfacts.put("Comments", mealComment.getText());
        }
        mealfacts.put("MealImage","dosa.jpg");
        JSONArray cat = new JSONArray();
        cat.put(category.getText());
        mealfacts.put("Category",cat);


        if(valid) {
            newMeal = new MealFacts(mealfacts);
            recipe.setMealFacts(newMeal);

            Intent intent = new Intent(AddMealActivity.this, AddNutritionActivity.class);
            intent.putExtra("RECIPE", recipe);
            intent.putExtra("RECIPEBOOK", recipeBook);
            startActivity(intent);
        }
    }

    public void OnHomeClicked(){
        Intent intent = new Intent(AddMealActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
