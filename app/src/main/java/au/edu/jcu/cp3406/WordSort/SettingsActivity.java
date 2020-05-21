package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import au.edu.jcu.cp3406.WordSort.R;

public class SettingsActivity extends AppCompatActivity {

    boolean checked;
    boolean value = true; //default value if no value is found

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //final objects for settings activity to access
        final SharedPreferences sharedPreferences = getSharedPreferences("isChecked", 0);
        Button finishButton = findViewById(R.id.finished);
        final Switch soundSwitch = findViewById(R.id.sound_switch);
        final Switch sensorSwitch = findViewById(R.id.sensor_switch);

        value = sharedPreferences.getBoolean("isChecked", value); //retrieve value of key
        final SoundPool soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);

        //Methods for switch being checked using shared preferences to pass the checked values through activities
        soundSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    soundSwitch.setChecked(true);
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                    soundPool.setVolume(1, 0, 0); //mute volume if sound switch is checked
                } else {
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                    soundSwitch.setChecked(false);
                }
            }
        });

        sensorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sensorSwitch.setChecked(true);
                    sharedPreferences.edit().putBoolean("isChecked", true).apply();
                } else {
                    sharedPreferences.edit().putBoolean("isChecked", false).apply();
                    sensorSwitch.setChecked(false);
                }
            }
        });

        //Button listener for the user to return to the "Home Page"
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    private void SavePreferences(String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        Intent send = new Intent(this, WordActivity.class);
        startActivity(send);
    }

    public void openMainActivity() {
        Intent openMainActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(openMainActivityIntent);
    }
}
