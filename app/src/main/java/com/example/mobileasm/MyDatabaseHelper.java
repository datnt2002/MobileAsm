package com.example.mobileasm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

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


    public boolean addHike(HikeModel hikeModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_HIKE_NAME, hikeModel.getHikeName());
        cv.put(COLUMN_HIKE_LOCATION, hikeModel.getHikeLocation());
        cv.put(COLUMN_HIKE_DATE, hikeModel.getHikeDate());
        cv.put(COLUMN_AVAILABLE, hikeModel.isParkingAvailable());
        cv.put(COLUMN_HIKE_LENGTH, hikeModel.getHikeLength());
        cv.put(COLUMN_HIKE_LEVEL, hikeModel.getHikeLevel());
        cv.put(COLUMN_HIKE_ESTIMATE, hikeModel.getHikeEstimate());
        cv.put(COLUMN_HIKE_DESCRIPTION, hikeModel.getHikeDescription());

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Add Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public ArrayList<HikeModel> getAllHike(){
        ArrayList<HikeModel> returnArray = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String hikeName = cursor.getString(1);
                String hikeLocation = cursor.getString(2);
                String hikeDate = cursor.getString(3);
                boolean available = cursor.getInt(4) == 1;
                int hikeLength = cursor.getInt(5);
                int hikeLevel = cursor.getInt(6);
                int hikeEstimate = cursor.getInt(7);
                String hikeDescription = cursor.getString(8);

                HikeModel hikeModel = new HikeModel(id, hikeName, hikeLocation, hikeDate, available, hikeLength, hikeLevel, hikeEstimate, hikeDescription);
                returnArray.add(hikeModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnArray;
    }

    public Cursor getData(int position) {
        SQLiteDatabase db = this.getReadableDatabase();
        position = position + 1;
        Cursor cursor = db.query(TABLE_NAME, null,"HIKE_ID = ?", new String[]{String.valueOf(position)}, null, null, null);
        return cursor;
    }
}
