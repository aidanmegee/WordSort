package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;

import au.edu.jcu.cp3406.WordSort.fragments.GameFragment;
import au.edu.jcu.cp3406.WordSort.fragments.StatusFragment;
import au.edu.jcu.cp3406.WordSort.fragments.WordFragment;
import au.edu.jcu.cp3406.WordSort.utilities.State;

public class WordActivity extends AppCompatActivity {

    private Chronometer timer;
    private boolean running;
    String[] easyWords;
    String[] mediumWords;
    String[] hardWords;
    private TextView info, word;
    private EditText wordGuess;
    private Button checkWord, newGame;
    private String currentWord;
    StatusFragment statusFragment;
    WordFragment wordFragment;
    GameFragment gameFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        //set up fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);

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
        newGame = findViewById(R.id.new_game); //add method for new Game restart

        //new game called when fragment is created

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

        //find chronometer ID
        timer = findViewById(R.id.timer);

    }


    //onUpdate method to determine what state the current game is in
    public void onUpdate(State state) {
        switch (state) {
            case CONTINUE_GAME:
                statusFragment.setScore(wordFragment.getScore());
                timer.start();
                break;

            case GAME_OVER:
                statusFragment.setScore(wordFragment.getScore());
                wordFragment.setMessage("Game Over!");
                timer.stop();
                break;
        }
    }

    public void newGame()

}
