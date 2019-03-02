package app.cubing.timer;

import android.content.Context;

import net.gnehzr.tnoodle.scrambles.Puzzle;

import java.text.DecimalFormat;

import puzzle.ClockPuzzle;
import puzzle.CubePuzzle;
import puzzle.FourByFourCubePuzzle;
import puzzle.MegaminxPuzzle;
import puzzle.PyraminxPuzzle;
import puzzle.SkewbPuzzle;
import puzzle.SquareOnePuzzle;
import puzzle.ThreeByThreeCubePuzzle;
import puzzle.TwoByTwoCubePuzzle;

public class Utils {
        public static String scramble(String puzzleName){
            switch (puzzleName) {
                case "2x2x2":
                    Puzzle puzzle1 = new TwoByTwoCubePuzzle();
                    return puzzle1.generateScramble();


                case "3x3x3":
                    Puzzle puzzle2 = new ThreeByThreeCubePuzzle();
                    return puzzle2.generateScramble();

                case "4x4x4":
                    Puzzle puzzle3=new FourByFourCubePuzzle();
                    return puzzle3.generateScramble();

                case "5X5X5":
                    Puzzle puzzle4=new CubePuzzle(5);
                    return puzzle4.generateScramble();

                case "6x6x6":
                    Puzzle puzzle5=new CubePuzzle(6);
                    return puzzle5.generateScramble();

                case "7x7x7":
                    Puzzle puzzle6=new CubePuzzle(7);
                    return puzzle6.generateScramble();

                case "Pyraminx":
                    Puzzle puzzle7=new PyraminxPuzzle();
                    return puzzle7.generateScramble();

                case "Skewb":
                    Puzzle puzzle8=new SkewbPuzzle();
                    return puzzle8.generateScramble();

                case "Square-1":
                    Puzzle puzzle9=new SquareOnePuzzle();
                    return puzzle9.generateScramble();

                case "Clock":
                    Puzzle puzzle10=new ClockPuzzle();
                    return puzzle10.generateScramble();

                case "Megaminx":
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
                    ,R.drawable.ic_6x6,R.drawable.ic_7x7,R.drawable.ic_prya,R.drawable.ic_skewb,R.drawable.ic_mega,R.drawable.ic_sq1};
        }
}

