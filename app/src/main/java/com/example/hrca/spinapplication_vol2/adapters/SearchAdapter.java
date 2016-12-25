package com.example.hrca.spinapplication_vol2.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hrca.spinapplication_vol2.FoodActivity;
import com.example.hrca.spinapplication_vol2.R;
import com.example.hrca.spinapplication_vol2.interfaces.DataTransferInterface;
import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;

/**
 * Created by hrca on 13.10.2016..
 */


public class SearchAdapter extends ArrayAdapter<Food> {
    DataTransferInterface dtInterface;
    public static ArrayList<Food> myFoodList=new ArrayList<>();
    private final Context mContext;
    private final ArrayList<Food> mItem;
    private UserListAdapter userListAdapter;

    public SearchAdapter(Context context, ArrayList<Food> itemsArrayList, DataTransferInterface dtInterface) {
        super(context, R.layout.search_row, itemsArrayList);
        this.mContext = context;
        this.dtInterface = dtInterface;
        this.mItem = itemsArrayList;
    }




    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.search_row, parent, false);
        TextView mFoodName = (TextView) v.findViewById(R.id.food_name);
        TextView mBrand = (TextView) v.findViewById(R.id.food_brand);
        TextView mFoodDescription = (TextView) v.findViewById(R.id.food_description);
        ImageButton addToMyListButton=(ImageButton)v.findViewById(R.id.buttonAddToList);
//        addToMyListButton.setTag(position);
        final int position2=position;

        addToMyListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG_BUTTON", mItem.get(position2).getFoodName());
//                Food food=new Food(mItem.get(position2).getFoodName(),mItem.get(position2).getDescription(),mItem.get(position2).getBrand()," ");
                Food food=new Food(mItem.get(position2).getFoodName(),mItem.get(position2).getDescription(),mItem.get(position2).getBrand(),mItem.get(position).getID());

                food.setBrand(mItem.get(position2).getBrand());
                food.setCalories(mItem.get(position2).getCalories());
                food.setProtein(mItem.get(position2).getProtein());
                food.setFat(mItem.get(position2).getFat());
                food.setCarbohydrate(mItem.get(position2).getCarbohydrate());


                myFoodList.add(food);
                Log.d("String_tag", food.toString());
                if (mContext instanceof FoodActivity){


//                    ((FoodActivity) mContext).setValues(myFoodList);


                   ((FoodActivity) mContext).addFoodToArrayList(food);
                  //((FoodActivity) mContext).onFragmentInteraction(UserListFragment.class);
                    ((FoodActivity) mContext).onArticleSelected(position);



                }

            }
        });
        mFoodName.setText(mItem.get(position).getFoodName());
        mFoodDescription.setText(mItem.get(position).getDescription());
        mBrand.setText(mItem.get(position).getBrand());
        return v;
    }




}