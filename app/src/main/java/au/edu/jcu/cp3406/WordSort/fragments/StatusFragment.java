package au.edu.jcu.cp3406.WordSort.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import au.edu.jcu.cp3406.WordSort.R;
import au.edu.jcu.cp3406.WordSort.utilities.StateListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {

    //private variables for status fragment
    private StateListener listener;
    private TextView time;
    private TextView guessesLeft;
    private TextView score;
    private TextView message;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        time = view.findViewById(R.id.time);
        guessesLeft = view.findViewById(R.id.incorrect_guesses);
        score = view.findViewById(R.id.score);

        return view;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        listener = (StateListener) context;
//    }

    public void setGuessesLeft(int guesses) {
        guessesLeft.setText(guesses);
    }

    public void setMessage(String text) {
        message.setText(text);
    }

    public void setTime(String timePassed) {
        time.setText(timePassed);
    }

    public void setScore(String currentScore) {
        score.setText(currentScore);
    }
}
