package app.cubing.timer;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.PictureDrawable;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import puzzle.ThreeByThreeCubePuzzle;


import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
    int currentPuzzleType;
    Session currentSession;
    boolean isDNF;
    boolean isPlusTwo;
    int currentSolveCode=-1;


    Spinner puzzleType;
    TextView scramble;
    TextView timer;
    MaterialButton plusTwo;
    MaterialButton dnf;
    MaterialButton delete;

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

        PuzzleAdapter spinnerAdapter = new PuzzleAdapter(getActivity(),R.layout.puzzle_spinner_layout,Utils.getPuzzleDrawableIds());
        puzzleType.setAdapter(spinnerAdapter);
        puzzleType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int item = Integer.parseInt(parent.getItemAtPosition(position).toString());
                currentPuzzleType=Utils.getPuzzleTypeFromInt(item);
                boolean doesSessionExist=false;
                for(Session session:Sessions.getSingletonInstance().getSessionsMap().values()){
                    if(session.getType()==currentPuzzleType){
                        doesSessionExist=true;
                        break;
                    }
                }
                if(doesSessionExist){
                    for(Session session:Sessions.getSingletonInstance().getSessionsMap().values()){
                        if(session.getType()==currentPuzzleType){
                           currentSession=session;
                            SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("currentSession",session.getName());
                            editor.apply();

                        }
                    }
                }else{
                    showDialog();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        timer=view.findViewById(R.id.time);
        timer.setText("0.00");


        scramble=view.findViewById(R.id.scramble);


        plusTwo=view.findViewById(R.id.plus_two);
        dnf=view.findViewById(R.id.dnf);
        plusTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSolveCode != -1) {
                    if (!isPlusTwo) {
                        Log.i("TAG", "PlusTwo clicked");
                        isPlusTwo = true;
                        isDNF = false;
                        Utils.modifySolvePenalty(getContext(), currentSession, currentSolveCode, Utils.getPenalty(isPlusTwo, isDNF));
                        plusTwo.setTextColor(Color.BLACK);
                        plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        dnf.setTextColor(Color.WHITE);
                        dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    } else {
                        isPlusTwo = false;
                        isDNF = false;
                        Utils.modifySolvePenalty(getContext(), currentSession, currentSolveCode, Utils.getPenalty(isPlusTwo, isDNF));

                        plusTwo.setTextColor(Color.WHITE);
                        plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    }
                }
            }
        });

        dnf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSolveCode != -1) {
                    if (!isDNF) {
                        Log.i("TAG", "dnf clicked");
                        isDNF = true;
                        isPlusTwo = false;
                        Utils.modifySolvePenalty(getContext(), currentSession, currentSolveCode, Utils.getPenalty(isPlusTwo, isDNF));

                        dnf.setTextColor(Color.BLACK);
                        dnf.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
                        plusTwo.setTextColor(Color.WHITE);
                        plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    } else {
                        isDNF = false;
                        isPlusTwo = false;
                        Utils.modifySolvePenalty(getContext(), currentSession, currentSolveCode, Utils.getPenalty(isPlusTwo, isDNF));

                        dnf.setTextColor(Color.WHITE);
                        dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
                    }
                }
            }
        });
        delete=view.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.setText("0.00");

                currentSession.removeSolve(currentSolveCode);
                currentSolveCode=-1;

                isPlusTwo=false;
                plusTwo.setTextColor(Color.WHITE);
                plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

                isDNF=false;
                dnf.setTextColor(Color.WHITE);
                dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
            }
        });
        if(getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null)!=null){
            currentSession=Sessions.getSingletonInstance().getSessionsMap().get(
                    getActivity().getPreferences(Context.MODE_PRIVATE).getString("currentSession",null));
            currentPuzzleType=currentSession.getType();
            puzzleType.setSelection(Utils.getIndexFromPuzzleType(spinnerAdapter,currentPuzzleType));
        }else{
            currentPuzzleType=Session.TYPE_3X3;
            showDialog();




        }



    }


    public void showDialog(){
        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity());
        builder.setMessage("It seems that you don't have a session for "+Utils.getPuzzleTypeName(currentPuzzleType)+"."+"Let's create a session now.");
        builder.setTitle("No Session");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Session session = new Session();
                session.setName("Default "+Utils.getPuzzleTypeName(currentPuzzleType)+" session");
                session.setType(currentPuzzleType);

                Sessions.getSingletonInstance().addSession(session);
                Log.i("TAG",Sessions.getSingletonInstance().getSessionsMap().toString());
                Sessions.getSingletonInstance().writeToFile(getContext());
                currentSession=session;
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("currentSession",session.getName());
                editor.apply();

                dialog.dismiss();

            }
        });
        builder.show();

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
        isPlusTwo=false;
        plusTwo.setTextColor(Color.WHITE);
        plusTwo.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));

        isDNF=false;
        dnf.setTextColor(Color.WHITE);
        dnf.setBackgroundTintList(ColorStateList.valueOf(Color.BLACK));
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



        Solve solve=new Solve(elapsedTime,Solve.PENALTY_NONE);
        currentSolveCode=currentSession.addSolve(solve);
        Sessions.getSingletonInstance().editSession(currentSession);
        Sessions.getSingletonInstance().writeToFile(getContext());
        Log.i("TAG",Sessions.getSingletonInstance().getSessionsMap().toString());


    }
    private class ScrambleGeneratorAsync extends AsyncTask<Integer,String,String> {
        @Override
        public String doInBackground(Integer... params){
            return Utils.scramble(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            scramble.setText(s);
        }
    }







}
