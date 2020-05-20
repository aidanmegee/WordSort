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
    private float accel;
    private float accelCurrent;
    private float accelLast;
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

        //setting up sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            //there exists an Accelerometer
            Toast.makeText(getApplicationContext(), "Accelerometer Detected", Toast.LENGTH_LONG).show();
        } else {
            //No Accelerometer
            Toast.makeText(getApplicationContext(), "No Accelerometer Detected :(", Toast.LENGTH_LONG).show();
        }
        Objects.requireNonNull(sensorManager).registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        accel = 10f;
        accelCurrent = SensorManager.GRAVITY_EARTH;
        accelLast = SensorManager.GRAVITY_EARTH;

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


    //New sensor event listener to listen for shake events
    private final SensorEventListener sensorEventListener = new SensorEventListener() {

        //detects sensor change events based on x, y, z coordinates using Math library
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            accelLast = accelCurrent;
            accelCurrent = (float) Math.sqrt(x * x + y * y + z * z);
            float delta = accelCurrent - accelLast;
            accel = accel * 0.9f + delta;
            if (accel > 12) {
                // new game method called for device movement (shaking)
                Toast.makeText(getApplicationContext(), "Shaking Detected, Restarting Game", Toast.LENGTH_LONG).show();
                WordActivity wordActivity = new WordActivity();
                wordActivity.newGame();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            //no records on change in accuracy
        }
    };

    /* Lifecycle methods to register and unregister sensor event listeners */
    @Override
    protected void onResume() {
        sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(sensorEventListener);
        super.onPause();
    }

}
