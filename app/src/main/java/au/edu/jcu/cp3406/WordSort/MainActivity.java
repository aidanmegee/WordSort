package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;
import au.edu.jcu.cp3406.WordSort.utilities.Game;
import au.edu.jcu.cp3406.WordSort.utilities.GameBuilder;
import au.edu.jcu.cp3406.WordSort.utilities.State;

public class MainActivity extends AppCompatActivity {

    private StatusFragment statusFragment;
    private GameFragment gameFragment;
    private WordFragment wordFragment;
    private boolean isLargeScreen;
    private SensorManager sensorManager;
    private float accel;
    private float accelCurrent;
    private float accelLast;


    GameBuilder gameBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find Buttons
        Button playButton = findViewById(R.id.play);
        Button settingsButton = findViewById(R.id.settings);
        Button highScoresButton = findViewById(R.id.high_scores);

        final TextView difficultyLevel = findViewById(R.id.difficulty_level);

        String[] difficulties = getResources().getStringArray(R.array.difficulty);
        final Spinner spinner = findViewById(R.id.spinner);
        final List<String> difficultyList = new ArrayList<>(Arrays.asList(difficulties));

        //initialising an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, difficultyList) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position%2 == 1) {
                    tv.setBackgroundColor(Color.parseColor("#FFC9A3FF"));
                } else {
                    tv.setBackgroundColor(Color.parseColor("#FFAF89E5"));
                }
                return view;
            }
        };
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            //methods for onItemSelectedListener
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItemText = (String) parent.getItemAtPosition(position);
                difficultyLevel.setText("Current Difficulty Level: " + selectedItemText);
                Toast.makeText(getApplicationContext(), "Selected: " + selectedItemText, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        setUpFragmentManager();
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
        playButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openWordActivity();
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

    public void setUpFragmentManager() {

        //** Find fragments using fragment manager **\\
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
    }

    public void onUpdate(State state) {
        Difficulty level = gameFragment.getLevel();
        String text = String.format((Locale.getDefault()), "state: %s level: %s", state, level);
        Log.i("MainActivity", text);

        if (isLargeScreen) {
            switch (state) {
                case START_GAME:
                    Game game = gameBuilder.create(level);
                    wordFragment.setGame(game);
                    break;
                case CONTINUE_GAME:
                    statusFragment.setScore(wordFragment.getScore());
                    break;
                case GAME_OVER:
                    statusFragment.setScore(wordFragment.getScore());
                    statusFragment.setMessage("Game Over");
                    break;
            }
        }
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

    public void openWordActivity() {
        Intent openWordIntent = new Intent(MainActivity.this, WordActivity.class);
        startActivity(openWordIntent);
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
                Toast.makeText(getApplicationContext(), "Shaking Detected, Restarting Game", Toast.LENGTH_LONG).show();

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
