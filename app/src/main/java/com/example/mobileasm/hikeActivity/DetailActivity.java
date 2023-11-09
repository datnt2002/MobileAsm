package com.example.mobileasm.hikeActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.obs.AddObsActivity;
import com.example.mobileasm.obs.ListObsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
public class DetailActivity extends AppCompatActivity {
    TextView hikeNameDetailTv, hikeLocationDetailTv, hikeDateDetailTv, hikeLengthDetailTv, hikeLevelDetailTv, hikeEstimateDetailTv, hikeDescriptionDetailTv;
    CheckBox parkingAvailable;
    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;
    Button addNewObsBtn, viewObsBtn;
    int id;
    String hikeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, DetailActivity.this);
                return true;
            }
        });
        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
        addNewObsBtn = findViewById(R.id.btn_add_observation);
        addNewObsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, AddObsActivity.class);
                intent.putExtra("hikeName", hikeName);
                intent.putExtra("hikeId", id);
                startActivity(intent);
            }
        });
        viewObsBtn = findViewById(R.id.btn_view_observation);
        viewObsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, ListObsActivity.class);
                intent.putExtra("hikeName", hikeName);
                intent.putExtra("hikeId", id);
                startActivity(intent);
            }
        });
        hikeNameDetailTv = findViewById(R.id.hike_name_detail);
        hikeLocationDetailTv = findViewById(R.id.hike_location_detail);
        hikeDateDetailTv = findViewById(R.id.hike_date_detail);
        hikeLengthDetailTv = findViewById(R.id.hike_length_detail);
        hikeLevelDetailTv = findViewById(R.id.hike_level_detail);
        hikeEstimateDetailTv = findViewById(R.id.hike_estimate_detail);
        hikeDescriptionDetailTv = findViewById(R.id.hike_description_detail);
        parkingAvailable = findViewById(R.id.parking_available_detail);
        getIntentData();
    }
    void getIntentData(){
        if (getIntent().hasExtra("id")){
            id = getIntent().getIntExtra("id", -1);
            MyDatabaseHelper db = new MyDatabaseHelper(DetailActivity.this);
            Cursor result = db.getData(id);
            if (result.getCount() == 0) {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            }else{
                if (result.moveToNext()){
                    hikeName = result.getString(1);
                    String location = result.getString(2);
                    String date = result.getString(3);
                    boolean available = result.getInt(4) == 1;
                    int hikeLength = result.getInt(5);
                    int hikeLevel = result.getInt(6);
                    int hikeEstimate = result.getInt(7);
                    String hikeDescription = result.getString(8);
                    hikeNameDetailTv.setText(hikeName);
                    hikeLocationDetailTv.setText(location);
                    hikeDateDetailTv.setText(date);
                    hikeLengthDetailTv.setText(String.valueOf(hikeLength));
                    hikeLevelDetailTv.setText(String.valueOf(hikeLevel));
                    hikeEstimateDetailTv.setText(String.valueOf(hikeEstimate));
                    hikeDescriptionDetailTv.setText(hikeDescription);
                    parkingAvailable.setChecked(available);
                }
            }
        }
    }
}