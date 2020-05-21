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
import android.media.MediaPlayer;
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
import java.util.Objects;
import java.util.Random;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;

public class WordActivity extends AppCompatActivity {

    private Chronometer timer;
    private Random randNum;
    private int n, currentScore;
    private int easyScore = 5;
    private int medScore = 10;
    private int hardScore = 15;
    boolean check;
    String[] easyWords;
    String[] mediumWords;
    String[] hardWords;
    String currentDifficulty;
    String[] currentWordArray;
    private TextView info, word, score;
    private EditText wordGuess;
    private Button checkWord, newGame, showWord;
    private String currentWord, nextWord;
    private SoundPool soundPool;
    private int correctSound, incorrectSound;
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
        showWord = findViewById(R.id.show_word);

        //find chronometer ID
        timer = findViewById(R.id.timer);

        //random object for index of currentWordArray
        final int randNum = new Random().nextInt(currentWordArray.length);

        showWord.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.start();
                //get random word from selected array
                currentWord = getCurrentWordArray()[randNum];
                word.setText(shuffleWord(getNextWord()));
                info.setText("Guess the Word!");


                //clear edit text field
                wordGuess.setText("");

                //switch buttons from when show word is clicked to when new game is clicked
                newGame.setEnabled(false);
                checkWord.setEnabled(true);

            }
        });

        checkWord.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                checkWord(currentWord);
                word.setText(shuffleWord(getNextWord()));
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

    public void chooseDifficulty(Difficulty difficulty) {
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

    public void setNextWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public String getNextWord() {
        return this.currentWord;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    public int getCurrentScore() {
        return this.currentScore;
    }

    public void updateScore(boolean check) {
        if (check) {
            setCurrentScore(currentScore += getCurrentScore());
        }
    }

    //Shuffling algorithm
    public String shuffleWord(String currentWord) {
        List<String> letters = Arrays.asList(currentWord.split("")); //creates a list of String characters and splits them with an empty string
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder(" ");//New String variable to hold letters
        for (String letter : letters) {
            shuffled.append(letter);  //appends letters to the new String variable shuffled
        }
        return shuffled.toString(); //returns the shuffled variable containing all letters of the word
    }

    @SuppressLint("SetTextI18n")
    public void checkWord(String currentWord) {
        if (wordGuess.getText().toString().equalsIgnoreCase(currentWord)) {
            soundPool.play(correctSound, 1, 1, 0, 0, 1);
            info.setText("Correct Guess!");
            updateScore(check);
            if (currentWordArray.length != 0) {
                showNextWord();
            }

            wordGuess.setText("");
            updateScore(true);
            score.setText(String.valueOf(currentScore += getCurrentScore()));
            showNextWord();

            if (currentWordArray.length == 0) {
                info.setText("All Words Have been solved");
                timer.stop();
                showWord.setEnabled(false);
                checkWord.setEnabled(false);
            }
            newGame.setEnabled(true);
            //iterate through current word array
        } else {
            info.setText("Incorrect Guess, Try Again :)");
            soundPool.play(incorrectSound, 1, 1, 0, 0, 1);
        }
    }

    @SuppressLint("SetTextI18n")
    public void showNextWord() {

        for (int i = 0; i < getCurrentWordArray().length; i++) {
            setNextWord(currentWordArray[i]);
        }
    }


    @SuppressLint("SetTextI18n")
    public void newGame() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        //get random word from selected array
//        currentWord = getCurrentWordArray()[randNum.nextInt(getCurrentWordArray().length)];
        info.setText("Guess the Word!");

        //clear edit text field
        wordGuess.setText("");

        //switch buttons from when show word is clicked to when new game is clicked
        newGame.setEnabled(false);
        checkWord.setEnabled(true);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        soundPool.release();
        soundPool = null;
    }

}
