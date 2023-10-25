package com.example.mobileasm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.mobileasm.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FloatingActionButton addHikeBtnFloatingMenu;

    BottomNavigationView nav;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                Intent intent;
                if (itemId == R.id.home) {
                    intent = new Intent(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
//                } else if (itemId == R.id.shorts) {
//                    replaceFragment(new ShortFragment());
//                } else if (itemId == R.id.subscriptions) {
//                    replaceFragment(new SubscriptionFragment());
//                } else if (itemId == R.id.library) {
//                    replaceFragment(new LibraryFragment());
//                }
                }
                return true;
            }
        });

        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

    }
}