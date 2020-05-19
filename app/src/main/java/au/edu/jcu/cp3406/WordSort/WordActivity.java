package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;

public class WordActivity extends AppCompatActivity {

    private Chronometer timer;
    private boolean running;
    String[] easyWords;
    String[] mediumWords;
    String[] hardWords;
    String currentWordArray;
    private TextView info, word;
    private EditText wordGuess;
    private Button checkWord, newGame, showWord;
    private String currentWord;
    StatusFragment statusFragment;
    WordFragment wordFragment;
    GameFragment gameFragment;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        //Set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);

        //Retrieve Shared preferences to get information from gameFragment in Main Activity
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        final String difficultyLevel = sharedPreferences.getString(getString(R.id.difficulty_level), "");

        //Find array resource values of different word arrays
        easyWords = getResources().getStringArray(R.array.easy_words);
        mediumWords = getResources().getStringArray(R.array.medium_words);
        hardWords = getResources().getStringArray(R.array.hard_words);


        //Find TextViews
        info = findViewById(R.id.info);
        word = findViewById(R.id.current_word);

        //Find EditText
        wordGuess = findViewById(R.id.word_guess);

        //Find Buttons
        checkWord = findViewById(R.id.check_word);
        newGame = findViewById(R.id.new_game);
        showWord = findViewById(R.id.show_word);

        showWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code to get random word from array (Easy, Medium or Hard
                shuffleWord(currentWord);
                word.setText(currentWord);
            }
        });

        checkWord.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (wordGuess.getText().toString().equalsIgnoreCase(currentWord)) {
                    info.setText("Correct Guess!");
                    checkWord.setEnabled(false);
                    newGame.setEnabled(true);
                }
            }
        });

        //new game called when fragment is created
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
//                timer.stop();
            }
        });

        //find chronometer ID
        timer = findViewById(R.id.timer);

    }

    public void setCurrentWordArray(String currentWordArray) {
        this.currentWordArray = currentWordArray;
    }

    //Shuffling algorithm
    public String shuffleWord(String currentWord) {
        List<String> letters = Arrays.asList(currentWord.split("")); //creates a list of String characters and splits them with an empty string
        Collections.shuffle(letters);
        StringBuilder shuffled = new StringBuilder(); //String Builder Object to hold the new word after shuffle
        for (String letter : letters) {
            shuffled.append(letter); //add letter to the new String builder object
        }
        return shuffled.toString(); //converts String builder object back to string value
    }


    public void newGame() {
        openMainActivityIntent();
    }

    public void openMainActivityIntent() {
        Intent mainActivityIntent = new Intent(WordActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

}
