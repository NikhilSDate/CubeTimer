package app.cubing.timer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PuzzleAdapter extends ArrayAdapter<Integer> {

    public PuzzleAdapter(Context context, int textViewResourceId, Integer[] objects){
        super(context,textViewResourceId,objects);



    }
    public View getCustomView(int position, View convertView, ViewGroup parent){
        View customView=LayoutInflater.from(getContext()).inflate(R.layout.puzzle_spinner_layout,parent,false);
        ImageView puzzleIcon=customView.findViewById(R.id.puzzleImage);
        puzzleIcon.setImageResource(getItem(position));
        return customView;



    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position,convertView,parent);
    }
}
