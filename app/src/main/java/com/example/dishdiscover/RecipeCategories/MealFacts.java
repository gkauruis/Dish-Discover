package com.example.dishdiscover.RecipeCategories;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MealFacts implements Parcelable{
    String mealName;
    String mealImage;
    ArrayList<String> category = new ArrayList<String>();
    String mealDescription;
    String difficulty;
    double prep;
    double cooktime;
    int serves;
    double rating;
    String weburl;
    String comments;

    public MealFacts(JSONObject mealFactsRaw) {

        try {
            this.mealName = mealFactsRaw.getString("MealName");
            this.mealImage = mealFactsRaw.getString("MealImage");
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

    public MealFacts() {
            this.mealName = "";
            this.mealImage = "";
            this.mealDescription = "";
            this.difficulty = "";
            this.prep = 0;
            this.cooktime = 0;
            this.serves = 0;
            this.rating = 0;
            this.weburl = "";
            this.comments = "";
    }

    public JSONObject getMealFactsJSON() throws JSONException {
        JSONObject mealfacts = new JSONObject();
        mealfacts.put("MealName",this.mealName);
        mealfacts.put("MealImage",this.mealImage);
        mealfacts.put("MealDescription",this.mealDescription);
        mealfacts.put("Difficulty",this.difficulty);
        mealfacts.put("Prep",this.prep);
        mealfacts.put("Cooktime",this.cooktime);
        mealfacts.put("Serves",this.serves);
        mealfacts.put("Rating",this.rating);
        mealfacts.put("Weburl",this.weburl);
        mealfacts.put("Comments",this.comments );
        JSONArray cat = new JSONArray();
        for (int i=0;i<this.category.size();i++){
            cat.put(this.category.get(i));
        }
        mealfacts.put("Category",cat);
        return mealfacts;
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
        this.mealImage = in.readString();
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
    public String getMealImage() {
        return mealImage;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String name){
        this.mealName = name;
    }
    public void setMealDescription(String desc){
        this.mealDescription = desc;
    }

    public String getMealDescription() { return this.mealDescription; }

    public void setDifficulty(String diff) { this.difficulty = diff; }

    public String getDifficulty() { return this.difficulty; }

    public void setPrep(double prep) { this.prep = prep; }

    public double getPrep() { return this.prep; }

    public void setCooktime(double time) { this.cooktime = time; }

    public double getCooktime() { return this.cooktime; }

    public void setServes(int serves) { this.serves = serves; }

    public int getServes() { return this.serves; }

    public void setRating(double rating) { this.rating = rating; }

    public double getRating() { return this.rating; }

    public void setWeburl(String url) { this.weburl = url; }

    public String getWeburl() { return this.weburl; }

    public void setComments(String comments) { this.comments = comments; }

    public String getComments() { return this.comments; }

    public void setCategory(JSONArray cat) {
        try {
            for (int i=0;i<cat.length();i++){
                this.category.add(cat.getString(i));
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean exists() {
        if(mealName != "") {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mealName);
        dest.writeString(this.mealImage);
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
