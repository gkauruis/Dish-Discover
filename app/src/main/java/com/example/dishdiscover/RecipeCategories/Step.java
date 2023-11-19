package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

public class Step implements Parcelable {
    int number;
    String action;
    String stepImage;

    public Step() {
        this.number = -1;
        this.action = "";
        this.stepImage = "";
    }

    public Step(int number) {
        this.number = number;
        this.action = null;
        this.stepImage = null;
    }

    public Step(int number, String action) {
        this.number = number;
        this.action = action;
        this.stepImage = null;
    }

    public Step(int number, String action, String stepIamge) {
        this.number = number;
        this.action = action;
        this.stepImage = stepIamge;
    }

    protected Step(Parcel in) {
        number = in.readInt();
        action = in.readString();
        stepImage = in.readString();
    }

    public static final Creator<Step> CREATOR = new Creator<Step>() {
        @Override
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        @Override
        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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


    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(number);
        dest.writeString(action);
        dest.writeString(stepImage);
    }

}
