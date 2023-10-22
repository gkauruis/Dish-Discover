package com.example.dishdiscover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dishdiscover.RecipeCategories.Recipe;

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
        holder.recipeImage.setImageResource(recipe.get(position).getRecipeImage());

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

    @Override
    public int getItemCount() {
        return recipe.size();
    }
}
