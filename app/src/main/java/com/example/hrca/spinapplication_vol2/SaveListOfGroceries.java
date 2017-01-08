package com.example.hrca.spinapplication_vol2;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hrca.spinapplication_vol2.helpers.Constants;
import com.example.hrca.spinapplication_vol2.interfaces.RequestInterface;
import com.example.hrca.spinapplication_vol2.model.Food;
import com.example.hrca.spinapplication_vol2.model.Meal;
import com.example.hrca.spinapplication_vol2.model.ServerRequest;
import com.example.hrca.spinapplication_vol2.model.ServerResponse;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.hrca.spinapplication_vol2.R.id.tv_message;

public class SaveListOfGroceries extends AppCompatActivity  implements View.OnClickListener{

    private AppBarLayout mAppBarLayout;

    private SharedPreferences pref;

    //    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMMM yyyy", /*Locale.getDefault()*/Locale.ENGLISH);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", /*Locale.getDefault()*/Locale.ENGLISH);

    private CompactCalendarView mCompactCalendarView;

    private boolean isExpanded = false;

    private Spinner spinnerTypeOfMeal;

    ArrayList<Food> foodList=new ArrayList<>();

    final int CAMERA_CAPTURE = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    //captured picture uri
    private Uri picUri;
    final int PIC_CROP = 2;
    private Button buttonTakePicture;
    private Bitmap thePic;
    private Button buttonChoose;
    private FloatingActionButton saveListButton;
    EditText nameOfMeal;
    private String dateOfMeal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_list_of_groceries);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_save_list);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setTitle("Select a date: ");

        /*
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


*/
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_save_list);
        spinnerTypeOfMeal=(Spinner)findViewById(R.id.spinnerTypeOfMeal);
        saveListButton=(FloatingActionButton)findViewById(R.id.floatingActionButtonSaveMeal);
        saveListButton.setImageBitmap(textAsBitmap("SAVE", 40, Color.WHITE));

        buttonTakePicture = (Button) findViewById(R.id.buttonTakePicture);
        buttonTakePicture.setOnClickListener(this);

        nameOfMeal=(EditText)findViewById(R.id.mealName);

        buttonChoose = (Button) findViewById(R.id.buttonBrowseForImage);
        buttonChoose.setOnClickListener(this);

        foodList = (ArrayList<Food>)getIntent().getSerializableExtra("GroceryFood");
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_of_meal_array, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTypeOfMeal.setAdapter(adapter);
        mCompactCalendarView = (CompactCalendarView) findViewById(R.id.compactcalendar_view);

        // Force English
        mCompactCalendarView.setLocale(TimeZone.getDefault(), Locale.ENGLISH);

        mCompactCalendarView.setShouldDrawDaysHeader(true);

        mCompactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {
            @Override
            public void onDayClick(Date dateClicked) {
                setSubtitle(dateFormat.format(dateClicked));
                dateOfMeal=dateFormat.format(dateClicked);
            }

            @Override
            public void onMonthScroll(Date firstDayOfNewMonth) {
                setSubtitle(dateFormat.format(firstDayOfNewMonth));
            }
        });

        // Set current date to today
        setCurrentDate(new Date());

        final ImageView arrow = (ImageView) findViewById(R.id.date_picker_arrow);

        RelativeLayout datePickerButton = (RelativeLayout) findViewById(R.id.date_picker_button);

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    ViewCompat.animate(arrow).rotation(0).start();
                    mAppBarLayout.setExpanded(false, true);
                    isExpanded = false;
                } else {
                    ViewCompat.animate(arrow).rotation(180).start();
                    mAppBarLayout.setExpanded(true, true);
                    isExpanded = true;
                }
            }
        });


        saveListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "toplel", Snackbar.LENGTH_LONG);
                Meal meal=new Meal();
                meal.setId(UUID.randomUUID().toString());
                meal.setDate(dateOfMeal);
                meal.setName(nameOfMeal.getText().toString());
                meal.setUniqueId(pref.getString(Constants.UNIQUE_ID,null));
                if (thePic == null){
                    thePic = BitmapFactory.decodeResource(getResources(), R.drawable.ic_error);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    byte [] byte_arr = stream.toByteArray();
                    meal.setDecodedBitmap(Base64.encodeToString(byte_arr, Base64.DEFAULT));
                    meal.setTypeOfMeal(spinnerTypeOfMeal.getSelectedItem().toString());
                }
                else {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    thePic.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byte [] byte_arr = stream.toByteArray();
                    meal.setDecodedBitmap(Base64.encodeToString(byte_arr, Base64.DEFAULT));
                    String dummy = Base64.encodeToString(byte_arr, Base64.DEFAULT);
                    meal.setTypeOfMeal(spinnerTypeOfMeal.getSelectedItem().toString());
                }


                Double calories=0.00;
                Double carbohydrate=0.00;
                Double protein=0.00;
                Double fat=0.00;
                for (Food food:foodList) {
                    protein+=food.getProtein();
                    calories+=food.getCalories();
                    fat+=food.getFat();
                    carbohydrate+=food.getCarbohydrate();
                }
                meal.setProtein(protein);
                meal.setCarbohydrate(carbohydrate);
                meal.setCalories(calories);
                meal.setFat(fat);
                meal.setArrayListOfIngridients(foodList);

                saveMeal(meal);

                Toast.makeText(getApplicationContext(), "Meal saved", Toast.LENGTH_LONG);

                Intent startIntent = new Intent(SaveListOfGroceries.this, MainActivity.class);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // clears all previous activities task
                finish(); // destroy current activity..
                startActivity(startIntent); // starts new activity



            }
        });

    }

    public void setCurrentDate(Date date) {
        setSubtitle(dateFormat.format(date));

        if (mCompactCalendarView != null) {
            mCompactCalendarView.setCurrentDate(date);
        }

    }

    @Override
    public void setTitle(CharSequence title) {
        TextView tvTitle = (TextView) findViewById(R.id.title);

        if (tvTitle != null) {
            tvTitle.setText(title);

        }
    }

    public void setSubtitle(String subtitle) {
        TextView datePickerTextView = (TextView) findViewById(R.id.date_picker_text_view);
        TextView textViewOfSelectedDate=(TextView)findViewById(R.id.textViewOfDateSelected);
        if (datePickerTextView != null) {
            datePickerTextView.setText(subtitle);
            textViewOfSelectedDate.setText(subtitle);
            dateOfMeal=subtitle;
        }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonTakePicture) {
            try {
                Log.e("Dugme pritisnuto", "ll");
                //use standard intent to capture an image
                Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                //display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
                toast.show();
            }
        }
