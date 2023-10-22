package com.example.dishdiscover;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class MainModel extends RecyclerView.ViewHolder {

    ImageView recipeImage;
    TextView recipeName;
    public CardView cardView;


    public MainModel(@NonNull View itemView) {
        super(itemView);
        recipeImage = itemView.findViewById(R.id.recipe_picture);
        recipeName = itemView.findViewById(R.id.recipe_name);
        cardView = itemView.findViewById(R.id.recipe_view);
    }
}
