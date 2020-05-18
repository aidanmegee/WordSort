package au.edu.jcu.cp3406.WordSort;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.widget.Chronometer;

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
    StatusFragment statusFragment;
    WordFragment wordFragment;
    GameFragment gameFragment;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        setUpFragments();

        //Find array resource values of different word arrays
        easyWords = getResources().getStringArray(R.array.easy_words);
        mediumWords = getResources().getStringArray(R.array.medium_words);
        hardWords = getResources().getStringArray(R.array.hard_words);

        //find chronometer ID
        timer = findViewById(R.id.timer);

    }

    //Helper method for settings up fragments
    public void setUpFragments() {
        statusFragment = (StatusFragment) fragmentManager.findFragmentById(R.id.status);
        wordFragment = (WordFragment) fragmentManager.findFragmentById(R.id.wordFragment);
        gameFragment = (GameFragment) fragmentManager.findFragmentById(R.id.game);
    }

    //onUpdate method to determine what state the current game is in
    public void onUpdate(State state) {
        switch (state) {
            case CONTINUE_GAME:
                statusFragment.setScore(wordFragment.getScore());
                statusFragment.setGuessesLeft(5);
                timer.start();
                break;

            case GAME_OVER:
                statusFragment.setScore(wordFragment.getScore());
                statusFragment.setMessage("Game Over");
                statusFragment.setGuessesLeft(0);
                timer.stop();
                break;
        }
    }
}