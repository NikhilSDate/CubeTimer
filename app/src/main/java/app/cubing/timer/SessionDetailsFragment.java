package app.cubing.timer;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SessionDetailsFragment extends Fragment {
    TextInputEditText sessionName ;
    TextView count,mean,ao5,ao12,ao50,ao100;
    RecyclerView solves;
    Session currentSession;
    FloatingActionButton addSolve,delete;

    public SessionDetailsFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_session_details,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initialize(view);
    }
    public void initialize(View view){
        currentSession=Sessions.getSingletonInstance().getSessionsMap().get(getArguments().getString("session"));
        solves=view.findViewById(R.id.sessionSolves);
        SolveAdapter adapter=new SolveAdapter(new ArrayList<Integer>(currentSession.getSolvesMap().keySet()),currentSession.getName(),getContext());
        solves.setLayoutManager(new LinearLayoutManager(getContext()));
        solves.setAdapter(adapter);

        count=view.findViewById(R.id.frag_count);
        mean=view.findViewById(R.id.frag_mean);
        ao5=view.findViewById(R.id.frag_ao5);
        ao12=view.findViewById(R.id.frag_ao12);
        ao50=view.findViewById(R.id.frag_ao50);
        ao100=view.findViewById(R.id.frag_ao100);
        sessionName=view.findViewById(R.id.frag_session_name);
        addSolve=view.findViewById(R.id.add_solve);
        delete=view.findViewById(R.id.delete_session);

        sessionName.setText(currentSession.getName());
        count.setText("Size: "+String.valueOf(currentSession.getCount()));
        ao5.setText("Ao5: "+Utils.formatTime((double)currentSession.getAo5()*1000,false));
        ao12.setText("Ao12: "+Utils.formatTime((double)currentSession.getAo12()*1000,false));
        ao50.setText("Ao50: "+Utils.formatTime((double)currentSession.getAo50()*1000,false));
        ao100.setText("Ao100: "+Utils.formatTime((double)currentSession.getAo100()*1000,false));
        mean.setText("Mean: "+Utils.formatTime((double)currentSession.getMean()*1000,false));

        sessionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String originalName=currentSession.getName();
                currentSession.setName(s.toString());
                Sessions.getSingletonInstance().changeSessionName(originalName,s.toString());
                Sessions.getSingletonInstance().writeToFile(getContext());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addSolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddSolveDialog();
            }
        });


    }
    public Solve showAddSolveDialog(){
        final Solve solve=new Solve();
        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.solve_builder_dialog);
        dialog.setTitle("Add a solve");
        TextInputEditText solveTime=dialog.findViewById(R.id.solve_time);

        Spinner solvePenalty=dialog.findViewById(R.id.solve_select_penalty);
        ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,new String[]{"None","+2","DNF"});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        solvePenalty.setAdapter(adapter);
        solvePenalty.setSelection(0);
        MaterialButton doneButton=dialog.findViewById(R.id.solve_done_button);
        solveTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               solve.setTime(Integer.parseInt(s.toString()));

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        solvePenalty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item =(String)parent.getItemAtPosition(position);
                switch(item){
                    case "None":

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
