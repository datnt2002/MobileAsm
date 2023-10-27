package com.example.mobileasm;

import android.app.Activity;
import android.content.Intent;

import com.example.mobileasm.hikeActivity.SearchActivity;

public class NavigatorHandler {
    public void navigateTo(int itemId, Activity currentActivity) {
        Intent intent;
        if (itemId == R.id.home) {
            intent = new Intent(currentActivity, MainActivity.class);
            currentActivity.startActivity(intent);
        } else if (itemId == R.id.search) {
            intent = new Intent(currentActivity, SearchActivity.class);
            currentActivity.startActivity(intent);
        }
        // Add other conditions as needed for other menu items


    }


}
