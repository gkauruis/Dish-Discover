package com.example.dishdiscover;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecepieModel> {

    Context context;
    List<Recipe> recipe;

    public RecyclerAdapter(Context context, List<Recipe> recipe) {
        this.context = context;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public RecepieModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecepieModel(LayoutInflater.from(context).inflate(R.layout.row_data_recepies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecepieModel holder, int position) {
        holder.recipeName.setText(recipe.get(position).getRecipeName());
        holder.recipeImage.setImageResource(recipe.get(position).getRecipeImage());
    }

    @Override
    public int getItemCount() {
        return recipe.size();
    }
}
