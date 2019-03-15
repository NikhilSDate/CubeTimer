package app.cubing.timer;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SolveAdapter extends RecyclerView.Adapter<SolveAdapter.CustomViewHolder> {
    ArrayList<Integer> solveCodes;
    String sessionName;
    Context context;
    public SolveAdapter(ArrayList<Integer> solveCodes,String sessionName,Context context){
        this.solveCodes=solveCodes;
        this.sessionName=sessionName;
        this.context=context;
    }
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(
                R.layout.session_solve_layout,parent,false);
        return new CustomViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {
        final Solve solve=Sessions.getSingletonInstance().getSessionsMap().
                get(sessionName).getSolve(solveCodes.get(position));
        holder.time.setText(String.valueOf(Utils.formatTime((double)solve.getTime(),false)));
        holder.scramble.setText(String.valueOf(solve.getScramble()));
        holder.expand.setImageResource(R.drawable.ic_drop_down);
        holder.scramble.setVisibility(View.GONE);
        holder.plusTwo.setVisibility(View.GONE);
        holder.dnf.setVisibility(View.GONE);
        holder.delete.setVisibility(View.GONE);

        holder.plusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (!holder.isPlusTwo) {
                        Log.i("TAG", "PlusTwo clicked");
                        holder.isPlusTwo = true;
                        holder.isDNF = false;
                        Utils.modifySolvePenalty(context,
                                Sessions.getSingletonInstance().getSessionsMap().get(sessionName),
                                solveCodes.get(position), Utils.getPenalty(holder.isPlusTwo, holder.isDNF));
                        holder.plusTwo.setTextColor(Color.BLACK);
                        holder.plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        holder.dnf.setTextColor(Color.WHITE);
                        holder.dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    } else {
                        holder.isPlusTwo = false;
                        holder.isDNF = false;
                        Utils.modifySolvePenalty(context,
                                Sessions.getSingletonInstance().getSessionsMap().get(sessionName),
                                solveCodes.get(position), Utils.getPenalty(holder.isPlusTwo, holder.isDNF));

                        holder.plusTwo.setTextColor(Color.WHITE);
                        holder.plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    }
                }

        });
        holder.dnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("TAG",Sessions.getSingletonInstance().getSessionsMap().get(sessionName).toString());

                    if (!holder.isDNF) {
                        Log.i("TAG", "dnf clicked");
                        holder.isDNF = true;
                        holder.isPlusTwo = false;
                        Utils.modifySolvePenalty(context,
                                Sessions.getSingletonInstance().getSessionsMap().get(sessionName),
                                solveCodes.get(position), Utils.getPenalty(holder.isPlusTwo, holder.isDNF));

                        holder.dnf.setTextColor(Color.BLACK);
                        holder.dnf.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        holder.plusTwo.setTextColor(Color.WHITE);
                        holder.plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    } else {
                        holder.isDNF = false;
                        holder.isPlusTwo = false;
                        Utils.modifySolvePenalty(context,
                                Sessions.getSingletonInstance().getSessionsMap().get(sessionName),
                                solveCodes.get(position), Utils.getPenalty(holder.isPlusTwo, holder.isDNF));


                        holder.dnf.setTextColor(Color.WHITE);
                        holder.dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    }
                }

        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Session s=Sessions.getSingletonInstance().getSessionsMap().get(sessionName);
                s.removeSolve(solveCodes.get(position));
                Sessions.getSingletonInstance().editSession(s);
                Sessions.getSingletonInstance().writeToFile(context);
                solveCodes.remove(position);
                notifyItemRemoved(position);

                holder.isPlusTwo=false;
                holder.plusTwo.setTextColor(Color.WHITE);
                holder.plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

                holder.isDNF=false;
                holder.dnf.setTextColor(Color.WHITE);
                holder.dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }
        });
        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!holder.isExpanded) {
                    holder.scramble.setVisibility(View.VISIBLE);
                    holder.plusTwo.setVisibility(View.VISIBLE);
                    holder.dnf.setVisibility(View.VISIBLE);
                    holder.delete.setVisibility(View.VISIBLE);
                    holder.expand.setImageResource(R.drawable.ic_drop_up);
                    holder.isExpanded=true;
                }else{
                    holder.scramble.setVisibility(View.GONE);
                    holder.plusTwo.setVisibility(View.GONE);
                    holder.dnf.setVisibility(View.GONE);
                    holder.delete.setVisibility(View.GONE);
                    holder.expand.setImageResource(R.drawable.ic_drop_down);
                    holder.isExpanded=false;

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return solveCodes.size();

    }

     public static class CustomViewHolder extends RecyclerView.ViewHolder{
        TextView time;
        TextView scramble;
        MaterialButton delete;
        MaterialButton plusTwo;
        MaterialButton dnf;
        ImageButton expand;
        boolean isPlusTwo;
        boolean isDNF;
        boolean isExpanded=false;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            time= itemView.findViewById(R.id.solve_time);
            scramble=itemView.findViewById(R.id.solve_scramble);
            delete=itemView.findViewById(R.id.DELETE);
            plusTwo=itemView.findViewById(R.id.PLUSTWO);
            dnf=itemView.findViewById(R.id.DNF);
            expand=itemView.findViewById(R.id.expand);
        }

    }
}
