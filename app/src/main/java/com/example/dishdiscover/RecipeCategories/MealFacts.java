package com.example.dishdiscover.RecipeCategories;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

class MealFacts implements Parcelable{
    String mealName;
    int mealImage;
    ArrayList<String> category = new ArrayList<String>();
    String mealDescription;
    String difficulty;
    double prep;
    double cooktime;
    int serves;
    double rating;
    String weburl;
    String comments;

    public MealFacts(JSONObject mealFactsRaw, android.content.res.Resources resources,String packagename) {

        try {
            this.mealName = mealFactsRaw.getString("MealName");
            this.mealImage = resources.getIdentifier(mealFactsRaw.getString("MealImage"), "drawable",packagename);
            this.mealDescription = mealFactsRaw.getString("MealDescription");
            this.difficulty = mealFactsRaw.getString("Difficulty");
            this.prep = mealFactsRaw.getDouble("Prep");
            this.cooktime = mealFactsRaw.getDouble("Cooktime");
            this.serves = mealFactsRaw.getInt("Serves");
            this.rating = mealFactsRaw.getInt("Rating");
            this.weburl = mealFactsRaw.getString("Weburl");
            this.comments = mealFactsRaw.getString("Comments");
            setCategory(mealFactsRaw.getJSONArray("Category"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static final Creator<MealFacts> CREATOR = new Creator<MealFacts>() {
        @Override
        public MealFacts createFromParcel(Parcel in) {
            return new MealFacts(in);
        }

        @Override
        public MealFacts[] newArray(int i) {
            return new MealFacts[0];
        }

    };

    protected MealFacts(Parcel in) {
        this.mealName = in.readString();
        this.mealImage = in.readInt();
        this.mealDescription = in.readString();
        this.difficulty = in.readString();
        this.prep = in.readDouble();
        this.cooktime = in.readDouble();
        this.serves = in.readInt();
        this.rating = in.readDouble();
        this.weburl = in.readString();
        this.comments = in.readString();
        this.category = in.readArrayList(String.class.getClassLoader());
    }
    public int getMealImage() {
        return mealImage;
    }

    public String getMealName() {
        return mealName;
    }


    public void setCategory(JSONArray cat) {
        try {
            for (int i=0;i<cat.length();i++){
                this.category.add(cat.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mealName);
        dest.writeInt(this.mealImage);
        dest.writeString(this.mealDescription);
        dest.writeString(this.difficulty);
        dest.writeDouble(this.prep);
        dest.writeDouble(this.cooktime);
        dest.writeInt(this.serves);
        dest.writeDouble(this.rating);
        dest.writeString(this.weburl);
        dest.writeString(this.comments);
        dest.writeList(this.category);
    }
}
