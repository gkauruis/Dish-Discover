package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Ingredient implements Parcelable {
    String name;
    double amt;
    String unit;

    protected Ingredient(Parcel in) {
        name = in.readString();
        amt = in.readDouble();
        unit = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public String getAmount() {return amt + " " + unit;}

    public Ingredient(JSONObject ingredient){
        try {
            this.name = ingredient.getString("Ingredient");
            this.amt = ingredient.getDouble("Amount");
            this.unit = ingredient.getString("Unit");
    } catch (JSONException e) {
        throw new RuntimeException(e);
    }

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


    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(amt);
        dest.writeString(unit);
    }
}