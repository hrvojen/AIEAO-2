package com.example.hrca.spinapplication_vol2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hrca.spinapplication_vol2.adapters.SearchAdapter;
import com.example.hrca.spinapplication_vol2.adapters.UserListAdapter;
import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private UserListAdapter userListAdapter;
    public ListView mListView;
    private ArrayList<Food> foodList=new ArrayList<>();
    private static final String FOOD_KEY = "food_key";

    private OnFragmentInteractionListener mListener;
    OnHeadlineSelectedListener mCallback;


    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    public UserListFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment UserListFragment.
     */
    // TODO: Rename and change types and number of parameters
//    public static UserListFragment newInstance(String param1, String param2) {
//        UserListFragment fragment = new UserListFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    public static UserListFragment newInstance(ArrayList <Food> foodItems) {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        args.putSerializable(FOOD_KEY, foodItems);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
            foodList= (ArrayList<Food>) getArguments().getSerializable(FOOD_KEY);
            System.out.println(foodList.size());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        userListAdapter.notifyDataSetChanged();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user_list, container, false);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView=(ListView)getView().findViewById(R.id.userFoodListView);

        TextView mFoodName = (TextView) getView().findViewById(R.id.user_food_name);
        TextView mBrand = (TextView) getView().findViewById(R.id.user_food_brand);
        TextView mFoodDescription = (TextView) getView().findViewById(R.id.user_food_description);

        if(foodList.size()!=0) {
            listViewConfigurations();
            updateList();
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//            Log.d("TAG_USER_LIST", "onAttachPozvana");
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnHeadlineSelectedListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }



    private void listViewConfigurations() {
        Log.d("TAG_USER_LIST", "config");

        for (Food food:foodList
                ) {
            Log.d("Hol-up", " "+food.getDescription());
        }
        userListAdapter = new UserListAdapter(getActivity(), foodList);
        mListView.setAdapter(userListAdapter);
        userListAdapter.notifyDataSetChanged();
        Log.d("TAG_USER_LIST", "config-inner");


    }

    public void setFood(ArrayList<Food> foodList){
        this.foodList=foodList;

    }

    private void updateList() {
        if (userListAdapter.getCount() == 0) {
            Log.d("TAG_GONE", "Cant see");
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
            Log.d("TAG_SHOW", "Can see");

        }
    }

}
