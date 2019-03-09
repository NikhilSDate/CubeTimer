package app.cubing.timer;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class LogFragment extends Fragment {
    TextView textView;
    RecyclerView sessionsList;
    ArrayList<String> sessionNames;

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
        sessionNames=Utils.getSessionNames();


        sessionsList=view.findViewById(R.id.log_sessions);
        sessionsList.setLayoutManager(new LinearLayoutManager(getContext()));
        final LogAdapter adapter=new LogAdapter(sessionNames);

        adapter.setClickListener(new LogAdapter.customClickListener() {
            @Override
            public void onItemClick(int position, View v) {

            }

            @Override
            public void onItemLongClick(int position, View v) {
                final String sessionName=adapter.getItemAtPosition(position);
                final MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(getContext());
                builder.setMessage("Do you really want to delete this session?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Sessions.getSingletonInstance().removeSession(sessionName);
                                Sessions.getSingletonInstance().writeToFile(getContext());
                                sessionNames=Utils.getSessionNames();
                                adapter.notifyDataSetChanged();
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

    }


}