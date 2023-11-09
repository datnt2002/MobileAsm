package com.example.mobileasm.hikeActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import java.util.Calendar;
public class UpdateActivity extends AppCompatActivity {
    EditText hikeNameEditText, hikeLocationEditText, hikeLengthEditText,  hikeLevelEditText, hikeEstimateEditText, hikeDescriptionEditText;
    CheckBox availableEditCheck;
    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;
    TextView hikeDateEditText;
    Button updateBtn, chooseDateBtn;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, UpdateActivity.this);
                return true;
            }
        });
        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        hikeNameEditText = findViewById(R.id.hike_name_edit_input);
        hikeLocationEditText = findViewById(R.id.hike_location_edit_input);
        hikeDateEditText = findViewById(R.id.hike_date_edit_input);
        hikeLengthEditText = findViewById(R.id.hike_length_edit_input);
        hikeLevelEditText = findViewById(R.id.hike_level_edit_input);
        hikeEstimateEditText = findViewById(R.id.hike_estimate_edit_input);
        hikeDescriptionEditText = findViewById(R.id.hike_description_edit_input);
        availableEditCheck = findViewById(R.id.parking_available_edit_checkbox);
        chooseDateBtn = findViewById(R.id.btn_choose_date_edit);
        chooseDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });
        updateBtn = findViewById(R.id.btn_submit_edit_hike);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValidated = validateInput();
                if (isValidated){
                    HikeModel hikeUpdate = new HikeModel(id, hikeNameEditText.getText().toString(), hikeLocationEditText.getText().toString(), hikeDateEditText.getText().toString(), availableEditCheck.isChecked(),Integer.parseInt(hikeLengthEditText.getText().toString()), Integer.parseInt(hikeLevelEditText.getText().toString()), Integer.parseInt(hikeEstimateEditText.getText().toString()), hikeDescriptionEditText.getText().toString());
                    MyDatabaseHelper myDb = new MyDatabaseHelper(UpdateActivity.this);
                    myDb.updateData(id, hikeUpdate);
                }else{
                    Toast.makeText(UpdateActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        getIntentData();
    }
    void getIntentData(){
        if (getIntent().hasExtra("id")){
            id = getIntent().getIntExtra("id", -1);
            MyDatabaseHelper db = new MyDatabaseHelper(UpdateActivity.this);
            Cursor result = db.getData(id);
            if (result.getCount() == 0) {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            }else{
                if (result.moveToFirst()){
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
                hikeDateEditText.setText(d + "/" + m + "/" + y);
            }
        }, year, month, day);
        dialog.show();
    }
    private boolean validateInput(){
        if (TextUtils.isEmpty(hikeNameEditText.getText().toString())){
            hikeNameEditText.setError("Please enter hike name");
            return false;
        }
        // Validate hike location
        if (TextUtils.isEmpty(hikeLocationEditText.getText().toString())) {
            hikeLocationEditText.setError("Please enter hike location");
            return false;
        }

        // Validate hike length
        if (TextUtils.isEmpty(hikeLengthEditText.getText().toString())) {
            hikeLengthEditText.setError("Please enter hike length");
            return false;
        }
        // Validate hike level
        if (TextUtils.isEmpty(hikeLevelEditText.getText().toString())) {
            hikeLevelEditText.setError("Please enter hike level");
            return false;
        }
        // Validate hike estimate
        if (TextUtils.isEmpty(hikeEstimateEditText.getText().toString())) {
            hikeEstimateEditText.setError("Please enter hike estimate");
            return false;
        }
        return true;
    }
}
