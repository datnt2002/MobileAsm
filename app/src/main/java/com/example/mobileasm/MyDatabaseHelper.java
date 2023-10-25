package com.example.mobileasm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MHike.db";
    public static final String TABLE_NAME = "HIKER_MANAGEMENT";
    public static final String COLUMN_HIKE_ID = "HIKE_ID";
    public static final String COLUMN_HIKE_NAME = "HIKE_NAME";
    public static final String COLUMN_HIKE_LOCATION = "HIKE_LOCATION";
    public static final String COLUMN_HIKE_DATE = "HIKE_DATE";
    public static final String COLUMN_AVAILABLE = "AVAILABLE";
    public static final String COLUMN_HIKE_LENGTH = "HIKE_LENGTH";
    public static final String COLUMN_HIKE_LEVEL = "HIKE_LEVEL";
    public static final String COLUMN_HIKE_ESTIMATE = "HIKE_ESTIMATE";
    public static final String COLUMN_HIKE_DESCRIPTION = "HIKE_DESCRIPTION";

    private Context context;

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME
                + " (" + COLUMN_HIKE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_HIKE_NAME + " TEXT, " + COLUMN_HIKE_LOCATION + " TEXT, "
                + COLUMN_HIKE_DATE + " TEXT, " + COLUMN_AVAILABLE + " INTEGER, "
                + COLUMN_HIKE_LENGTH + " INTEGER, " + COLUMN_HIKE_LEVEL + " INTEGER, "
                + COLUMN_HIKE_ESTIMATE + " INTEGER, " + COLUMN_HIKE_DESCRIPTION + " TEXT)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
