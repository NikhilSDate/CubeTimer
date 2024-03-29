package app.cubing.timer;


import android.content.Context;

import android.util.Log;
import android.widget.ArrayAdapter;

import org.worldcubeassociation.tnoodle.scrambles.Puzzle;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import org.worldcubeassociation.tnoodle.puzzle.ClockPuzzle;
import org.worldcubeassociation.tnoodle.puzzle.ThreeByThreeCubePuzzle;
import org.worldcubeassociation.tnoodle.puzzle.FourByFourCubePuzzle;
import org.worldcubeassociation.tnoodle.puzzle.MegaminxPuzzle;
import org.worldcubeassociation.tnoodle.puzzle.PyraminxPuzzle;
import org.worldcubeassociation.tnoodle.puzzle.SkewbPuzzle;
import org.worldcubeassociation.tnoodle.puzzle.SquareOnePuzzle;
import org.worldcubeassociation.tnoodle.puzzle.CubePuzzle;
import org.worldcubeassociation.tnoodle.puzzle.TwoByTwoCubePuzzle;

public class Utils {
        public static String scramble(int puzzleType){
            switch (puzzleType) {
                case Session.TYPE_2X2:
                    Puzzle puzzle1 = new TwoByTwoCubePuzzle();
                    return puzzle1.generateScramble();


                case Session.TYPE_3X3:
                    Puzzle puzzle2 = new ThreeByThreeCubePuzzle();

                    return puzzle2.generateScramble();

                case Session.TYPE_4X4:
                    Puzzle puzzle3=new FourByFourCubePuzzle();
                    return puzzle3.generateScramble();

                case Session.TYPE_5X5:
                    Puzzle puzzle4=new CubePuzzle(5);
                    return puzzle4.generateScramble();

                case Session.TYPE_6X6:
                    Puzzle puzzle5=new CubePuzzle(6);
                    return puzzle5.generateScramble();

                case Session.TYPE_7X7:
                    Puzzle puzzle6=new CubePuzzle(7);
                    return puzzle6.generateScramble();

                case Session.TYPE_PYRAMINX:
                    Puzzle puzzle7=new PyraminxPuzzle();
                    return puzzle7.generateScramble();

                case Session.TYPE_SKEWB:
                    Puzzle puzzle8=new SkewbPuzzle();
                    return puzzle8.generateScramble();

                case Session.TYPE_SQUARE1:
                    Puzzle puzzle9=new SquareOnePuzzle();
                    return puzzle9.generateScramble();

                case Session.TYPE_CLOCK:
                    Puzzle puzzle10=new ClockPuzzle();
                    return puzzle10.generateScramble();

                case Session.TYPE_MEGAMINX:
                    Puzzle puzzle11=new MegaminxPuzzle();
                    return puzzle11.generateScramble();

                default:
                    return "Error";



            }
        }
        public static String formatTime(double millis,boolean isThreeDigits){
            int minutes = (int)((millis/1000)-(millis/1000)%60)/60;
            String secondsAndMilliseconds;
            if(isThreeDigits){
                secondsAndMilliseconds=new DecimalFormat("#,###,##0.000").format((millis/1000d)%60d);
            }else {
                secondsAndMilliseconds=new DecimalFormat("#,###,##0.00").format((millis/1000d)%60d);
            }
            if(minutes!=0) {
                return minutes + ":" + secondsAndMilliseconds;
            }else{
                return secondsAndMilliseconds;
            }
        }
        public static String getPuzzleTypeName(int puzzleTypeInt){
            switch(puzzleTypeInt){
                case Session.TYPE_2X2:
                    return "2x2";
                case Session.TYPE_3X3:
                    return "3x3";
                case Session.TYPE_4X4:
                    return "4X4";
                case Session.TYPE_5X5:
                    return "5x5";
                case Session.TYPE_6X6:
                    return "6x6";
                case Session.TYPE_7X7:
                    return "7x7";
                case Session.TYPE_PYRAMINX:
                    return "Pyra";
                case Session.TYPE_SKEWB:
                    return "Skewb";
                case Session.TYPE_MEGAMINX:
                    return "Mega";
                case Session.TYPE_SQUARE1:
                    return "SQ1";
                case Session.TYPE_CLOCK:
                    return "Clock";
                default:
                    return "";



            }
        }
        public static int getPenalty(boolean isPlusTwo,boolean isDNF){
            if(isPlusTwo){
                return Solve.PENALTY_PLUSTWO;
            }else if(isDNF){
                return  Solve.PENALTY_DNF;
            }else{
                return Solve.PENALTY_NONE;
            }
        }
        public static void modifySolvePenalty(Context context,Session session, int code, int penalty){
            Solve solve=session.getSolve(code);
            solve.setPenalty(penalty);
            session.editSolve(code,solve);
            Sessions.getSingletonInstance().editSession(session);
            Sessions.getSingletonInstance().writeToFile(context);


        }
        public static Integer[] getPuzzleDrawableIds(){
            return new Integer[]{R.drawable.ic_2x2,R.drawable.ic_3x3,R.drawable.ic_4x4,R.drawable.ic_5x5
                    ,R.drawable.ic_6x6, R.drawable.ic_7x7,R.drawable.ic_prya,R.drawable.ic_skewb,R.drawable.ic_mega,R.drawable.ic_sq1};
        }
        public static int getPuzzleTypeFromInt(int drawable){
            switch (drawable){
                case R.drawable.ic_2x2:
                    return Session.TYPE_2X2;
                case R.drawable.ic_3x3:
                    return Session.TYPE_3X3;
                case R.drawable.ic_4x4:
                    return Session.TYPE_4X4;
                case R.drawable.ic_5x5:
                    return Session.TYPE_5X5;
                case R.drawable.ic_6x6:
                    return Session.TYPE_6X6;
                case R.drawable.ic_7x7:
                    return Session.TYPE_7X7;
                case R.drawable.ic_prya:
                    return Session.TYPE_PYRAMINX;
                case R.drawable.ic_skewb:
                    return Session.TYPE_SKEWB;
                case R.drawable.ic_mega:
                    return Session.TYPE_MEGAMINX;
                case R.drawable.ic_sq1:
                    return Session.TYPE_SQUARE1;

                default:
                    return -1;


            }
        }
        public static int getIndexFromPuzzleType(ArrayAdapter<Integer> adapter,int puzzleType){
            switch(puzzleType){
                case Session.TYPE_2X2:
                    return adapter.getPosition(R.drawable.ic_2x2);
                case Session.TYPE_3X3:
                    return adapter.getPosition(R.drawable.ic_3x3);
                case Session.TYPE_4X4:
                    return adapter.getPosition(R.drawable.ic_4x4);
                case Session.TYPE_5X5:
                    return adapter.getPosition(R.drawable.ic_5x5);
                case Session.TYPE_6X6:
                    return adapter.getPosition(R.drawable.ic_6x6);
                case Session.TYPE_7X7:
                    return adapter.getPosition(R.drawable.ic_7x7);
                case Session.TYPE_PYRAMINX:
                    return adapter.getPosition(R.drawable.ic_prya);
                case Session.TYPE_SKEWB:
                    return adapter.getPosition(R.drawable.ic_skewb);
                case Session.TYPE_MEGAMINX:
                    return adapter.getPosition(R.drawable.ic_mega);
                case Session.TYPE_SQUARE1:
                    return adapter.getPosition(R.drawable.ic_sq1);
                default:
                    return -1;
            }

        }
    public static Fragment getVisibleFragment(AppCompatActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }
    public static ArrayList<String> getSessionNames(){
            ArrayList<String> names=new ArrayList<String>();
            for(Session s:Sessions.getSingletonInstance().getSessionsMap().values()){
                names.add(s.getName());
            }
            return names;
    }
    public static ArrayList<String> getFilteredSessions(int puzzleType, @Nullable String search ){
            ArrayList<String> filteredSessions=new ArrayList<String>();
            ArrayList<String> finalList=new ArrayList<String>();

        for(Session s:Sessions.getSingletonInstance().getSessionsMap().values()){
                if(s.getType()==puzzleType){
                    filteredSessions.add(s.getName());
                }
            }
            if(search==null) {
                finalList=filteredSessions;
            }else{
                String[] tokens=search.split(" ");
                for(String s:filteredSessions){
                    boolean contains=true;
                    for(String token:tokens){
                        if(!s.toUpperCase().trim().contains(token.toUpperCase())){
                            contains=false;
                        }

                    }
                    Log.i("TAG",String.valueOf(contains));
                    if(contains){
                        finalList.add(s);
                    }
                }
                Log.i("TAG",finalList.toString());



            }
            return finalList;

    }

}

