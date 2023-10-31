package com.example.dishdiscover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONArray;
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
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    List<Recipe> recipes = new ArrayList<Recipe>();
    String recipeJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String jsonString;
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        if (!load()) {
            save();
        }
        //getResources().getIdentifier(mealImageResource, "drawable", getPackageName())
        RecipeBook recipeBook = new RecipeBook(this.recipeJson,getResources(),getPackageName());
        recipes = recipeBook.getBook();

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), recipes, this));
        Button newrecipe = findViewById(R.id.newrecipe);
        newrecipe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnNewRecipeClicked(recipeBook);
            }
        });
    }

    @Override
    public void OnRecipeClicked(Recipe recipe) {
        Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
        intent.putExtra("RECIPE", recipe);
        startActivity(intent);
        Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
    }

    public void OnNewRecipeClicked(RecipeBook recipeBook) {
        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        intent.putExtra("RECIPEBOOK",recipeBook);
        startActivity(intent);
        Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
    }
    public boolean load()
    {
        try {
            FileInputStream fileInputStream =  openFileInput("recipe.json");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            while((read =fileInputStream.read())!= -1){
                buffer.append((char)read);
            }
            this.recipeJson = buffer.toString();
            Toast.makeText(this,"Loaded", Toast.LENGTH_SHORT).show();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void  save()  // SAVE
    {
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
        } catch(IOException e){
            throw new RuntimeException(e);
        }
        this.recipeJson = stringBuilder.toString();
        File file= null;
        FileOutputStream fileOutputStream = null;
        try {
            file = getFilesDir();
            fileOutputStream = openFileOutput("recipe.json", Context.MODE_PRIVATE); //MODE PRIVATE
            fileOutputStream.write(this.recipeJson.getBytes());
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