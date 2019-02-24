package app.cubing.timer;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import puzzle.ThreeByThreeCubePuzzle;


import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.material.button.MaterialButton;

import net.gnehzr.tnoodle.scrambles.InvalidScrambleException;
import net.gnehzr.tnoodle.scrambles.Puzzle;



public class TimerFragment extends Fragment  {
    private final static int MODE_IDLE=0;
    private final static int MODE_INSPECTING=1;
    private final static int MODE_PRESSED=2;
    private final static int MODE_TIMING=3;
    int currentMode=MODE_IDLE;
    boolean isInspectionEnabled;
    boolean isGreen=false;
    float elapsedTime;
    int currentPenalty=Solve.PENALTY_NONE;
    int currentPuzzleType;
    Session currentSession;


    Spinner puzzleType;
    TextView scramble;
    TextView timer;
    MaterialButton plusTwo;
    MaterialButton dnf;

    public TimerFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         return inflater.inflate(R.layout.fragment_timer, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initialize(view);

        Puzzle puzzle = new ThreeByThreeCubePuzzle();
        String scramble=puzzle.generateScramble();
        view.setOnTouchListener(listener);
        ImageView view1 = view.findViewById(R.id.scramble_image);
        try {
            PictureDrawable pictureDrawable = new PictureDrawable(SVG.getFromString(puzzle.drawScramble(scramble,null).toString()).renderToPicture());
            view1.setImageDrawable(pictureDrawable);
        } catch (SVGParseException e) {
            e.printStackTrace();
        } catch (InvalidScrambleException e) {
            e.printStackTrace();
        }


    }
    public void initialize(View view){
        puzzleType=view.findViewById(R.id.puzzle_type);
        Integer[] items={R.drawable.ic_2x2};
        PuzzleAdapter spinnerAdapter = new PuzzleAdapter(getActivity(),R.layout.puzzle_spinner_layout,items);
        puzzleType.setAdapter(spinnerAdapter);


        timer=view.findViewById(R.id.time);
        timer.setText("0.00");


        scramble=view.findViewById(R.id.scramble);


        plusTwo=view.findViewById(R.id.plus_two);
        dnf=view.findViewById(R.id.dnf);
        plusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPenalty==Solve.PENALTY_NONE||currentPenalty==Solve.PENALTY_DNF){
                    Log.i("TAG","PlusTwo clicked");

                    currentPenalty=Solve.PENALTY_PLUSTWO;
                    plusTwo.setTextColor(Color.BLACK);
                    plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    dnf.setTextColor(Color.WHITE);
                    dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }else{
                    currentPenalty=Solve.PENALTY_NONE;
                    plusTwo.setTextColor(Color.WHITE);
                    plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }
        });
        dnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPenalty==Solve.PENALTY_NONE||currentPenalty==Solve.PENALTY_PLUSTWO){
                    Log.i("TAG","dnf clicked");
                    currentPenalty=Solve.PENALTY_DNF;
                    dnf.setTextColor(Color.BLACK);
                    dnf.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                    plusTwo.setTextColor(Color.WHITE);
                    plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }else{
                    currentPenalty=Solve.PENALTY_NONE;
                    dnf.setTextColor(Color.WHITE);
                    dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                }
            }
        });
        if(getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null)!=null){
            currentSession=Sessions.getSingletonInstance().getSessionsMap().get(
                    getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null));
        }else{
            currentPuzzleType=Session.TYPE_3X3;
            showDialog();
            currentSession=Sessions.getSingletonInstance().getSessionsMap().get("Default 3x3 session");

        }



    }


    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("It seems that you don't have a session for "+Utils.getPuzzleTypeName(currentPuzzleType)+"."+"Let's create a session now.");
        builder.setTitle("No Session");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session session = new Session();
                session.setName("Default "+Utils.getPuzzleTypeName(currentPuzzleType)+" session");
                session.setType(currentPuzzleType);
                Sessions.getSingletonInstance().addSession(session);
                Sessions.getSingletonInstance().writeToFile(getContext());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
    public View.OnTouchListener listener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                switch(currentMode){
                    case MODE_IDLE:
                        if(isInspectionEnabled){
                            currentMode=MODE_INSPECTING;
                            inspectingHandler();
                            break;
                        }else{
                            isGreen=false;

                            currentMode=MODE_PRESSED;
                            pressedHandler();
                            break;
                        }

                    case MODE_INSPECTING:
                        isGreen=false;
                        currentMode=MODE_PRESSED;
                        pressedHandler();
                        break;
                    case MODE_PRESSED:
                        break;
                    case MODE_TIMING:
                        currentMode=MODE_IDLE;
                        afterSolveHandler();
                        break;

                }
            }else if(event.getAction()==MotionEvent.ACTION_UP){
                Log.i("TAG","finger released");
                switch(currentMode){
                    case MODE_IDLE:
                        break;
                    case MODE_INSPECTING:
                        break;
                    case MODE_PRESSED:
                        Log.i("TAG","finger released");
                        if(isGreen) {
                            currentMode = MODE_TIMING;
                            timingHandler();
                        }else{
                            currentMode=MODE_IDLE;
                            timer.setTextColor(getResources().getColor(android.R.color.white));
                        }
                        break;
                    case MODE_TIMING:
                        break;
                }
            }
            return true;
        }
    };
    public void pressedHandler(){

        Log.i("TAG","pressedHandler called");
       final Handler handler=new Handler();
       Thread thread=new Thread(new Runnable() {
           @Override
           public void run() {

               long time = System.currentTimeMillis();


               while (System.currentTimeMillis() - time < 500 && currentMode == MODE_PRESSED) {
                   try {
                       Thread.sleep(10);
                   } catch (InterruptedException e) {
                       e.printStackTrace();
                   }
               }
               Log.i("TAG","Exited");
               handler.post(new Runnable() {
                   @Override
                   public void run() {
                       if (currentMode == MODE_PRESSED) {
                           isGreen = true;
                           timer.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                       }

                   }
               });

           }
       });


       thread.run();
    }






    public void inspectingHandler(){

           }
    public void timingHandler(){
        final long time=System.currentTimeMillis();
        final Handler handler=new Handler();
        final Runnable timingRunnable;

        timer.setTextColor(getResources().getColor(android.R.color.white));
        timingRunnable= new Runnable() {

            @Override
            public void run() {

                if(currentMode==MODE_TIMING){
                    double eTime=System.currentTimeMillis()-time;
                    timer.setText(Utils.formatTime(eTime,true));
                    elapsedTime= (float)eTime/1000;

                    handler.postDelayed(this,10);
                }
            }



        };
        handler.post(timingRunnable);


    }
    public void afterSolveHandler(){
        Solve solve=new Solve(elapsedTime,currentPenalty);
        currentSession.addSolve(solve);
        Sessions.getSingletonInstance().editSession(currentSession);
        Sessions.getSingletonInstance().writeToFile(getContext());



    }







}
