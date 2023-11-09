package com.example.mobileasm.obs;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mobileasm.MyDatabaseHelper;
import com.example.mobileasm.NavigatorHandler;
import com.example.mobileasm.R;
import com.example.mobileasm.hikeActivity.AddActivity;
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
        getObservationDetail();
    }
    void getObservationDetail(){
        if (getIntent().hasExtra("obsId")){
            int obsId = getIntent().getIntExtra("obsId", -1);
            MyDatabaseHelper db = new MyDatabaseHelper(DetailObsActivity.this);
            Cursor result = db.getObservationDetail(obsId);
            if (result.getCount() == 0) {
                Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
            }else{
                if (result.moveToNext()){
                    String name = result.getString(1);
                    String date = result.getString(2);
                    String time = result.getString(3);
                    String sighting = result.getString(4);
                    String weather = result.getString(5);
                    byte[] imageData = result.getBlob(6);
                    String comment = result.getString(7);
                    obsName.setText(name);
                    obsDate.setText(date);
                    obsTime.setText(time);
                    obsSighting.setText(sighting);
                    obsWeather.setText(weather);
                    obsComment.setText(comment);
                    if (imageData != null) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        obsImage.setImageBitmap(bitmap);
                    } else {
                        // Set a default image if the avatar data is null
                        obsImage.setImageResource(R.drawable.ic_image);
                    }
                }
            }
        }
    }
}