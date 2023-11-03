package com.example.dishdiscover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dishdiscover.RecipeCategories.Step;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddStepActivity extends AppCompatActivity{

    RecipeBook recipeBook;
    Recipe recipe;
    JSONObject recipeJson;
    int stepnum = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstep);

        recipeBook = getIntent().getParcelableExtra("RECIPEBOOK");
        recipe = getIntent().getParcelableExtra("RECIPE");
        stepnum = getIntent().getIntExtra("STEPNUM",1);
        EditText steptextfield = findViewById(R.id.Stepnumber);
        steptextfield.setText(Integer.toString(stepnum));
        Button next = findViewById(R.id.nextStep);
        Button finish = findViewById(R.id.NextAddRecipe);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                addStep();
            }

        });
        finish.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                finish();
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

        Button prevstep = findViewById(R.id.previousStep);
        //disable the previous step on the first step as there is no previous step from the first
        if (stepnum < 2) {
            prevstep.setEnabled(false);
        }
        prevstep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                prevStep();
            }

        });
    }

    public void addxml(){
        try {
            this.recipeJson = recipeBook.getRecipeBookJSON();
            EditText step = findViewById(R.id.step);
            Recipe recipe = recipeBook.getBook().get(0);
            this.recipeJson = recipeBook.getRecipeBookJSON();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        save();
        Intent intent = new Intent(AddStepActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }

    public void  save()  // SAVE
    {
        File file= null;
        FileOutputStream fileOutputStream = null;
        try {
            recipeJson = recipeBook.getRecipeBookJSON();
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
    public void addStep(){

        EditText steptext = findViewById(R.id.step);
        Step step = new Step();
        step.setAction(steptext.getText().toString());
        step.setNumber(stepnum);
        step.setStepImage("placeholder");
        recipe.addStep(step);
        Intent intent = new Intent(AddStepActivity.this,AddStepActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        intent.putExtra("STEPNUM",stepnum+1);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void finish(){

        EditText steptext = findViewById(R.id.step);
        Step step = new Step();
        step.setAction(steptext.getText().toString());
        step.setNumber(stepnum);
        step.setStepImage("placeholder");
        recipe.addStep(step);
        recipeBook.addRecipe(recipe);
        save();
        Intent intent = new Intent(AddStepActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void OnHomeClicked(){
        Intent intent = new Intent(AddStepActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
    public void prev()  {

        Intent intent = new Intent(AddStepActivity.this, AddIngredientActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }

    public void prevStep(){
        Intent intent = new Intent(AddStepActivity.this, AddStepActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
        intent.putExtra("STEPNUM", stepnum-1);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }
}
