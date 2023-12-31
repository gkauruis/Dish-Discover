package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Nutrition implements Parcelable {
    double cal;
    double fat;
    double sugar;
    double salt;

    public Nutrition(JSONObject nutritionRaw) {
        try {
            this.cal = nutritionRaw.getDouble("Calories");
            this.fat = nutritionRaw.getDouble("Fat");
            this.sugar = nutritionRaw.getDouble("Sugar");
            this.salt = nutritionRaw.getDouble("Salt");
        } catch (JSONException e) {
        throw new RuntimeException(e);
    }
    }
    public Nutrition(double cal, double fat, double sugar, double salt){
        this.cal = cal;
        this.fat = fat;
        this.sugar = sugar;
        this.salt = salt;
    }
    public Nutrition() {
            this.cal = 0;
            this.fat = 0;
            this.sugar = 0;
            this.salt = 0;
    }
    public JSONObject getNutritionJSON() throws JSONException {
        JSONObject nutrition = new JSONObject();
        nutrition.put("Calories",this.cal);
        nutrition.put("Fat",this.fat);
        nutrition.put("Sugar",this.sugar);
        nutrition.put("Salt",this.salt);
        return nutrition;
    }

    public void setCal(double cal) { this.cal = cal; }

    public double getCal() { return this.cal; }

    public void setFat(double fat) { this.fat = fat; }

    public double getFat() { return this.fat; }

    public void setSugar(double sugar) { this.sugar = sugar; }

    public double getSugar() { return this.sugar; }

    public void setSalt(double salt) { this.salt = salt; }

    public double getSalt() { return this.salt; }

    public boolean exists() {
        if(cal == 0) {
            return false;
        } else {
            return true;
        }
    }
    protected Nutrition(Parcel in) {
        cal = in.readDouble();
        fat = in.readDouble();
        sugar = in.readDouble();
        salt = in.readDouble();
    }

    public static final Creator<Nutrition> CREATOR = new Creator<Nutrition>() {
        @Override
        public Nutrition createFromParcel(Parcel in) {
            return new Nutrition(in);
        }

        @Override
        public Nutrition[] newArray(int size) {
            return new Nutrition[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.cal);
        dest.writeDouble(this.fat);
        dest.writeDouble(this.sugar);
        dest.writeDouble(this.salt);
    }
}
