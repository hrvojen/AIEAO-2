package com.example.hrca.spinapplication_vol2.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrca.spinapplication_vol2.FoodActivity;
import com.example.hrca.spinapplication_vol2.R;
import com.example.hrca.spinapplication_vol2.UserListFragment;
import com.example.hrca.spinapplication_vol2.model.Food;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

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
                Food food=new Food(mItem.get(position2).getFoodName(),mItem.get(position2).getDescription(),mItem.get(position2).getBrand()," ");
                myFoodList.add(food);
                Log.d("String_tag", food.toString());
                if (mContext instanceof FoodActivity){
                    ((FoodActivity) mContext).setValues(myFoodList);
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

//    private View.OnClickListener myButtonClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            View parentRow = (View) v.getParent();
//            ListView listView = (ListView) parentRow.getParent();
//            final int position = listView.getPositionForView(parentRow);
//            Object o=listView.getItemAtPosition(position);
//            String s=(String)o;
//
//            Toast toast=Toast.makeText(getContext(), s, Toast.LENGTH_SHORT);
//        }
//    };


}