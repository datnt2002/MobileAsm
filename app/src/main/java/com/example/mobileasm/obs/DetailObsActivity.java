package com.example.mobileasm.obs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.hikeActivity.AddActivity;
import com.example.mobileasm.hikeActivity.DetailActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class DetailObsActivity extends AppCompatActivity {
    BottomNavigationView nav;
    FloatingActionButton addHikeBtnFloatingMenu;

    TextView obsName, obsDate, obsTime, obsSighting, obsWeather, obsComment;
    ImageView obsImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_obs);

        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, DetailObsActivity.this);
                return true;
            }
        });

        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailObsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        obsName = findViewById(R.id.obs_name_detail);
        obsDate = findViewById(R.id.obs_date_detail);
        obsTime = findViewById(R.id.obs_time_detail);
        obsSighting = findViewById(R.id.obs_sighting_detail);
        obsWeather = findViewById(R.id.obs_weather_detail);
        obsComment = findViewById(R.id.obs_comment_detail);
        obsImage = findViewById(R.id.obs_image_detail);
    }
}