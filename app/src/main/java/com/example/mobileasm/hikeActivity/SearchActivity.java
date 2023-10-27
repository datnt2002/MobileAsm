package com.example.mobileasm.hikeActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class SearchActivity extends AppCompatActivity {
    FloatingActionButton addHikeBtnFloatingMenu;
    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, SearchActivity.this);
                return true;
            }
        });

        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}