// Generated by view binder compiler. Do not edit!
package com.example.dishdiscover.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.dishdiscover.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRecipeBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView ingredientsLabelTextView;

  @NonNull
  public final ListView ingredientsListView;

  @NonNull
  public final TextView recipeNameTextView;

  @NonNull
  public final Toolbar toolbar;

  private ActivityRecipeBinding(@NonNull LinearLayout rootView,
      @NonNull TextView ingredientsLabelTextView, @NonNull ListView ingredientsListView,
      @NonNull TextView recipeNameTextView, @NonNull Toolbar toolbar) {
    this.rootView = rootView;
    this.ingredientsLabelTextView = ingredientsLabelTextView;
    this.ingredientsListView = ingredientsListView;
    this.recipeNameTextView = recipeNameTextView;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRecipeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRecipeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_recipe, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRecipeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ingredientsLabelTextView;
      TextView ingredientsLabelTextView = ViewBindings.findChildViewById(rootView, id);
      if (ingredientsLabelTextView == null) {
        break missingId;
      }

      id = R.id.ingredientsListView;
      ListView ingredientsListView = ViewBindings.findChildViewById(rootView, id);
      if (ingredientsListView == null) {
        break missingId;
      }

      id = R.id.recipeNameTextView;
      TextView recipeNameTextView = ViewBindings.findChildViewById(rootView, id);
      if (recipeNameTextView == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityRecipeBinding((LinearLayout) rootView, ingredientsLabelTextView,
          ingredientsListView, recipeNameTextView, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}