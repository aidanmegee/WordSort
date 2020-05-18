package au.edu.jcu.cp3406.WordSort.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import au.edu.jcu.cp3406.WordSort.R;
import au.edu.jcu.cp3406.WordSort.utilities.Game;
import au.edu.jcu.cp3406.WordSort.utilities.StateListener;
import au.edu.jcu.cp3406.WordSort.utilities.Words;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {

    private TextView info, word;
    private EditText wordGuess;
    private Button checkWord, newGame;
    private String currentWord;
    private StateListener listener;
    Game currentGame;

    public WordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_word, container, false);

        //Find TextViews
        info = view.findViewById(R.id.info);
        word = view.findViewById(R.id.current_word);

        //Find EditText
        wordGuess = view.findViewById(R.id.word_guess);

        //Find Buttons
        checkWord = view.findViewById(R.id.check_word);
        newGame = view.findViewById(R.id.new_game);

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
        return view;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        listener = (StateListener) context;
//    }

    //** Public methods for word elements **//
    public void setGame(Game currentGame) {
        this.currentGame = currentGame;
        displayUpdate();
    }

    public String getScore() {
        return currentGame.getScore();
    }

    public void showNextWord() {
        currentGame.next();
        displayUpdate();
    }

    //updates display along with new list of type words
    public void displayUpdate() {
        assert this.getContext() != null;
        List<Words> words = Collections.singletonList(currentGame.getNextWord());
        Collections.shuffle(words);
    }
}
