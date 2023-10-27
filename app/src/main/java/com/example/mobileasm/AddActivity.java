package com.example.mobileasm;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileasm.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    TextView hikeDateInput;
    EditText hikeNameInput, hikeLocationInput, hikeLengthInput, hikeLevelInput, hikeEstimateInput, hikeDescriptionInput;
    CheckBox parkingAvailableCheckbox;
    Button submitAddNewHikeBtn, chooseDateBtn;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, AddActivity.this);
                return true;
            }
        });

        hikeNameInput = findViewById(R.id.hike_name_input);
        hikeLocationInput = findViewById(R.id.hike_location_input);
        hikeDateInput = findViewById(R.id.hike_date_input);
        hikeLengthInput = findViewById(R.id.hike_length_input);
        hikeLevelInput = findViewById(R.id.hike_level_input);
        hikeEstimateInput = findViewById(R.id.hike_estimate_input);
        hikeDescriptionInput = findViewById(R.id.hike_description_input);

        parkingAvailableCheckbox = findViewById(R.id.parking_available_checkbox);

        chooseDateBtn = findViewById(R.id.btn_choose_date);
        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });

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
                        hikeNameInput.setText("");
                        hikeLocationInput.setText("");
                        hikeLengthInput.setText("");
                        hikeLevelInput.setText("");
                        hikeDateInput.setText("00/00/0000");
                        hikeDescriptionInput.setText("");
                        hikeEstimateInput.setText("");
                        parkingAvailableCheckbox.setChecked(false);
                    }
                }
            }
        });
    }

    private void datePickerDialog(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                // Handle the selected date
                hikeDateInput.setText(d + "/" + m + "/" + y);
            }
        }, year, month, day);

        dialog.show();
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
