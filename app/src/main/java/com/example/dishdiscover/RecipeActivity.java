package com.example.dishdiscover;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiscover.RecipeCategories.Ingredient;
import com.example.dishdiscover.RecipeCategories.Recipe;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity{

    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = getIntent().getParcelableExtra("RECIPE");

        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());
//
        TextView ingredientsTitle = findViewById(R.id.ingredientsLabelTextView);
        ingredientsTitle.setText("Ingredients");
//
        ListView ingredientsListView = findViewById(R.id.ingredientsListView);
        ArrayList<String> ingredientsList = new ArrayList<>();
//
        for (Ingredient ingredient : recipe.getIngredients()){
            String ingredientsInfo = ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getName();
            ingredientsList.add(ingredientsInfo);
        }
//
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientsList);
//
        ingredientsListView.setAdapter(arrayAdapter);

        Button home = findViewById(R.id.homeview);
        Button buttonOne = findViewById(R.id.stepsview);

        buttonOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnStepsClicked(recipe);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnHomeClicked();
            }

        });

    }


    public void OnStepsClicked(Recipe recipe) {
        Intent intent = new Intent(RecipeActivity.this, StepActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("STEPNUM",0);
        startActivity(intent);
        Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
    }
    public void OnHomeClicked(){
        Intent intent = new Intent(RecipeActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void addnew(){
        save();
    }

    public void  save()  // SAVE
    {
        File file= null;
        String name = "test1";
        String password = "test2";

        FileOutputStream fileOutputStream = null;
        try {
            name = name + " ";
            file = getFilesDir();
            fileOutputStream = openFileOutput("Code.txt", Context.MODE_PRIVATE); //MODE PRIVATE
            fileOutputStream.write(name.getBytes());
            fileOutputStream.write(password.getBytes());
            Toast.makeText(this, "Saved \n" + "Path --" + file + "\tCode.txt", Toast.LENGTH_SHORT).show();
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

    public void  next( View view)   //NEXT
    {
        Toast.makeText(this,"NEXT", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this, AddRecipeActivity.class);
        startActivity(intent);

    }


}
