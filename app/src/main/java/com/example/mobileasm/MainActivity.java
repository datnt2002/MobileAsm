package com.example.mobileasm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mobileasm.hikeActivity.AddActivity;
import com.example.mobileasm.hikeActivity.CustomAdapter;
import com.example.mobileasm.models.HikeModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton addHikeBtnFloatingMenu;
    BottomNavigationView nav;
    RecyclerView recyclerView;
    MyDatabaseHelper db;
    ArrayList<HikeModel> hikeList;
    CustomAdapter customAdapter;
    Button resetDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nav = findViewById(R.id.bottomNavigationView);

        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, MainActivity.this);
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


        recyclerView = findViewById(R.id.recyclerView);
        db = new MyDatabaseHelper(MainActivity.this);
        hikeList = db.getAllHike();

        customAdapter = new CustomAdapter(MainActivity.this,this, hikeList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        resetDb = findViewById(R.id.reset_database);
        resetDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete all hikes?");
                builder.setMessage("Are you sure to delete all hikes");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteAllHikes();
                        hikeList.clear();
                        customAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }
}