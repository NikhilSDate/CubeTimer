package app.cubing.timer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SessionDetailsFragment extends Fragment {
    TextInputEditText sessionName ;
    RecyclerView solves;
    Session currentSession;

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
    }
}
