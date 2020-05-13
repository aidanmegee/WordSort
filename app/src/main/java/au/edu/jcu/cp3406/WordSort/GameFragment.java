package au.edu.jcu.cp3406.WordSort;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView info, currentWord;
        EditText wordGuess;
        Button checkWord, newGame;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_game, container, false);


        return view;
    }
}
