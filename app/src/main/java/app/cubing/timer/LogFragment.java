package app.cubing.timer;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class LogFragment extends Fragment {
    Spinner puzzleType;
    RecyclerView sessionsList;
    ArrayList<String> sessionNames;
    int currentPuzzleType;

    public LogFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_log, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initialize(view);


    }
    public void initialize(View view){
        if(getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null)!=null){
            Log.i("TAG","preferences->"+getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null));
            Session currentSession=Sessions.getSingletonInstance().getSessionsMap().get(
                    getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null));
            currentPuzzleType=currentSession.getType();
        }else{
            currentPuzzleType=Session.TYPE_3X3;
        }
        sessionNames=Utils.getFilteredSessions(currentPuzzleType);
        sessionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        final LogAdapter adapter=new LogAdapter(sessionNames);

        adapter.setClickListener(new LogAdapter.customClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Log.i("TAG","CLICKED");
            }

            @Override
            public void onItemLongClick(final int position, View v) {
                final String sessionName=adapter.getItemAtPosition(position);
                final MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(getContext());
                builder.setMessage("Do you really want to delete this session?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                adapter.removeItem(position);
                                adapter.notifyItemRemoved(position);
                                Sessions.getSingletonInstance().removeSession(sessionName);
                                Sessions.getSingletonInstance().writeToFile(getContext());
                                sessionNames=Utils.getSessionNames();
                                if(getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null).equals(sessionName)){
                                    SharedPreferences preferences=getActivity().getPreferences(Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor=preferences.edit();
                                    editor.clear();
                                    editor.apply();
                                }

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        sessionsList.setAdapter(adapter);


        sessionsList=view.findViewById(R.id.log_sessions);
        puzzleType=view.findViewById(R.id.log_puzzletype);
        PuzzleAdapter spinnerAdapter=new PuzzleAdapter(getContext(),R.layout.puzzle_spinner_layout,Utils.getPuzzleDrawableIds());
        puzzleType.setAdapter(spinnerAdapter);
        puzzleType.setSelection(Utils.getIndexFromPuzzleType(spinnerAdapter,currentPuzzleType));

        puzzleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentPuzzleType=Utils.getPuzzleTypeFromInt((int)parent.getItemAtPosition(position));
                sessionNames=Utils.getFilteredSessions(currentPuzzleType);
                LogAdapter filteredSpinnerAdapter=new LogAdapter(sessionNames);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }


}