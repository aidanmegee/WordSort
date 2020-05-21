package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;

public class MainActivity extends AppCompatActivity  {

    private StatusFragment statusFragment;
    private GameFragment gameFragment;
    private WordFragment wordFragment;
    private boolean isLargeScreen;
    private SensorManager sensorManager;
    private Difficulty level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Buttons
        Button settingsButton = findViewById(R.id.settings);
        Button highScoresButton = findViewById(R.id.high_scores);

        final TextView difficultyLevel = findViewById(R.id.difficulty_level);


        //** Find fragments using fragment manager **\\
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);

        isLargeScreen = statusFragment != null;

        //** onClick Listeners for buttons on Main Activity class **//
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

    public void openSettingsActivity() {
        Intent openSettingsIntent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(openSettingsIntent);
    }

    public void openHighScoresActivity() {
        Intent openHighScoresIntent = new Intent(MainActivity.this, HighScoresActivity.class);
        startActivity(openHighScoresIntent);
    }


}
