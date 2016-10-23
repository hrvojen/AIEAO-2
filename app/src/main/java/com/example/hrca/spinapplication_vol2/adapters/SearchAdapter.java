package com.example.hrca.spinapplication_vol2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.hrca.spinapplication_vol2.R;
import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;

/**
 * Created by hrca on 13.10.2016..
 */

public class SearchAdapter extends ArrayAdapter<Food> {

    private final Context mContext;
    private final ArrayList<Food> mItem;

    public SearchAdapter(Context context, ArrayList<Food> itemsArrayList) {
        super(context, R.layout.search_row, itemsArrayList);
        this.mContext = context;
        this.mItem = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.search_row, parent, false);
        TextView mFoodName = (TextView) v.findViewById(R.id.food_name);
        TextView mBrand = (TextView) v.findViewById(R.id.food_brand);
        TextView mFoodDescription = (TextView) v.findViewById(R.id.food_description);
        mFoodName.setText(mItem.get(position).getFoodName());
        mFoodDescription.setText(mItem.get(position).getDescription());
        mBrand.setText(mItem.get(position).getBrand());
        return v;
    }

}