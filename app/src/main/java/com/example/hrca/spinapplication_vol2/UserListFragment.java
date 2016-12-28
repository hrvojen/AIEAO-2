package com.example.hrca.spinapplication_vol2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrca.spinapplication_vol2.adapters.SpinnerAdapter;
import com.example.hrca.spinapplication_vol2.interfaces.DataTransferInterface;
import com.example.hrca.spinapplication_vol2.adapters.UserListAdapter;
import com.example.hrca.spinapplication_vol2.fatsecretimplementation.FatSecretGet;
import com.example.hrca.spinapplication_vol2.model.Food;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


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
    private List<Food> foodListBasedOnServings=new ArrayList<>();
    private static final String FOOD_KEY = "food_key";
    private ArrayList<Food> finalFoodList;
    private TextView dummyTextView;
    private OnFragmentInteractionListener mListener;
    OnHeadlineSelectedListener mCallback;
    RecyclerView recyclerView;
    private EditText editTextNumberOfUnits;
    private FatSecretGet mFatSecretGet;
    private SpinnerAdapter spinnerAdapter;
    private Spinner spinnerUnits;
    private EditText editTextOfUnits;

    @Override
    public void setValues(ArrayList<Food> arrayList) {
        foodList.addAll(arrayList);





//
//        userListAdapter = new UserListAdapter(getActivity(), foodList);
//        userListAdapter.registerDataSetObserver(new DataSetObserver() {
//            @Override
//            public void onChanged() {
//                super.onChanged();
//                Log.d("Foobar", " "+userListAdapter.getCount());
//            }
//        });
//        mListView.setAdapter(userListAdapter);
//        Log.d("TAG_USER_LIST", "config-inner");
//
//
//
//        updateList();
    }

    @Override
    public ArrayList<Food> getValues() {
        return null;
    }

    @Override
    public void addFoodToArrayList(Food food) {

    }

    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int position);
    }


    public UserListFragment() {
        // Required empty public constructor
    }

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
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        FloatingActionButton floatingButtonProceed= (FloatingActionButton)getView().findViewById(R.id.floatingProceedToSave);

        floatingButtonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveListActivity=new Intent(getActivity(), SaveListOfGroceries.class);
                //saveListActivity.putExtra("grocery-food", finalFoodList);

                saveListActivity.putExtra("GroceryFood",finalFoodList);
                startActivity(saveListActivity);
            }
        });

        mFatSecretGet = new FatSecretGet();



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



    public void listViewConfigurations(final ArrayList<Food> tmpFoodList) {
//        ******************
        Log.d("TAG_USER_LIST", "config");



        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {

//                if (tmpFoodList.get(position).getBrand().contains("Generic")){



             getFood(tmpFoodList.get(position).getID());



                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                LayoutInflater inflater = getActivity().getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.dialog_user_list_item, null);
                dialogBuilder.setView(dialogView);

                dialogBuilder.setTitle(tmpFoodList.get(position).getFoodName());
                dialogBuilder.setMessage("Enter values");


                spinnerUnits=(Spinner)dialogView.findViewById(R.id.spinnerOfUnits);
                editTextOfUnits=(EditText)dialogView.findViewById(R.id.editTextOfUnits);



                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //do something with edt.getText().toString();
                        String foodDescription=foodListBasedOnServings.get(spinnerUnits.getSelectedItemPosition()).getDescription();
                        String numberOfUnits=editTextOfUnits.getText().toString();
                        Log.d("TAG_LOGA", " "+numberOfUnits);

                        Food selectedFoodByUser=null;

                        for (Food selectedFood : foodListBasedOnServings) {
                            if (selectedFood.getDescription() == foodDescription){
                                selectedFoodByUser=selectedFood;
                                selectedFoodByUser.setNumberOfUnits(numberOfUnits);
                                selectedFoodByUser.setID(tmpFoodList.get(position).getID());


                                Boolean wantToCloseDialog = (editTextOfUnits.getText().toString().trim().isEmpty());
                                if (wantToCloseDialog){
                                    editTextOfUnits.requestFocus();
                                    break;
                                }
                                else {


                                    Double doubleValueOfNumberOfUnits=Double.parseDouble(numberOfUnits);

                                    selectedFoodByUser.setCalories(selectedFoodByUser.getCalories()*doubleValueOfNumberOfUnits);

                                    selectedFoodByUser.setFat(selectedFoodByUser.getFat()*doubleValueOfNumberOfUnits);

                                    selectedFoodByUser.setCarbohydrate(selectedFoodByUser.getCarbohydrate()*doubleValueOfNumberOfUnits);

                                    selectedFoodByUser.setProtein(selectedFoodByUser.getProtein()*doubleValueOfNumberOfUnits);




                                    // dialog.dismiss();

                                }





         //                       Double doubleValueOfNumberOfUnits=Double.parseDouble(numberOfUnits);

         //                       selectedFoodByUser.setCalories(selectedFoodByUser.getCalories()*doubleValueOfNumberOfUnits);

        //                        selectedFoodByUser.setFat(selectedFoodByUser.getFat()*doubleValueOfNumberOfUnits);

         //                       selectedFoodByUser.setCarbohydrate(selectedFoodByUser.getCarbohydrate()*doubleValueOfNumberOfUnits);

         //                       selectedFoodByUser.setProtein(selectedFoodByUser.getProtein()*doubleValueOfNumberOfUnits);


                            }
                        }
                        tmpFoodList.set(position, selectedFoodByUser);

                        userListAdapter = new UserListAdapter(getActivity(), tmpFoodList);

                        finalFoodList=new ArrayList<Food>();
                        finalFoodList=tmpFoodList;

                        mListView.setAdapter(userListAdapter);
                        userListAdapter.notifyDataSetChanged();




                    }
                });
                dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //pass
                    }
                });
                AlertDialog b = dialogBuilder.create();




