package com.example.hrca.spinapplication_vol2;

import android.app.Fragment;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hrca.spinapplication_vol2.fragments.ChartFragment;
import com.example.hrca.spinapplication_vol2.fragments.GetMealFragment;
import com.example.hrca.spinapplication_vol2.fragments.LoginFragment;
import com.example.hrca.spinapplication_vol2.fragments.ProfileFragment;
import com.example.hrca.spinapplication_vol2.helpers.Constants;
import com.example.hrca.spinapplication_vol2.model.Food;

import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChartFragment.OnFragmentInteractionListener {

    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FancyButton fancyButton = new FancyButton(this);
        fancyButton=(FancyButton)findViewById(R.id.btn_search_for_food);
        fancyButton.setIconResource(R.drawable.ic_search_white_24dp);
        fancyButton.setFontIconSize(40);

        FancyButton fancyLoginButton=new FancyButton(this);
        fancyLoginButton=(FancyButton)findViewById(R.id.login_or_register_button);
        fancyButton.setFontIconSize(40);

        fancyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, FoodActivity.class);
                startActivity(i);
            }
        });

        fancyLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this, LoginOrRegisterActivity.class);
                startActivity(i);
            }
        });

        //initFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_search){

            Intent i = new Intent(MainActivity.this, FoodActivity.class);
            startActivity(i);

        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent homeIntent=new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        }

        else if (id == R.id.nav_login_register) {





            Intent loginIntent=new Intent(this, LoginOrRegisterActivity.class);
            startActivity(loginIntent);

        }


        else if (id == R.id.nav_meal_history) {
            Intent loginIntent=new Intent(this, DietCalendarActivity.class);
            startActivity(loginIntent);

        } else if (id == R.id.nav_nutritions) {




            if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){


                Intent loginIntent=new Intent(this, NutritionDataActivity.class);
                startActivity(loginIntent);

            }else {


                Intent loginIntent=new Intent(this, LoginOrRegisterActivity.class);
                startActivity(loginIntent);

            }





        }
        //else if (id == R.id.nav_manage) {

        //}
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initFragment(){
        Fragment fragment;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            ChartFragment chartFragment=ChartFragment.newInstance("0", "");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFrame,chartFragment);
            fragmentTransaction.commit();
        }else {
            Toast.makeText(this, "Go To Login Page", Toast.LENGTH_LONG);
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
