package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

public class Ingredient{
    String name;
    double amt;
    String unit;

    public String getAmount() {return amt + " " + unit;}

    public Ingredient(String name, double amt, String unit){
        super();
        this.name = name;
        this.amt = amt;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmt() {
        return amt;
    }

    public void setAmt(double amt) {
        this.amt = amt;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}