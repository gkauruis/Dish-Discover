package com.example.dishdiscover;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipe = getIntent().getParcelableExtra("RECIPE");
        stepNum = getIntent().getIntExtra("STEPNUM",0);
        stepAmt = recipe.getStepAmt();
        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());
        step = recipe.getStep(stepNum);
        TextView recipeStep = findViewById(R.id.RecipeInsruction);
        recipeStep.setText(step.getAction());
        ImageButton prev = findViewById(R.id.prev);
        ImageButton next = findViewById(R.id.next);

        if (stepNum == 0) {
            prev.setEnabled(false);
            prev.setAlpha(0.3f); // Reduce button opacity
            prev.setTooltipText(null);
        }
        if (stepNum >= stepAmt -1){
            next.setEnabled(false);
            next.setAlpha(0.3f); // Reduce button opacity
            next.setTooltipText(null);
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
    }
        public void OnStepsClicked(Recipe recipe,int step) {
            Intent intent = new Intent(StepActivity.this, StepActivity.class);
            intent.putExtra("RECIPE", recipe);
            intent.putExtra("STEPNUM",step);
            startActivity(intent);
            Toast.makeText(this, recipe.getRecipeName(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onSupportNavigateUp() {
            Intent intent = new Intent(StepActivity.this, RecipeActivity.class);
            intent.putExtra("RECIPE", recipe);
            startActivity(intent);
            finish(); // Close the current StepActivity
            return true;
        }

}


