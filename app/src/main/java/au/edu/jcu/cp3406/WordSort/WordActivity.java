package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import java.util.Random;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;

public class WordActivity extends AppCompatActivity {

    private Chronometer timer;
    private Random randNum;
    private int n, currentScore;
    String[] easyWords;
    String[] mediumWords;
    String[] hardWords;
    String currentDifficulty;
    String[] currentWordArray;
    private TextView info, word, score;
    private EditText wordGuess;
    private Button checkWord, newGame, showWord;
    private String currentWord;
    StatusFragment statusFragment;
    WordFragment wordFragment;
    GameFragment gameFragment;


    @SuppressLint({"ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        //Set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);

        //retrieve difficulty level selected from Main Activity
        Intent intent = getIntent();
        currentDifficulty = intent.getStringExtra("level");

        //Find array resource values of different word arrays
        easyWords = getResources().getStringArray(R.array.easy_words);
        mediumWords = getResources().getStringArray(R.array.medium_words);
        hardWords = getResources().getStringArray(R.array.hard_words);

        //determines difficulty and assigns word array based on the difficulty
        final Difficulty[] difficulties = Difficulty.values();
        for (Difficulty currentDifficulty : difficulties) {
            //enum switch case for difficulties
            switch (currentDifficulty) {
                case EASY:
                    setCurrentWordArray(easyWords);
                    break;
                case MEDIUM:
                    setCurrentWordArray(mediumWords);
                    break;
                case HARD:
                    setCurrentWordArray(hardWords);
                    break;
            }
        }

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
        randNum = new Random();

        showWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when use clicks show word, timer starts and newGame is called
                newGame();

            }
        });

        checkWord.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (wordGuess.getText().toString().equalsIgnoreCase(currentWord)) {
                    Toast.makeText(getApplicationContext(), "Correct Guess!", Toast.LENGTH_LONG).show();
                    wordGuess.setText("");
                    setNextWord(shuffleWord(currentWordArray[randNum.nextInt(getCurrentWordArray().length)]));

                    if (currentWord.equals(currentWordArray[9])) {
                        info.setText("All Words Have been solved");
                        showWord.setEnabled(false);
                        checkWord.setEnabled(false);
                    }
                    word.setText(currentWord);
                    newGame.setEnabled(true);
                    //iterate through current word array
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Guess", Toast.LENGTH_LONG).show();
                }
            }
        });

        //new game called when fragment is created
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setBase(SystemClock.elapsedRealtime());
                timer.stop();
            }
        });

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

    //fix or delete
//    public String[] getNextWord() {
//        return this.currentWordArray[n + 1];
//    }

    //Shuffling algorithm
    public String shuffleWord(String currentWord) {
        List<String> letters = Arrays.asList(currentWord.split("")); //creates a list of String characters and splits them with an empty string
        Collections.shuffle(letters);
        String shuffled = " ";//New String variable to hold letters
        for (String letter : letters) {
            shuffled += letter;  //appends letters to the new String variable shuffled
        }
        return shuffled; //returns the shuffled variable containing all letters of the word
    }


    public void newGame() {
        timer.setBase(SystemClock.elapsedRealtime());
        timer.start();
        //get random word from selected array
        currentWord = getCurrentWordArray()[randNum.nextInt(getCurrentWordArray().length)];

        //shows shuffled word
        word.setText(shuffleWord(currentWord));

        //clear edit text field
        wordGuess.setText("");

        //switch buttons from when show word is clicked to when new game is clicked
        newGame.setEnabled(false);
        checkWord.setEnabled(true);
    }

    public void openMainActivityIntent() {
        Intent mainActivityIntent = new Intent(WordActivity.this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

}
