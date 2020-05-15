package au.edu.jcu.cp3406.WordSort.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Random;

import au.edu.jcu.cp3406.WordSort.R;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;
import au.edu.jcu.cp3406.WordSort.utilities.State;
import au.edu.jcu.cp3406.WordSort.utilities.StateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {
    private TextView info, word;
    private EditText wordGuess;
    private Button checkWord, newGame;
    private StateListener listener;
    private Difficulty level;

    Random randomNum;

    String currentWord;

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner);

        //find views to start game + set difficulty
        view.findViewById(R.id.play);
        String selection = spinner.getSelectedItem().toString();
        Log.i("GameFragment", "selection: " + selection);
        level = Difficulty.valueOf(selection.toUpperCase());
        listener.onUpdate(State.START_GAME);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (StateListener) context;
    }




}
