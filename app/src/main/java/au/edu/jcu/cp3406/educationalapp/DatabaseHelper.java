package au.edu.jcu.cp3406.educationalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "hi-scores_table";
    private static final String COL1 = "ID";
    private static final String COL2 =  "Name";
    private static final String COL3 = "Score";
    private static final String COL4 = "Time";
    private static final String COL5 = "Difficulty";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creates table + columns for high scores database
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " +
            COL3 + " INTEGER, " + COL4 + " DECIMAL, " + COL5 + " VARCHAR)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //SQL code to drop table if it exists
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //method for adding data using values for each column of the database
    public boolean addData(String name, int score, float time, String difficulty) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        contentValues.put(COL3, score);
        contentValues.put(COL4, time);
        contentValues.put(COL5, difficulty);

        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + score + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + time + " to " + TABLE_NAME);
        Log.d(TAG, "addData: Adding " + difficulty + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
}
