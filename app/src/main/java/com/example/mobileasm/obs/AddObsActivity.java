package com.example.mobileasm.obs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.hikeActivity.AddActivity;
import com.example.mobileasm.hikeActivity.DetailActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class AddObsActivity extends AppCompatActivity {

    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;
    EditText obsNameInput, obsSightingInput, obsWeatherInput, obsCommentInput;
    TextView obsDateInput, obsTimeInput, hikeNameTextview;
    Button btnChooseObsDate, btnChooseObsTime, btnSubmitAddObs;
    ImageView obsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_obs);

        int hikeId = getIntent().getIntExtra("hikeId", -1);
        String hikeName = getIntent().getStringExtra("hikeName");
        Log.d("add obs", " " + hikeId);
        Log.d("add obs1", " " + hikeName);

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
        obsTimeInput = findViewById(R.id.obs_time_input);

        obsImage = findViewById(R.id.obs_image);

        btnSubmitAddObs = findViewById(R.id.btn_submit_add_new_obs);
    }
}