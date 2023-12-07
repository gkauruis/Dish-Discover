package com.example.dishdiscover;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.Step;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

        TextView ingredientsTitle = findViewById(R.id.stepsLabelTextView);
        ingredientsTitle.setText("Steps");

        recipe = getIntent().getParcelableExtra("RECIPE");
        stepNum = getIntent().getIntExtra("STEPNUM",1);
        stepAmt = recipe.getStepAmt();
        TextView recipeName = findViewById(R.id.recipeNameTextView);
        recipeName.setText(recipe.getRecipeName());
        step = recipe.getStep(stepNum);
        TextView recipeStep = findViewById(R.id.RecipeInsruction);
        recipeStep.setText(step.getAction());
        ImageButton prev = findViewById(R.id.prev);
        ImageButton next = findViewById(R.id.next);

        if (stepNum == 1) {
            prev.setEnabled(false);
            prev.setAlpha(0.3f); // Reduce button opacity
            prev.setTooltipText(null);
        }
        if (stepNum >= stepAmt){
            next.setEnabled(false);
            next.setAlpha(0.3f); // Reduce button opacity
            next.setTooltipText(null);
        }

        ImageView newStepImage = findViewById(R.id.StepImage);
        try {
            Bitmap map = loadImageFromStorage(step.getStepImage());
            newStepImage.setImageBitmap(map);
        } catch (FileNotFoundException e) {
            try {
                newStepImage.setImageBitmap(loadImageFromStorage("filenotfound.jpg",1));
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
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
        Button home = findViewById(R.id.homeview);
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnHomeClicked();
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

    public void OnHomeClicked(){
        Intent intent = new Intent(StepActivity.this,MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
    }

        @Override
        public boolean onSupportNavigateUp() {
            Intent intent = new Intent(StepActivity.this, RecipeActivity.class);
            intent.putExtra("RECIPE", recipe);
            startActivity(intent);
            finish(); // Close the current StepActivity
            return true;
        }

    private Bitmap loadImageFromStorage(String name) throws FileNotFoundException {
        File rootpath = new File(getFilesDir(),"app_imageDir");
        File path = new File(rootpath,"stepImages");
        path.mkdirs();
        Bitmap b;
        File f=new File(path, name);
        b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }
    private Bitmap loadImageFromStorage(String name, int root) throws FileNotFoundException {
        File path = new File(getFilesDir(),"app_imageDir");
        path.mkdirs();
        Bitmap b;
        File f=new File(path, name);
        b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }

}


