package com.example.hrca.spinapplication_vol2.interfaces;

import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;

/**
 * Created by hrca on 29.10.2016..
 */

public interface DataTransferInterface {

    public ArrayList<Food> arrayList=new ArrayList<>();

    public void setValues(ArrayList<Food> arrayList);

    public ArrayList<Food> getValues();

    public void addFoodToArrayList(Food food);
}
