package au.edu.jcu.cp3406.WordSort;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    private TextView info, word;
    private EditText wordGuess;
    private Button checkWord, newGame;

    Random randomNum;

    String currentWord;

    String[] dictionary = { //Hardcode dictionary for test
            "hello",
            "car",
            "paris",
            "football",
            "tennis",
            "fruit",
            "night",
            "computer",
            "java",
            "network"

    };

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        //Find TextViews
        info = view.findViewById(R.id.info);
        word = view.findViewById(R.id.current_word);

        //Find EditText
        wordGuess = view.findViewById(R.id.word_guess);

        //Find Buttons
        checkWord = view.findViewById(R.id.check_word);
        newGame = view.findViewById(R.id.new_game);

        //random num variable for accessing random word in array
        randomNum = new Random();

        //new game called when fragment is created
        newGame();

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

        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame(); //new Game is called again once the user clicks "New Game" button
            }
        });

        return view;
    }

    //method to shuffle letters of the current word
    private String wordShuffle(String word) {
        List<String> letters = Arrays.asList(word.split("")); //Assigns a list of letters split from given array
        Collections.shuffle(letters);
        String shuffled = "";
        for (String letter : letters) { //iterate over all letters in the List of type <String>
            shuffled += letter;
        }
        return shuffled;
    }

    //method that is called when new game button is clicked by user
    private void newGame() {

        currentWord = dictionary[randomNum.nextInt(dictionary.length)];

        word.setText(wordShuffle(currentWord));

        //clears user guess to produce empty String
        wordGuess.setText("");

        //re-enable buttons based on previous game played
        newGame.setEnabled(false);
        checkWord.setEnabled(true);
    }

}
