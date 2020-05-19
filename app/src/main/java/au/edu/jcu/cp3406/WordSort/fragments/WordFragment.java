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

    private StateListener listener;
    TextView message, word;
    Game currentGame;

    public WordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_word, container, false);
        message = view.findViewById(R.id.info);
        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        listener = (StateListener) context;
//    }

    //** Public methods for word elements **//
    public void setGame(Game currentGame) {
        this.currentGame = currentGame;
        displayUpdate();
    }

    public void setMessage(String text) {
        message.setText(text);
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
