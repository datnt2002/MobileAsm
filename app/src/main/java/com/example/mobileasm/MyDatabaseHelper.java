package com.example.mobileasm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.mobileasm.models.HikeModel;
import com.example.mobileasm.models.ObservationsModel;

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


    public static final String OBS_TABLE_NAME = "HIKE_OBSERVATION";
    public static final String COLUMN_OBS_ID = "OBS_ID";
    public static final String COLUMN_OBS_NAME = "OBS_NAME";
    public static final String COLUMN_OBS_DATE = "OBS_DATE";
    public static final String COLUMN_OBS_TIME = "OBS_TIME";
    public static final String COLUMN_OBS_SIGHTING = "OBS_SIGHTING";
    public static final String COLUMN_OBS_WEATHER = "OBS_WEATHER";
    public static final String COLUMN_OBS_COMMENT = "OBS_COMMENT";

    public static final String COLUMN_OBS_IMAGE = "OBS_IMAGE";
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

        String createTableObservation = "CREATE TABLE " + OBS_TABLE_NAME
                + " (" + COLUMN_OBS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_OBS_NAME + " TEXT, " + COLUMN_OBS_DATE + " TEXT, "
                + COLUMN_OBS_TIME + " TEXT, " + COLUMN_OBS_SIGHTING + " TEXT, " +
                COLUMN_OBS_WEATHER + " TEXT, " + COLUMN_OBS_IMAGE + " BLOB, "
                + COLUMN_OBS_COMMENT + " TEXT, " + COLUMN_HIKE_ID + " INTEGER, " +
                "FOREIGN KEY (" + COLUMN_HIKE_ID + ") REFERENCES " + TABLE_NAME + "(" + COLUMN_HIKE_ID + "))";;

        db.execSQL(createTableObservation);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + OBS_TABLE_NAME);
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

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null,"HIKE_ID = ?", new String[]{String.valueOf(id)}, null, null, null);
        return cursor;
    }

    public void updateData(int id, HikeModel hikeModel){
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

        long result = db.update(TABLE_NAME, cv, "HIKE_ID = ?", new String[]{String.valueOf(id)});

        if (result == -1){
            Toast.makeText(context, "Update Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "HIKE_ID=?", new String[]{String.valueOf(id)});
        if (result == -1){
            Toast.makeText(context, "Delete Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Delete Successfully", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void deleteAllHikes(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }

    //observation
    public boolean addObservation(ObservationsModel obs, int hikeId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_OBS_NAME, obs.getObsName());
        cv.put(COLUMN_OBS_DATE, obs.getObsDate());
        cv.put(COLUMN_OBS_TIME, obs.getObsTime());
        cv.put(COLUMN_OBS_SIGHTING, obs.getObsSighting());
        cv.put(COLUMN_OBS_WEATHER, obs.getObsWeather());
        cv.put(COLUMN_OBS_IMAGE, obs.getObsImage());
        cv.put(COLUMN_OBS_COMMENT, obs.getObsComment());
        cv.put(COLUMN_HIKE_ID, hikeId);

        long result = db.insert(OBS_TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Add Obs Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "Add Obs Successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public ArrayList<ObservationsModel> getAllObsOfHike(int hikeId){
        ArrayList<ObservationsModel> returnArray = new ArrayList<>();
        String query = "SELECT * FROM " + OBS_TABLE_NAME + " WHERE " + COLUMN_HIKE_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(hikeId)});

        if (cursor.moveToFirst()){
            do {
                int obsId = cursor.getInt(0);
                String obsName = cursor.getString(1);
                String obsDate = cursor.getString(2);
                byte[] image = cursor.getBlob(6);

                ObservationsModel obs = new ObservationsModel(obsId, obsName, obsDate, image);
                returnArray.add(obs);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnArray;
    }
}
