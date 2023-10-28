package com.example.mobileasm.obs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileasm.MainActivity;
import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.hikeActivity.AddActivity;
import com.example.mobileasm.models.ObservationsModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class ListObsActivity extends AppCompatActivity {
    FloatingActionButton addHikeBtnFloatingMenu;
    BottomNavigationView nav;
    RecyclerView obsRecyclerView;
    MyDatabaseHelper db;
    ArrayList<ObservationsModel> obsList;
    ObsAdapter obsAdapter;
    TextView title;

    Button deleteAllObsOfHikeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_obs);

        nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                NavigatorHandler navHandle = new NavigatorHandler();
                navHandle.navigateTo(itemId, ListObsActivity.this);
                return true;
            }
        });
        addHikeBtnFloatingMenu = findViewById(R.id.add_hike_btn_floating);
        addHikeBtnFloatingMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListObsActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        int hikeId = getIntent().getIntExtra("hikeId", -1);
        String hikeName = getIntent().getStringExtra("hikeName");

        title = findViewById(R.id.title_list_obs);
        title.setText("Observations Of " + hikeName);

        obsRecyclerView = findViewById(R.id.obsRecyclerView);
        db = new MyDatabaseHelper(ListObsActivity.this);
        obsList = db.getAllObsOfHike(hikeId);

        obsAdapter = new ObsAdapter(ListObsActivity.this, this, obsList);
        obsRecyclerView.setAdapter(obsAdapter);

        obsRecyclerView.setLayoutManager(new LinearLayoutManager(ListObsActivity.this));

        deleteAllObsOfHikeBtn = findViewById(R.id.reset_obs_database);
        deleteAllObsOfHikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ListObsActivity.this);
                builder.setTitle("Delete all observations?");
                builder.setMessage("Are you sure to delete all observations");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteAllObsOfHike(hikeId);
                        obsList.clear();
                        obsAdapter.notifyDataSetChanged();
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
}