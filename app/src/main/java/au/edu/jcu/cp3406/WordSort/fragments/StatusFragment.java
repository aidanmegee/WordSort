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
    private TextView score;

    public StatusFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_status, container, false);

        score = view.findViewById(R.id.score);

        return view;
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        listener = (StateListener) context;
//    }

    public void setScore(String currentScore) {
        score.setText(currentScore);
    }
}
