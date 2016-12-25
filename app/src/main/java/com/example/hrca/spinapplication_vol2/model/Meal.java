package com.example.hrca.spinapplication_vol2.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hrca on 02.12.2016..
 */

public class Meal implements Serializable{

    @SerializedName("mealName")
    String name;

    @SerializedName("id")
    String id;

    String typeOfMeal;

    @SerializedName("dateOfMeal")
    String date;

    ArrayList<Food> arrayListOfIngridients;

    Bitmap mealImage;

    String decodedBitmap;
    private Double calories;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private String username;
    private String email;
    private String uniqueId;

    public Meal() {
    }

    public Meal(String name, String id, String typeOfMeal, String date, ArrayList<Food> arrayListOfIngridients, Bitmap mealImage, String decodedBitmap) {
        this.name = name;
        this.id = id;
        this.typeOfMeal = typeOfMeal;
        this.date = date;
        this.arrayListOfIngridients = arrayListOfIngridients;
        this.mealImage=mealImage;
        this.decodedBitmap=decodedBitmap;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public String getDecodedBitmap() {
        return decodedBitmap;
    }

    public void setDecodedBitmap(String decodedBitmap) {
        this.decodedBitmap = decodedBitmap;
    }

    public Bitmap getMealImage() {
        return mealImage;
    }

    public void setMealImage(Bitmap mealImage) {
        this.mealImage = mealImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeOfMeal() {
        return typeOfMeal;
    }

    public void setTypeOfMeal(String typeOfMeal) {
        this.typeOfMeal = typeOfMeal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Food> getArrayListOfIngridients() {
        return arrayListOfIngridients;
    }

    public void setArrayListOfIngridients(ArrayList<Food> arrayListOfIngridients) {
        this.arrayListOfIngridients = arrayListOfIngridients;
    }
}
