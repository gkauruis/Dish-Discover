package com.example.dishdiscover;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.dishdiscover.RecipeCategories.MealFacts;
import com.example.dishdiscover.RecipeCategories.Recipe;
import com.example.dishdiscover.RecipeCategories.RecipeBook;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

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
        //Requirement Adding a recipe 2 and 3
        if(recipe.getMealFacts().exists()) {
            //preload recipe if exists
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
            //Requirment add a recipe 1
            try {
                ImageView mealImage = findViewById(R.id.mealImage);
                Bitmap map = loadImageFromStorage(mealName.getText().toString()+ ".jpg");
                mealImage.setImageBitmap(map);
            } catch (Exception e) {
                try {
                    Bitmap map = loadImageFromStorage("filenotfound.jpg");
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }


        Button next = findViewById(R.id.NextAddRecipe);
        //When button is clicked it will save meal and move on to Add Nutrients

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
        ActivityResultLauncher<Uri> getContent = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        System.out.println("result");
                        ImageView mealImage = findViewById(R.id.mealImage);
                        EditText mealName = findViewById(R.id.MealName);
                        try {
                            Bitmap map = loadImageFromStorage(mealName.getText().toString()+ ".jpg");
                            mealImage.setImageBitmap(map);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        Button home = findViewById(R.id.HomeAddRecipe);
        Button addImage = findViewById(R.id.addNewImage);
        //When clicked will take user home
        home.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                OnHomeClicked();
            }

        });
        addImage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("Button Clicked");
                EditText mealName = findViewById(R.id.MealName);
                takepicture(getContent, mealName.getText().toString() + ".jpg");
            }

        });


    }


    public void  save()  // SAVE
    {
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
        mealfacts.put("MealImage",mealName.getText() + ".jpg");

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

    public void saveimage(Bitmap bitmapImage,String imageName){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        //File file = getFilesDir();
        //file = file.getParentFile();
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,imageName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void takepicture(ActivityResultLauncher<Uri> getContent,String name){
        File filepath = new File(getFilesDir(),"app_imageDir");
        File file = new File(filepath,name);
        Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", file);
        getContent.launch(uri);

    }

    private Bitmap loadImageFromStorage(String name) throws FileNotFoundException {
        File path = new File(getFilesDir(),"app_imageDir");
        Bitmap b;
        File f=new File(path, name);
        b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }
}
