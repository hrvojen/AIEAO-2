package com.example.hrca.spinapplication_vol2.model;

/**
 * Created by hrca on 18.10.2016..
 */

public class Food {

    private String foodName;
    private Double calories;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private String brand;
    private String description;
    private Long ID;


    public Food() {

    }

    public Food(String foodName) {
        this.foodName = foodName;
    }

    public Food(String foodName, Long ID) {
        this.foodName = foodName;
        this.ID = ID;
    }

    public Food(String foodName, String description, String brand, Long ID) {
        this.foodName = foodName;
        this.brand=brand;
        this.ID = ID;
        this.description = description;
    }

    public Food(String foodName, Double calories, Double carbohydrate, Double protein, Double fat) {
        this.foodName = foodName;
        this.calories = calories;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
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

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getDescription().toString();
    }
}
