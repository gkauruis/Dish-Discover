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

    public Step(String number) {
        this.number = number;
        this.action = null;
        this.stepImage = null;
    }

    public Step(String number, String action) {
        this.number = number;
        this.action = action;
        this.stepImage = null;
    }

    public Step(String number, String action, String stepIamge) {
        this.number = number;
        this.action = action;
        this.stepImage = stepIamge;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getStepImage() {
        return stepImage;
    }

    public void setStepImage(String stepImage) {
        this.stepImage = stepImage;
    }
}