//                spinnerAdapter=new SpinnerAdapter(getActivity(), R.layout.spinner_item, foodListBasedOnServings);
//
//                spinnerUnits.setAdapter(spinnerAdapter);

                b.show();
//            }

//                else {
//                    getFood(tmpFoodList.get(position).getID());
//                }



            }


        });



        userListAdapter = new UserListAdapter(getActivity(), tmpFoodList);
        userListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                Log.d("Foobar", " "+userListAdapter.getCount());
            }
        });
        mListView.setAdapter(userListAdapter);

        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = mListView.getCheckedItemCount();
                mode.setTitle(checkedCount + " Selected");
                userListAdapter.toggleSelection(position);
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.user_list_, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = userListAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                Food selectedFood = userListAdapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
                                userListAdapter.remove(selectedFood);

                                for (Food food:userListAdapter.getUserFoodList()
                                     ) {
                                    Log.e("Leftover items are: ", food.getFoodName());
                                }

                                FoodActivity foodActivity=(FoodActivity) getActivity();
                                foodActivity.setValues(userListAdapter.getUserFoodList());

                            }
                        }
                        // Close CAB
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                userListAdapter.removeSelection();
            }
        });

        Log.d("TAG_USER_LIST", "config-inner");
//*******************



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


    private void getFood(final long id) {

        foodListBasedOnServings.clear();

        new AsyncTask<String, String, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected String doInBackground(String... arg0) {
                JSONObject foodGet = mFatSecretGet.getFood(id);
                try {
                    if (foodGet != null) {
                        String food_name = foodGet.getString("food_name");

                        String food_brand=foodGet.getString("food_type");

                        if (food_brand.contains("Brand")){
                            System.out.print(food_brand);

                            JSONObject servings = foodGet.getJSONObject("servings");

                            JSONObject serving = servings.getJSONObject("serving");

                            Double calories = Double.parseDouble(serving.getString("calories"));
                            Double carbohydrate = Double.parseDouble(serving.getString("carbohydrate"));
                            Double protein = Double.parseDouble(serving.getString("protein"));
                            Double fat = Double.parseDouble(serving.getString("fat"));
                            String mesaurment_description = serving.getString("measurement_description");

                            Food foodBasedOnServing = new Food(food_name,calories,carbohydrate,protein,fat,mesaurment_description, null);

                            foodListBasedOnServings.add(foodBasedOnServing);

                        }
                        else {



                        JSONObject servings = foodGet.getJSONObject("servings");

                        JSONArray serving = servings.getJSONArray("serving");

                        System.out.print(servings.length());


                        for (int i=0;i<serving.length();i++){
                            JSONObject servingObject=serving.getJSONObject(i);

                            Double calories = Double.parseDouble(servingObject.getString("calories"));
                            Double carbohydrate = Double.parseDouble(servingObject.getString("carbohydrate"));
                            Double protein = Double.parseDouble(servingObject.getString("protein"));
                            Double fat = Double.parseDouble(servingObject.getString("fat"));
                            String mesaurment_description = servingObject.getString("measurement_description");

                            Food foodBasedOnServing = new Food(food_name,calories,carbohydrate,protein,fat,mesaurment_description, null);

                            foodListBasedOnServings.add(foodBasedOnServing);
                        }

                        }

                    }

                } catch (JSONException exception) {
                    String s = exception.getMessage();
                    exception.getMessage();
                    return "Error";
                }
                return "";
            }

            @Override
            protected void onPostExecute(String result) {
                spinnerAdapter=new SpinnerAdapter(getActivity(), R.layout.spinner_item, foodListBasedOnServings);

                spinnerUnits.setAdapter(spinnerAdapter);
                super.onPostExecute(result);
                if (result.equals("Error"))
                    Toast.makeText(getActivity().getBaseContext(),"No Items Containing Your Search", Toast.LENGTH_SHORT).show();
            }
        }.execute();
    }


}
