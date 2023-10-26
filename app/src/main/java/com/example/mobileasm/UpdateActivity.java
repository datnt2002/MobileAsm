package com.example.mobileasm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText hikeNameEditText, hikeLocationEditText, hikeLengthEditText,  hikeLevelEditText, hikeEstimateEditText, hikeDescriptionEditText;
    CheckBox availableEditCheck;

    TextView hikeDateEditText;
    Button updateBtn;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        hikeNameEditText = findViewById(R.id.hike_name_edit_input);
        hikeLocationEditText = findViewById(R.id.hike_location_edit_input);
        hikeDateEditText = findViewById(R.id.hike_date_edit_input);
        hikeLengthEditText = findViewById(R.id.hike_length_edit_input);
        hikeLevelEditText = findViewById(R.id.hike_level_edit_input);
        hikeEstimateEditText = findViewById(R.id.hike_estimate_edit_input);
        hikeDescriptionEditText = findViewById(R.id.hike_description_edit_input);
        availableEditCheck = findViewById(R.id.parking_available_edit_checkbox);
        updateBtn = findViewById(R.id.btn_submit_edit_hike);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getIntentData();
    }

    void getIntentData(){
        if (getIntent().hasExtra("position")){
            position = getIntent().getIntExtra("position", -1);
            MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
            Cursor result = db.getData(position);
            if (result.getCount() == 0) {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            }else{
                if (result.moveToNext()){
                    String hikeName = result.getString(1);
                    String location = result.getString(2);
                    String date = result.getString(3);
                    boolean available = result.getInt(4) == 1;
                    int hikeLength = result.getInt(5);
                    int hikeLevel = result.getInt(6);
                    int hikeEstimate = result.getInt(7);
                    String hikeDescription = result.getString(8);
                    hikeNameEditText.setText(hikeName);
                    hikeLocationEditText.setText(location);
                    hikeDateEditText.setText(date);
                    hikeLengthEditText.setText(String.valueOf(hikeLength));
                    hikeLevelEditText.setText(String.valueOf(hikeLevel));
                    hikeEstimateEditText.setText(String.valueOf(hikeEstimate));
                    hikeDescriptionEditText.setText(hikeDescription);
                    availableEditCheck.setChecked(available);
                }
            }
        }
    }
}
