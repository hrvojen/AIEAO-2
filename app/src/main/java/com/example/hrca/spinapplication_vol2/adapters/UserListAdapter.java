package com.example.hrca.spinapplication_vol2.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hrca.spinapplication_vol2.R;
import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;

/**
 * Created by hrca on 24.10.2016..
 */

public class UserListAdapter extends ArrayAdapter<Food> {

    private final Context mContext;
    private final ArrayList<Food> userFoodList;
    private UserListAdapter userListAdapter;

    public UserListAdapter(Context mContext, ArrayList<Food> userFoodList) {
        super(mContext, R.layout.user_list_row);
        this.mContext = mContext;
        this.userFoodList = userFoodList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.user_list_row, parent, false);


        TextView mFoodName = (TextView) v.findViewById(R.id.user_food_name);
        TextView mBrand = (TextView) v.findViewById(R.id.user_food_brand);
        TextView mFoodDescription = (TextView) v.findViewById(R.id.user_food_description);

        mFoodName.setText(userFoodList.get(position).getFoodName());
        mFoodDescription.setText(userFoodList.get(position).getDescription());
        mBrand.setText(userFoodList.get(position).getBrand());
        return v;


    }


    public void AddItem(Food food){
        if(null != food){
            userFoodList.add(food);
        }
    }

}
