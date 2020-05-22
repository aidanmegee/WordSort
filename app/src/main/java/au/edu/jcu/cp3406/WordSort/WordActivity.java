package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class WordActivity extends AppCompatActivity {

    private Chronometer timer;
    private Random randNum;
    private int currentScore;
    String[] easyWords;
    String[] mediumWords;
    String[] hardWords;
    String currentDifficulty;
    String[] currentWordArray;
    private TextView info, word, score;
    private EditText wordGuess;
    private Button checkWord, newGame, start;
    private String currentWord;
    private SoundPool soundPool;
    private int correctSound, incorrectSound;
    private int i = 0;
    StatusFragment statusFragment;
    WordFragment wordFragment;
    GameFragment gameFragment;
    private SensorManager sensorManager;
    private float accel;
    private float accelCurrent;
    private float accelLast;


    @SuppressLint({"ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        //setting up sensor manager
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
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

        //Set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);

        //Set up soundPool and sounds for audio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            //new sound pool constructor to set max streams and audio attributes above
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .setAudioAttributes(audioAttributes)
                    .build();

        } else {
            //use old constructor for builds before version LOLLIPOP
            soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }

        correctSound = soundPool.load(this, R.raw.correct, 1);
        incorrectSound = soundPool.load(this, R.raw.incorrect, 1);

        //retrieve difficulty level selected from Main Activity
        Intent intent = getIntent();
        currentDifficulty = intent.getStringExtra("level");

        //Find array resource values of different word arrays
        easyWords = getResources().getStringArray(R.array.easy_words);
        mediumWords = getResources().getStringArray(R.array.medium_words);
        hardWords = getResources().getStringArray(R.array.hard_words);

        //determines difficulty and assigns word array based on the difficulty
        chooseDifficulty(Difficulty.valueOf(currentDifficulty.toUpperCase()));

        //Find TextViews
        info = findViewById(R.id.info);
        word = findViewById(R.id.current_word);
        score = findViewById(R.id.score);

        //Find EditText
        wordGuess = findViewById(R.id.word_guess);

        //Find Buttons
        checkWord = findViewById(R.id.check_word);
        newGame = findViewById(R.id.new_game);
        start = findViewById(R.id.start);

        //find chronometer ID
        timer = findViewById(R.id.timer);

        //random object for index of currentWordArray
        randNum = new Random();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                showNextWord();
                start.setEnabled(false);
            }
        });

        checkWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkWord();
            }
        });

        //new game called when fragment is created
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();

            }
        });

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
                newGame();
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

    //choose difficulty method to determine which word array and which score increment
    public void chooseDifficulty(Difficulty difficulty) {
        int hardScore = 5;
        int medScore = 3;
        int easyScore = 1;
        switch (difficulty) {
            case EASY:
                setCurrentWordArray(easyWords);
                setCurrentScore(easyScore);
                break;
            case MEDIUM:
                setCurrentWordArray(mediumWords);
                setCurrentScore(medScore);
                break;
            case HARD:
                setCurrentWordArray(hardWords);
                setCurrentScore(hardScore);
                break;
        }
    }

    //setter and getter methods for the current word array based on difficulty selected
    public void setCurrentWordArray(String[] currentWordArray) {
        this.currentWordArray = currentWordArray;
    }

    public String[] getCurrentWordArray() {
        return this.currentWordArray;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    //update score method that sets the current score each time check word is clicked by the user
    public void updateScore(boolean check) {
        if (check) {
            setCurrentScore(currentScore += getCurrentScore());
        }
    }

    //method to display next word
    public void showNextWord() {
        currentWordArray = getCurrentWordArray();
        currentWord = currentWordArray[i];
        word.setText(shuffleWord(currentWord));
        i++;
        //If statement to determine if the user has sorted all words
        if (i == currentWordArray.length - 1) {
            timer.stop();
            info.setText(String.format("Congratulations, you scored %s on Difficulty: %s ", currentScore, currentDifficulty));
//            new TwitterDemo().updateTweet(String.format(Locale.getDefault(),"Congratulations, you scored %s on Difficulty: %s ", currentScore, currentDifficulty));
            word.setText("");
            score.setText("");
            checkWord.setEnabled(false);
            start.setEnabled(false);
            newGame.setEnabled(true);
        }
    }

//    public void showNextWord() {
//        currentWordArray = getCurrentWordArray();
//        for (int i = 0; i < currentWordArray.length; i++) {
//            wordGuess.setText("");
//            currentWord = currentWordArray[i];
//            word.setText(shuffleWord(currentWord));
//
//            }
//        }


    //method to determine if the word is correct
    public void checkWord() {
        if (wordGuess.getText().toString().equalsIgnoreCase(currentWord)) {
            soundPool.play(correctSound, 1, 1, 0, 0, 1);
            info.setText(R.string.correct);
            showNextWord();
            newGame.setEnabled(true);
            updateScore(true);
            score.setText(String.valueOf(currentScore));
            wordGuess.setText("");
        } else {
            soundPool.play(incorrectSound, 1, 1, 0, 0, 1);
            info.setText(R.string.incorrect_word);
        }
    }

    //Shuffling algorithm
    public String shuffleWord(String currentWord) {
        List<String> letters = Arrays.asList(currentWord.split("")); //creates a list of String characters and splits them with an empty string
        Collections.shuffle(letters);
        String shuffled = " "; //New String variable to hold letters
        for (String letter : letters) {
            shuffled += letter;  //appends letters to the new String variable shuffled
        }
        return shuffled; //returns the shuffled variable containing all letters of the word
    }


    @SuppressLint("SetTextI18n")
    public void newGame() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.stop();
        //get random word from selected array
//        currentWord = getCurrentWordArray()[randNum.nextInt(getCurrentWordArray().length)];
        info.setText("Guess the Word!");
        word.setText("");
        score.setText("Score: 0");

        //clear edit text field
        wordGuess.setText("");

        //switch buttons from when show word is clicked to when new game is clicked
        newGame.setEnabled(false);
        start.setEnabled(true);
        checkWord.setEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
