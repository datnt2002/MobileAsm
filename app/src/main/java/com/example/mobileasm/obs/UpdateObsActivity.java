package com.example.mobileasm.obs;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class UpdateObsActivity extends AppCompatActivity {
    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;

    EditText obsNameEdit, obsSightingEdit, obsWeatherEdit, obsCommentEdit;
    TextView obsDateEdit, obsTimeEdit, hikeNameTextview;
    Button btnChooseEditObsDate, btnChooseEditObsTime, btnSubmitEditObs;
    ImageView obsImageEdit;
    private byte[] imageBytes;
    int obsId;
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
    int currentMinute = calendar.get(Calendar.MINUTE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_obs);

        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, UpdateObsActivity.this);
                return true;
            }
        });
        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateObsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        obsId = getIntent().getIntExtra("obsId", -1);

        obsNameEdit = findViewById(R.id.obs_name_edit);
        obsSightingEdit = findViewById(R.id.obs_sighting_edit);
        obsWeatherEdit = findViewById(R.id.obs_weather_edit);
        obsCommentEdit = findViewById(R.id.obs_comment_edit);
        obsDateEdit = findViewById(R.id.obs_date_edit);
        obsTimeEdit = findViewById(R.id.obs_time_edit);

        obsImageEdit = findViewById(R.id.obs_image_edit);
        obsImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(UpdateObsActivity.this)
                        .crop()
                        .compress(1024)
                        .start();
            }
        });

        btnChooseEditObsDate = findViewById(R.id.btn_choose_edit_obs_date);
        btnChooseEditObsDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });

        btnChooseEditObsTime = findViewById(R.id.btn_choose_edit_obs_time);
        btnChooseEditObsTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog();
            }
        });

        btnSubmitEditObs = findViewById(R.id.btn_submit_edit_obs);
        btnSubmitEditObs.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                boolean isValidated = validateInput();
                if (isValidated){
                    ObservationsModel obs = new ObservationsModel(obsId, obsNameEdit.getText().toString(), obsDateEdit.getText().toString(), obsTimeEdit.getText().toString(), obsCommentEdit.getText().toString(), obsSightingEdit.getText().toString(), obsWeatherEdit.getText().toString(), imageBytes);
                    MyDatabaseHelper db = new MyDatabaseHelper(UpdateObsActivity.this);
                    db.updateObservationData(obs);
                }
            }
        });
        getObservationData();
    }

    void getObservationData(){
        if (getIntent().hasExtra("obsId")){
            int obsId = getIntent().getIntExtra("obsId", -1);
            Log.d("YourTag1", "Clicked ID: " + obsId);
            MyDatabaseHelper db = new MyDatabaseHelper(UpdateObsActivity.this);
            Cursor result = db.getObservationDetail(obsId);
            if (result.getCount() == 0) {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            }else{
                if (result.moveToNext()){
                    String name = result.getString(1);
                    String date = result.getString(2);
                    String time = result.getString(3);
                    String sighting = result.getString(4);
                    String weather = result.getString(5);
                    byte[] imageData = result.getBlob(6);
                    String comment = result.getString(7);

                    obsNameEdit.setText(name);
                    obsDateEdit.setText(date);
                    obsTimeEdit.setText(time);
                    obsSightingEdit.setText(sighting);
                    obsWeatherEdit.setText(weather);
                    obsCommentEdit.setText(comment);

                    if (imageData != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        obsImageEdit.setImageBitmap(bitmap);
                    } else {
                        // Set a default image if the avatar data is null
                        obsImageEdit.setImageResource(R.drawable.ic_image);
                    }
                }
            }
        }
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
                obsImageEdit.setImageURI(imageUri);
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
                obsDateEdit.setText(d + "/" + m + "/" + y);
            }
        }, year, month, day);
        dialog.show();
    }

    private void timePickerDialog(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minutes) {
                obsTimeEdit.setText(hour + ":" + minutes);
            }
        }, currentHour, currentMinute, true);
        dialog.show();
    }

    private boolean validateInput(){
        if (TextUtils.isEmpty(obsNameEdit.getText().toString())){
            obsNameEdit.setError("Please enter obs name");
            return false;
        }

        return true;
    }
}