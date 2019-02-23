package app.cubing.timer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import puzzle.ClockPuzzle;
import puzzle.CubePuzzle;
import puzzle.ThreeByThreeCubePuzzle;
import puzzle.TwoByTwoCubePuzzle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.gnehzr.tnoodle.scrambles.Puzzle;

import java.util.Random;

public class SessionFragment extends Fragment {
    TextView textView;

    public SessionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_session, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStart() {
        super.onStart();


    }
}