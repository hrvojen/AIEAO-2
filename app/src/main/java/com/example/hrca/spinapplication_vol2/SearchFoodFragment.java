package com.example.hrca.spinapplication_vol2;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrca.spinapplication_vol2.adapters.DataTransferInterface;
import com.example.hrca.spinapplication_vol2.adapters.SearchAdapter;
import com.example.hrca.spinapplication_vol2.fatsecretimplementation.FatSecretGet;
import com.example.hrca.spinapplication_vol2.fatsecretimplementation.FatSecretSearch;
import com.example.hrca.spinapplication_vol2.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFoodFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFoodFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFoodFragment extends Fragment{

    static boolean IS_SEARCH_VISIBLE; // retain list
    static boolean SEARCH_RETAIN; // retain search toolbar
    private static String STRING_FOOD_SEARCH; // retain editText
    private static int CURRENT_PAGE = 0; // Was used to control page items with CURRENT_PAGE++

    private EditText mSearch;
    private ListView mListView;
    private ArrayList<Food> foodList;
    private ProgressBar mProgressMore, mProgressSearch;
    private FatSecretSearch mFatSecretSearch;
    private FatSecretGet mFatSecretGet;
    private SearchAdapter mSearchAdapter;
    private ImageButton addToListButton;
    DataTransferInterface tdi;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    public SearchFoodFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFoodFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFoodFragment newInstance(String param1, String param2) {
        SearchFoodFragment fragment = new SearchFoodFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_search_food, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView=(ListView) getView().findViewById(R.id.listViewSearchFragment);

        mSearch=(EditText) getView().findViewById(R.id.editTextSearchFragment);
        mProgressMore=(ProgressBar)getView().findViewById(R.id.progressBarSearch);
        mProgressSearch=(ProgressBar)getView().findViewById(R.id.progressBarSearch2);
        mProgressMore.setVisibility(View.INVISIBLE);
        mProgressSearch.setVisibility(View.INVISIBLE);
        addToListButton=(ImageButton) getView().findViewById(R.id.buttonAddToList);

        mFatSecretSearch = new FatSecretSearch(); // method.search
        mFatSecretGet = new FatSecretGet(); // method.get


        searchImplementation();
        getImplementation();


        listViewConfigurations();
        updateList();




    }




    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
        foodList = new ArrayList<>();
        mSearchAdapter = new SearchAdapter(getActivity(), foodList, tdi);
        mListView.setAdapter(mSearchAdapter);
    }

    private void searchImplementation() {
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == 6 || actionId == 5) {
                    foodList.clear();
                    //EditorInfo.IME_ACTION_SEARCH
                    InputMethodManager inputManager = (InputMethodManager) getActivity().getBaseContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    searchFood(mSearch.getText().toString(), CURRENT_PAGE);
                    mSearch.clearFocus();
                    return true;
                }
                return false;
            }
        });
    }


    private void getImplementation() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < foodList.size()) {
                    getFood(Long.valueOf(foodList.get(position - 1).getID()));
                } else {
                    if (foodList.size() == 20)
                        searchFood(mSearch.getText().toString(), 1);
                    else if (foodList.size() == 40)
                        searchFood(mSearch.getText().toString(), 2);
                    else if (foodList.size() == 60)
                        searchFood(mSearch.getText().toString(), 3);
                    else if (foodList.size() == 80)
                        searchFood(mSearch.getText().toString(), 4);
                    else if (foodList.size() == 100)
                        searchFood(mSearch.getText().toString(), 5);
                    else if (foodList.size() == 120)
                        searchFood(mSearch.getText().toString(), 6);
                    else if (foodList.size() == 140)
                        searchFood(mSearch.getText().toString(), 7);
                    else if (foodList.size() == 160)
                        searchFood(mSearch.getText().toString(), 8);
                    else if (foodList.size() == 180)
                        searchFood(mSearch.getText().toString(), 9);
                    else if (foodList.size() == 200)
                        searchFood(mSearch.getText().toString(), 10);
                }
            }
        });




    }


    String brand;

    private void searchFood(final String item, final int page_num) {
        new AsyncTask<String, String, String>() {
            @Override
            protected void onPreExecute() {
                mProgressMore.setVisibility(View.VISIBLE);
                mProgressSearch.setVisibility(View.VISIBLE);

            }

            @Override
            protected String doInBackground(String... arg0) {
                JSONObject food = mFatSecretSearch.searchFood(item, page_num);
                JSONArray FOODS_ARRAY;
                try {
                    if (food != null) {
                        FOODS_ARRAY = food.getJSONArray("food");
                        if (FOODS_ARRAY != null) {
                            for (int i = 0; i < FOODS_ARRAY.length(); i++) {
                                JSONObject food_items = FOODS_ARRAY.optJSONObject(i);
                                String food_name = food_items.getString("food_name");
                                String food_description = food_items.getString("food_description");
                                String[] row = food_description.split("-");
                                String id = food_items.getString("food_type");
                                if (id.equals("Brand")) {
                                    brand = food_items.getString("brand_name");
                                }
                                if (id.equals("Generic")) {
                                    brand = "Generic";
                                }
                                String food_id = food_items.getString("food_id");
                                foodList.add(new Food(food_name, row[1].substring(1),
                                        "" + brand, food_id));
                            }
                        }
                    }
                } catch (JSONException exception) {
                    return "Error";
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Error"))
                    Toast.makeText(getActivity().getBaseContext(), "No Items Containing Your Search", Toast.LENGTH_SHORT).show();
                mSearchAdapter.notifyDataSetChanged();
                mSearchAdapter.getCount();
                updateList();
                 mProgressMore.setVisibility(View.INVISIBLE);
                 mProgressSearch.setVisibility(View.INVISIBLE);
                SEARCH_RETAIN = true;
            }
        }.execute();
    }


    private void updateList() {
        if (mSearchAdapter.getCount() == 0) {
            mListView.setVisibility(View.GONE);
        } else {
            mListView.setVisibility(View.VISIBLE);
        }
    }


    /**
     * FatSecret get
     */
    private void getFood(final long id) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... arg0) {
                JSONObject foodGet = mFatSecretGet.getFood(id);
                try {
                    if (foodGet != null) {
                        String food_name = foodGet.getString("food_name");
                        JSONObject servings = foodGet.getJSONObject("servings");

                        JSONObject serving = servings.getJSONObject("serving");
                        String calories = serving.getString("calories");
                        String carbohydrate = serving.getString("carbohydrate");
                        String protein = serving.getString("protein");
                        String fat = serving.getString("fat");
                        String serving_description = serving.getString("serving_description");
                        Log.e("serving_description", serving_description);
                        /**
                         * Displays results in the LogCat
                         */
                        Log.e("food_name", food_name);
                        Log.e("calories", calories);
                        Log.e("carbohydrate", carbohydrate);
                        Log.e("protein", protein);
                        Log.e("fat", fat);
                    }

                } catch (JSONException exception) {
                    return "Error";
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Error"))
                    Toast.makeText(getActivity().getBaseContext(),"No Items Containing Your Search", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }




}
