package com.example.dishdiscover.RecipeCategories;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class Nutrition implements Parcelable {
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

    public JSONObject getNutritionJSON() throws JSONException {
        JSONObject nutrition = new JSONObject();
        nutrition.put("Calories",this.cal);
        nutrition.put("Fat",this.fat);
        nutrition.put("Sugar",this.sugar);
        nutrition.put("Salt",this.salt);
        return nutrition;
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
