package au.edu.jcu.cp3406.WordSort;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "WordSort";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateDatabase(db, 0, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateDatabase(db, oldVersion, newVersion);
    }

    private void updateDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE WORDSORT (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "SCORE INTEGER);");
            insertData(db, "WordSort", "Champion", 200);
        }
    }

    public void insertData(SQLiteDatabase db, String TABLE, String name, int score) {
        if (db == null) {
            db = this.getWritableDatabase();
        }
        ContentValues scoreValues = new ContentValues();
        scoreValues.put("NAME", name);
        scoreValues.put("SCORE", score);
        db.insert(TABLE, null, scoreValues);
    }
}
