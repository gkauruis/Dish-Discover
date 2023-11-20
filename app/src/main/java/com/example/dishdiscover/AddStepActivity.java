package com.example.dishdiscover;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.dishdiscover.RecipeCategories.Step;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        Step step = recipe.getStep(stepnum );
        TextView steptextfield = findViewById(R.id.Stepnumber);
        steptextfield.setText(Integer.toString(stepnum));
        Button next = findViewById(R.id.nextStep);
        Button finish = findViewById(R.id.NextAddRecipe);
        ImageView newStepImage = findViewById(R.id.newStepImage);
        EditText action = findViewById(R.id.step);
        action.setText(step.getAction());
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

        ActivityResultLauncher<Uri> getContent = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        System.out.println("result");
                        ImageView newStepImage = findViewById(R.id.newStepImage);
                        try {
                            Bitmap map = loadImageFromStorage(recipe.getRecipeName()+"_"+stepnum + ".jpg");
                            newStepImage.setImageBitmap(map);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );

        Button addImage = findViewById(R.id.addNewStepImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                takepicture(getContent, recipe.getRecipeName()+"_"+stepnum + ".jpg");
            }

        });
    }
    public void takepicture(ActivityResultLauncher<Uri> getContent, String name){
        File filepath = new File(getFilesDir(),"app_imageDir");
        File fileimage = new File(filepath,"stepImages");
        fileimage.mkdirs();
        File file = new File(fileimage,name);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        getContent.launch(uri);

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
        step.setStepImage(recipe.getRecipeName()+"_"+stepnum + ".jpg");
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
        step.setStepImage(recipe.getRecipeName()+"_"+stepnum + ".jpg");
        recipe.addStep(step);
        recipeBook.addRecipe(recipe);
        save();
        Intent intent = new Intent(AddStepActivity.this,MainActivity.class);
        intent.putExtra("RECIPE", recipe);
        intent.putExtra("RECIPEBOOK", recipeBook);
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
        intent.putExtra("PASS", true);
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
    private Bitmap loadImageFromStorage(String name, int root) throws FileNotFoundException {
        File path = new File(getFilesDir(),"app_imageDir");
        path.mkdirs();
        Bitmap b;
        File f=new File(path, name);
        b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }

}
