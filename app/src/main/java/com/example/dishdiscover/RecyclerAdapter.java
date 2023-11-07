package com.example.dishdiscover;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiscover.RecipeCategories.Recipe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<MainModel> {

    Context context;
    List<Recipe> recipe;
    private SelectListener listener;

    public RecyclerAdapter(Context context, List<Recipe> recipe, SelectListener listener) {
        this.context = context;
        this.recipe = recipe;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MainModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MainModel(LayoutInflater.from(context).inflate(R.layout.row_data_recepies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainModel holder, int position) {
        holder.recipeName.setText(recipe.get(position).getRecipeName());
        try {
            String path = holder.itemView.getContext().getFilesDir() + "/app_imageDir";
            holder.recipeImage.setImageBitmap(loadImageFromStorage(path,recipe.get(position).getRecipeImage()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //holder.recipeImage.setImageResource(Integer.getInteger(recipe.get(position).getRecipeImage()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    listener.OnRecipeClicked(recipe.get(clickedPosition));
                }
            }
        });
    }
    private Bitmap loadImageFromStorage(String path,String name) throws FileNotFoundException {
        Bitmap b;
        File f=new File(path, name);
        b = BitmapFactory.decodeStream(new FileInputStream(f));
        return b;
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }
}
