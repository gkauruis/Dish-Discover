package com.example.dishdiscover;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecepieModel extends RecyclerView.ViewHolder {

    ImageView recipeImage;
    TextView recipeName;

    public RecepieModel(@NonNull View itemView) {
        super(itemView);
        recipeImage = itemView.findViewById(R.id.recipe_picture);
        recipeName = itemView.findViewById(R.id.recipe_name);
    }
}
