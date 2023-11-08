package com.example.dishdiscover;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    List<Recipe> recipes = new ArrayList<Recipe>();
    String recipeJson;
    RecipeBook recipeBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // path to /data/data/yourapp/app_data/imageDir
        if (!load()) {
            save();
        }
        File directory = new File(getFilesDir(),"app_imageDir");
        // Create imageDir
        File mypath=new File(directory,"dosa.jpg");
        Bitmap meallogos;
        //create images in storage if not already exists, don't create it all the time to save boot time
        if (!mypath.exists()) {
            meallogos = BitmapFactory.decodeResource(getResources(), R.drawable.dosa);
            saveimage(meallogos, "dosa.jpg");
        }
        mypath=new File(directory,"crepes.jpg");
        if (!mypath.exists()) {
            meallogos = BitmapFactory.decodeResource(getResources(), R.drawable.crepes);
            saveimage(meallogos, "crepes.jpg");
        }
        setContentView(R.layout.activity_main);
        String jsonString;
        RecyclerView recyclerView = findViewById(R.id.recycler_view);


        //getResources().getIdentifier(mealImageResource, "drawable", getPackageName())
        recipeBook = new RecipeBook(this.recipeJson);
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
        requestPermissions(new String[]{android.Manifest.permission.CAMERA},200);

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

    public void saveimage(Bitmap bitmapImage,String imageName){
        File projDir = new File(getFilesDir(),"app_imageDir");
        Boolean done = projDir.mkdirs();
        File mypath=new File(projDir,imageName);
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
    private boolean loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            //ImageView img=(ImageView)findViewById(R.id.);
            //img.setImageBitmap(b);
        }
        catch (Exception e)
        {
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 200) {
            if (!Arrays.asList(grantResults).contains(PackageManager.PERMISSION_DENIED)) {
                Intent intent = new Intent(MainActivity.this, AddMealActivity.class);
                intent.putExtra("RECIPEBOOK", recipeBook);
                startActivity(intent);
                Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show();
            }
        }
    }
}