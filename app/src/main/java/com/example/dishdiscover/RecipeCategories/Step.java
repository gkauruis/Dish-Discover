package com.example.dishdiscover.RecipeCategories;
import android.os.Parcel;
import android.os.Parcelable;

public class Step {
    String number;
    String action;
    String stepImage;

    public Step() {
        this.number = null;
        this.action = null;
        this.stepImage = null;
    }

    public String getNumber() {
        return number;
    }

    public String getAction() {
        return action;
    }

    public String getStepImage() {
        return stepImage;
    }
}
