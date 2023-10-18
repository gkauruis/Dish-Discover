package com.example.dishdiscover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.widget.Toolbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
            recipes.add(new Recipe(reciperaw.getJSONObject("Mealfacts").getString("MealName"), R.drawable.crepes));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        recipes.add(new Recipe("Dosa", R.drawable.dosa));

        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(new RecyclerAdapter(getApplicationContext(), recipes));

    }

}