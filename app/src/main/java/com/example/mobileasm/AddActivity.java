package com.example.mobileasm;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText hikeNameInput, hikeLocationInput, hikeDateInput, hikeLengthInput, hikeLevelInput, hikeEstimateInput, hikeDescriptionInput;
    CheckBox parkingAvailableCheckbox;
    Button submitAddNewHikeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        hikeNameInput = findViewById(R.id.hike_name_input);
        hikeLocationInput = findViewById(R.id.hike_location_input);
        hikeDateInput = findViewById(R.id.hike_date_input);
        hikeLengthInput = findViewById(R.id.hike_length_input);
        hikeLevelInput = findViewById(R.id.hike_level_input);
        hikeEstimateInput = findViewById(R.id.hike_estimate_input);
        hikeDescriptionInput = findViewById(R.id.hike_description_input);

        parkingAvailableCheckbox = findViewById(R.id.parking_available_checkbox);

        submitAddNewHikeBtn = findViewById(R.id.btn_submit_add_new_hike);
        submitAddNewHikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidated = validateInput();
                if (isValidated){
                    HikeModel newHike = new HikeModel(-1, hikeNameInput.getText().toString(), hikeLocationInput.getText().toString(), hikeDateInput.getText().toString(), parkingAvailableCheckbox.isChecked() ,Integer.parseInt(hikeLengthInput.getText().toString()), Integer.parseInt(hikeLevelInput.getText().toString()), Integer.parseInt(hikeEstimateInput.getText().toString()), hikeDescriptionInput.getText().toString());

                    MyDatabaseHelper db = new MyDatabaseHelper(AddActivity.this);
                    boolean isSuccess = db.addHike(newHike);
                    if (isSuccess){
                        Toast.makeText(AddActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddActivity.this, "not ok", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validateInput(){
        if (TextUtils.isEmpty(hikeNameInput.getText().toString())){
            hikeNameInput.setError("Please enter hike name");
            return false;
        }

        // Validate hike location
        if (TextUtils.isEmpty(hikeLocationInput.getText().toString())) {
            hikeLocationInput.setError("Please enter hike location");
            return false;
        }

        // Validate hike date
        if (TextUtils.isEmpty(hikeDateInput.getText().toString())) {
            hikeDateInput.setError("Please enter hike date");
            return false;

        }

        // Validate hike length
        if (TextUtils.isEmpty(hikeLengthInput.getText().toString())) {
            hikeLengthInput.setError("Please enter hike length");
            return false;

        }

        // Validate hike level
        if (TextUtils.isEmpty(hikeLevelInput.getText().toString())) {
            hikeLevelInput.setError("Please enter hike level");
            return false;

        }

        // Validate hike estimate
        if (TextUtils.isEmpty(hikeEstimateInput.getText().toString())) {
            hikeEstimateInput.setError("Please enter hike estimate");
            return false;
        }
        return true;
    }
}
