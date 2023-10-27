package com.example.mobileasm.obs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.hikeActivity.AddActivity;
import com.example.mobileasm.hikeActivity.DetailActivity;
import com.example.mobileasm.models.ObservationsModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

public class AddObsActivity extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;
    EditText obsNameInput, obsSightingInput, obsWeatherInput, obsCommentInput;
    TextView obsDateInput, obsTimeInput, hikeNameTextview;
    Button btnChooseObsDate, btnChooseObsTime, btnSubmitAddObs;
    ImageView obsImage;
    private byte[] imageBytes;


    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinute = calendar.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obs);

        int hikeId = getIntent().getIntExtra("hikeId", -1);
        String hikeName = getIntent().getStringExtra("hikeName");

        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, AddObsActivity.this);
                return true;
            }
        });
        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddObsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        hikeNameTextview = findViewById(R.id.hike_name_in_add_obs);
        hikeNameTextview.setText(hikeName);

        obsNameInput = findViewById(R.id.obs_name_input);
        obsSightingInput = findViewById(R.id.obs_sighting_input);
        obsWeatherInput = findViewById(R.id.obs_weather_input);
        obsCommentInput = findViewById(R.id.obs_comment_input);
        obsDateInput = findViewById(R.id.obs_date_input);
        obsDateInput.setText(day + "/" + month + "/" + year);
        obsTimeInput = findViewById(R.id.obs_time_input);
        obsTimeInput.setText(currentHour + ":" + currentMinute);

        obsImage = findViewById(R.id.obs_image);
        obsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(AddObsActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .start();
            }
        });

        btnChooseObsDate = findViewById(R.id.btn_choose_obs_date);
        btnChooseObsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });


        btnChooseObsTime = findViewById(R.id.btn_choose_obs_time);
        btnChooseObsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog();
            }
        });

        btnSubmitAddObs = findViewById(R.id.btn_submit_add_new_obs);
        btnSubmitAddObs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                boolean isValidated = validateInput();
                if (isValidated){
                    ObservationsModel obs = new ObservationsModel(-1, obsNameInput.getText().toString(), obsDateInput.getText().toString(), obsTimeInput.getText().toString(), obsCommentInput.getText().toString(), obsSightingInput.getText().toString(), obsWeatherInput.getText().toString(), imageBytes);

                    MyDatabaseHelper db = new MyDatabaseHelper(AddObsActivity.this);
                    boolean isSuccess = db.addObservation(obs, hikeId);
                    if (isSuccess){
                        obsNameInput.setText("");
                        obsSightingInput.setText("");
                        obsWeatherInput.setText("");
                        obsCommentInput.setText("");
                        obsDateInput.setText(day + "/" + month + "/" + year);
                        obsTimeInput.setText(currentHour + ":" + currentMinute);
                        obsImage.setImageURI(null);
                        imageBytes = null;
                    }
                }
            }
        });
    }

    private byte[] getObsImage(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                imageBytes = getObsImage(inputStream);
                obsImage.setImageURI(imageUri);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            // Handle errors from ImagePicker
            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();
        }
    }

    private void datePickerDialog(){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                // Handle the selected date
                obsDateInput.setText(d + "/" + m + "/" + y);
            }
        }, year, month, day);
        dialog.show();
    }

    private void timePickerDialog(){
        Calendar currentTime = Calendar.getInstance();
        int currentHour = currentTime.get(Calendar.HOUR_OF_DAY);
        int currentMinute = currentTime.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                obsTimeInput.setText(hour + ":" + minutes);
            }
        }, currentHour, currentMinute, true);
        dialog.show();
    }

    private boolean validateInput(){
        if (TextUtils.isEmpty(obsNameInput.getText().toString())){
            obsNameInput.setError("Please enter obs name");
            return false;
        }

        return true;
    }
}