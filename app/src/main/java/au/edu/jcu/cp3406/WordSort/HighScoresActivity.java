package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import au.edu.jcu.cp3406.WordSort.R;

public class HighScoresActivity extends AppCompatActivity {

    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);

        SQLiteOpenHelper wordSortHelper = new DatabaseHelper(this);
        ListView listNames = findViewById(R.id.name);
        ListView listScores = findViewById(R.id.scores);
        try {
            db = wordSortHelper.getReadableDatabase();

            cursor = db.query("WordSort",
                    new String[]{"_id", "NAME", "SCORE"},
                    null, null, null, null, "SCORE DESC");
            SimpleCursorAdapter nameAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            SimpleCursorAdapter scoresAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"SCORE"},
                    new int[]{android.R.id.text1},
                    0);
            listNames.setAdapter(nameAdapter);
            listScores.setAdapter(scoresAdapter);
        } catch (SQLiteException e) {
            System.out.println(e.toString());
            Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
