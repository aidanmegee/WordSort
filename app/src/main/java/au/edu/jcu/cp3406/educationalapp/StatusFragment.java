package au.edu.jcu.cp3406.educationalapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusFragment extends Fragment {
    private TextView time;
    private TextView guessesLeft;

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

        return view;
    }

    public void setGuessesLeft(String guesses) {
        guessesLeft.setText(guesses);
    }

    public void setTime(String timePassed) {
        time.setText(timePassed);
    }
}
