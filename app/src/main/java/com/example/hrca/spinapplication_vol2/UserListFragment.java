package com.example.hrca.spinapplication_vol2;

import android.content.Context;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.hrca.spinapplication_vol2.adapters.DataTransferInterface;
import com.example.hrca.spinapplication_vol2.adapters.SearchAdapter;
import com.example.hrca.spinapplication_vol2.adapters.Updateable;
import com.example.hrca.spinapplication_vol2.adapters.UserListAdapter;
import com.example.hrca.spinapplication_vol2.model.Food;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserListFragment extends Fragment implements DataTransferInterface {
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
    private ArrayList<Food> tempArrayList;
    private TextView dummyTextView;
    private OnFragmentInteractionListener mListener;
    OnHeadlineSelectedListener mCallback;


    @Override
    public void setValues(ArrayList<Food> arrayList) {
        foodList.addAll(arrayList);

        userListAdapter = new UserListAdapter(getActivity(), foodList);
        userListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("Foobar", " "+userListAdapter.getCount());
            }
        });
        mListView.setAdapter(userListAdapter);
        Log.d("TAG_USER_LIST", "config-inner");
        updateList();
    }

    @Override
    public ArrayList<Food> getValues() {
        return null;
    }


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

//            foodList= (ArrayList<Food>) getArguments().getSerializable(FOOD_KEY);



            if((ArrayList<Food>) getArguments().getSerializable(FOOD_KEY) != null) {
                foodList.addAll((ArrayList<Food>) getArguments().getSerializable(FOOD_KEY));
                System.out.println(foodList.size());
            }



            if(!(foodList.equals(null))) {
                userListAdapter = new UserListAdapter(getActivity(), (ArrayList<Food>) getArguments().getSerializable(FOOD_KEY));
                userListAdapter.getCount();
                Log.e("GET COUNT VALUE", ""+userListAdapter.getCount());
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //        if (!(foodList.isEmpty())) {
//            userListAdapter.swapItems(foodList);
//            Log.e("GET COUNT-On resume", ""+userListAdapter.getCount());
//        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_user_list, container, false);

        mListView=(ListView)view.findViewById(R.id.userFoodListView);


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

//        TextView mFoodName = (TextView) getView().findViewById(R.id.user_food_name);
//        TextView mBrand = (TextView) getView().findViewById(R.id.user_food_brand);
//        TextView mFoodDescription = (TextView) getView().findViewById(R.id.user_food_description);



    }


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



    public void listViewConfigurations(ArrayList<Food> tmpFoodList) {
        Log.d("TAG_USER_LIST", "config");

        userListAdapter = new UserListAdapter(getActivity(), tmpFoodList);
        userListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("Foobar", " "+userListAdapter.getCount());
            }
        });
        mListView.setAdapter(userListAdapter);
        Log.d("TAG_USER_LIST", "config-inner");


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

    private class AcyncClass extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            // Get a handler that can be used to post to the main thread
            Handler mainHandler = new Handler(getActivity().getMainLooper());

            Runnable myRunnable = new Runnable() {
                String tempStr=foodList.get(0).getFoodName().toString();

                @Override
                public void run() {
                    dummyTextView.setText(tempStr);

                } // This is your code
            };
            mainHandler.post(myRunnable);


            userListAdapter.notifyDataSetChanged();
            updateList();
            return "do in bckg executed";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String tempStr=foodList.get(0).getFoodName().toString();
            dummyTextView.setText(tempStr);

        }


    }

}
