package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import au.edu.jcu.cp3406.WordSort.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button finishButton = findViewById(R.id.finished);

        //Button listener for the user to return to the "Home Page"
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openMainActivity() {
        Intent openMainActivityIntent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(openMainActivityIntent);
    }
}
