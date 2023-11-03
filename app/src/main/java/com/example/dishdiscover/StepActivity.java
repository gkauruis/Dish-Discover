package com.example.dishdiscover;
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
import com.example.dishdiscover.RecipeCategories.Step;

import java.util.ArrayList;

public class StepActivity extends AppCompatActivity{

    Recipe recipe;
    int stepNum;
    Step step;
    int stepAmt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        recipe = getIntent().getParcelableExtra("RECIPE");
        stepNum = getIntent().getIntExtra("STEPNUM",0);
        stepAmt = recipe.getStepAmt();
        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());
        step = recipe.getStep(stepNum);
        TextView recipeStep = findViewById(R.id.RecipeInsruction);
        recipeStep.setText(step.getAction());
        Button prev = findViewById(R.id.prev);
        Button next = findViewById(R.id.Next);
        Button home = findViewById(R.id.Recipe);
        if (stepNum == 0) {
            prev.setEnabled(false);
        }
        if (stepNum >= stepAmt -1){
            next.setEnabled(false);
        }
        prev.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnStepsClicked(recipe,stepNum - 1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnStepsClicked(recipe,stepNum + 1);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                returnHome(recipe);
            }
        });
    }
        public void OnStepsClicked(Recipe recipe,int step) {
            Intent intent = new Intent(StepActivity.this, StepActivity.class);
            intent.putExtra("RECIPE", recipe);
            intent.putExtra("STEPNUM",step);
            startActivity(intent);
            Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        }
        public void returnHome(Recipe recipe) {
            Intent intent = new Intent(StepActivity.this, RecipeActivity.class);
            intent.putExtra("RECIPE", recipe);
            startActivity(intent);
            Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        }
    }


