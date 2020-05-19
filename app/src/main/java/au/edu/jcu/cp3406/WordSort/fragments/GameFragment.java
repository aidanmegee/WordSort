package au.edu.jcu.cp3406.WordSort.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.Random;
import au.edu.jcu.cp3406.WordSort.R;
import au.edu.jcu.cp3406.WordSort.WordActivity;
import au.edu.jcu.cp3406.WordSort.utilities.Difficulty;
import au.edu.jcu.cp3406.WordSort.utilities.State;
import au.edu.jcu.cp3406.WordSort.utilities.StateListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {

    private StateListener listener;
    private Difficulty level;
    private Button playButton;
    private TextView difficulty;

    Random randomNum;

    public GameFragment() {
        // Required empty public constructor
    }

    public Difficulty getLevel() {
        return level;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                difficulty.setText("Current Difficulty: " + spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Find ids through view
        difficulty = view.findViewById(R.id.difficulty_level);
        playButton = view.findViewById(R.id.play);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selection = spinner.getSelectedItem().toString();
                Log.i("GameFragment", "selection: " + selection);
                level = Difficulty.valueOf(selection.toUpperCase());
                openWordActivity();
                listener.onUpdate(State.START_GAME);
            }
        });
        return view;
    }

    //attach listener to this fragment
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (StateListener) context;
    }


    //helper method for opening the word Activity
    private void openWordActivity() {
        Intent openWordIntent = new Intent(getContext(), WordActivity.class);
        startActivity(openWordIntent);
    }

}
