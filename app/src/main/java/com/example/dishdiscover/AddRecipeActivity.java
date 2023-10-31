package com.example.dishdiscover;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.RecipeBook;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.MealFacts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity{

    RecipeBook recipeBook;
    JSONObject recipeJson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addrecipe);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");


        Button home = findViewById(R.id.addxml);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                addxml();
            }

        });
    }

    public void addxml(){
        try {
            this.recipeJson = recipeBook.getRecipeBookJSON();
            EditText name = findViewById(R.id.MealName);
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
        Intent intent = new Intent(AddRecipeActivity.this,MainActivity.class);
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

}
