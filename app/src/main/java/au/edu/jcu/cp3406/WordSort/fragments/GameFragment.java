package au.edu.jcu.cp3406.WordSort.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;


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

    StateListener listener;
    private Difficulty level;
    Button playButton;

    Random randomNum;

    public GameFragment() {
        // Required empty public constructor
    }

    public Difficulty getLevel() {
        return level;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_game, container, false);
        final Spinner spinner = view.findViewById(R.id.spinner);

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

    public void openWordActivity() {
        Intent openWordIntent = new Intent(getContext(), WordActivity.class);
        startActivity(openWordIntent);
    }

    //attach listener to this fragment
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        listener = (StateListener) context;
//    }


}
