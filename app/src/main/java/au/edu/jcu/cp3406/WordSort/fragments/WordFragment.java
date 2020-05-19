package au.edu.jcu.cp3406.WordSort.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import au.edu.jcu.cp3406.WordSort.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WordFragment extends Fragment {

    TextView message, score;
    int currentScore;


    public WordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_word, container, false);


        //find views in fragment
        message = view.findViewById(R.id.info);
        score = view.findViewById(R.id.score);
        return view;
    }


    //** Public methods for word elements **//
    public void setMessage(String text) {
        message.setText(text);
    }

    public int getScore() {
        return this.currentScore;
    }


}
