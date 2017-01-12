package com.example.hrca.spinapplication_vol2;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.hrca.spinapplication_vol2.interfaces.DataTransferInterface;
import com.example.hrca.spinapplication_vol2.model.Food;

import java.util.ArrayList;
import java.util.List;



public class FoodActivity extends AppCompatActivity implements SearchFoodFragment.OnFragmentInteractionListener, UserListFragment.OnFragmentInteractionListener, DataTransferInterface, UserListFragment.OnHeadlineSelectedListener, View.OnClickListener{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ViewPagerAdapter adapter;
    private ArrayList<Food> foodArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);
        mViewPager.setOffscreenPageLimit(2);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        foodArrayList=new ArrayList<>();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_food, menu);
        return true;
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonAddToList) {
            notifyViewPagerDataSetChanged();
        }
    }

    private void notifyViewPagerDataSetChanged() {
        Log.d("TAG", "\nnotifyDataSetChanged()");
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SearchFoodFragment(), "SEARCH");
        adapter.addFragment(new UserListFragment(), "USER'S LIST");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void addFoodToArrayList(Food food) {
        foodArrayList.add(food);
        mViewPager.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void setValues(ArrayList<Food> arrayListFood) {
        foodArrayList=new ArrayList<>();
        foodArrayList.addAll(arrayListFood);
        mViewPager.getAdapter().notifyDataSetChanged();
        for (Food food:foodArrayList
                ) {
            Log.d("TAG_USER_LISST", food.getDescription());
        }
    }

    @Override
    public ArrayList<Food> getValues() {
        return foodArrayList;
    }

    @Override
    public void onArticleSelected(int position) {

    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        @Override
        public int getItemPosition(Object object) {


            if (object instanceof UserListFragment) {
                ((UserListFragment) object).listViewConfigurations(foodArrayList);
            }
            //don't return POSITION_NONE, avoid fragment recreation.
            return super.getItemPosition(object);

        }

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
//  item
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int position) {
            Log.d("TAG", "\nonPageSelected(" + position + ")");
            notifyViewPagerDataSetChanged();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int state) {}
    };
}

