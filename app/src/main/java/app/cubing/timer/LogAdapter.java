package app.cubing.timer;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.MyViewHolder> {
    ArrayList<String> sessionsList;
    private static customClickListener listener;


    public interface customClickListener{
        void onItemClick(int position,View v);
        void onItemLongClick(int position,View v);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        TextView sessionName;
        ImageView sessionType;
        TextView count;
        TextView ao5;
        TextView ao12;
        TextView ao50;
        TextView ao100;
        TextView mean;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.sessionName = itemView.findViewById(R.id.session_name);
            this.sessionType = itemView.findViewById(R.id.session_type_image);
            this.count = itemView.findViewById(R.id.count);
            this.ao5 = itemView.findViewById(R.id.ao5);
            this.ao12 = itemView.findViewById(R.id.ao12);
            this.ao50 = itemView.findViewById(R.id.ao50);
            this.ao100 = itemView.findViewById(R.id.ao100);
            this.mean = itemView.findViewById(R.id.mean);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition(),v);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition(),v);
            return true;
        }

    }
    public LogAdapter(ArrayList<String> sessionsList) {
        this.sessionsList = sessionsList;
    }

    @NonNull
    @Override
    public LogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_log_layout,parent,false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Session currentSession=Sessions.getSingletonInstance().getSessionsMap().get(sessionsList.get(position));

        holder.sessionName.setText(currentSession.getName());
        holder.count.setText("Size: "+String.valueOf(currentSession.getCount()));
        holder.ao5.setText("Ao5: "+Utils.formatTime((double)currentSession.getAo5()*1000,false));
        Log.i("TAG",currentSession.getName()+"-> "+Utils.formatTime(currentSession.getAo5()*1000,false));
        holder.ao12.setText("Ao12: "+Utils.formatTime((double)currentSession.getAo12()*1000,false));
        holder.ao50.setText("Ao50: "+Utils.formatTime((double)currentSession.getAo50()*1000,false));
        holder.ao100.setText("Ao100: "+Utils.formatTime((double)currentSession.getAo100()*1000,false));
        holder.mean.setText("Mean: "+Utils.formatTime((double)currentSession.getMean()*1000,false));



    }

    @Override
    public int getItemCount() {
        return sessionsList.size();
    }
    public void setClickListener(customClickListener clickListener){
       LogAdapter.listener=clickListener;
    }
}
