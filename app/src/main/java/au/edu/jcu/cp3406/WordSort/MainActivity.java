package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.jcu.cp3406.WordSort.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button playButton = findViewById(R.id.play);
        Button settingsButton = findViewById(R.id.settings);
        Button highScoresButton = findViewById(R.id.high_scores);

        //** onClick Listeners for buttons on Main Activity class **//
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGameActivity();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        highScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHighScoresActivity();
            }
        });
    }

    //** Methods for creating new intent to navigate to different activities **//
    public void openGameActivity() {
        Intent openGameIntent = new Intent(MainActivity.this, GameActivity.class);
        startActivity(openGameIntent);
    }

    public void openSettingsActivity() {
        Intent openSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(openSettingsIntent);
    }

    public void openHighScoresActivity() {
        Intent openHighScoresIntent = new Intent(MainActivity.this, HighScoresActivity.class);
        startActivity(openHighScoresIntent);
    }

}