//        if (view.getId() == R.id.btnProceed) {
//            Intent intentOCR = new Intent(this, OCRActivity.class);
//            intentOCR.putExtra("slika", thePic);
//            startActivity(intentOCR);
//        }
        if (view.getId() == R.id.buttonBrowseForImage) {
            Intent i = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

            startActivityForResult(i, RESULT_LOAD_IMAGE);
        }
        if(view.getId()==R.id.floatingActionButtonSaveMeal){

            Snackbar.make(view, "toplel", Snackbar.LENGTH_LONG);
            Meal meal=new Meal();
            meal.setId(UUID.randomUUID().toString());
            meal.setDate(dateOfMeal);
            meal.setName(nameOfMeal.getText().toString());
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            thePic.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte [] byte_arr = stream.toByteArray();
            meal.setDecodedBitmap(Base64.encodeToString(byte_arr, Base64.DEFAULT));
            meal.setTypeOfMeal(spinnerTypeOfMeal.getSelectedItem().toString());
            meal.setArrayListOfIngridients(foodList);

            saveMeal(meal);
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_CAPTURE) {
                picUri = data.getData();
                performCrop();
            } else if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ImageView imageView = (ImageView) findViewById(R.id.imageViewMeal);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            } else if (requestCode == PIC_CROP) {
                Log.e("Jedan", "l");

                //get the returned data
                Bundle extras = data.getExtras();
                Log.e("Dva", "l");
                //get the cropped bitmap
                thePic = extras.getParcelable("data");
                Log.e("Tri", "l");
                //retrieve a reference to the ImageView
                ImageView picView = (ImageView) findViewById(R.id.imageViewMeal);

                Log.e("uskoro cu postaviti", "l");


                //display the returned cropped image
                picView.setImageBitmap(thePic);

                Log.e("slika postavljena", "lll");

            }

        }
    }

private void performCrop() {
        try {
        //call the standard crop action intent (the user device may not support it)
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException anfe) {
        //display an error message
        String errorMessage = "Whoops - your device doesn't support the crop action!";
        Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
        toast.show();
        }
        }


    private void saveMeal(Meal meal){

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);


        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.SAVE_MEAL_OPERATION);
        request.setMeal(meal);
        Call<ServerResponse> response = requestInterface.operation(request);

try {
    Gson gson = new Gson();


// 2. Java object to JSON, and assign to a String
    String jsonInString = gson.toJson(request);
    Log.d("JSON OBJECT", jsonInString);
}

catch (Exception e){
    e.getMessage();
}

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){


                    Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT);

                }else {
                    Toast.makeText(getApplicationContext(), resp.getMessage(), Toast.LENGTH_SHORT);

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT);

            }
        });

    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.0f); // round
        int height = (int) (baseline + paint.descent() + 0.0f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

}