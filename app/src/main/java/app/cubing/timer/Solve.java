package app.cubing.timer;

public class Solve {
    final static int PENALTY_NONE=0;
    final static int PENALTY_PLUSTWO=1;
    final static int PENALTY_DNF=2;
    private float time;
    private int penalty;
    private String scramble;
    public Solve(float time,int penalty,String scramble){
        this.time=time;
        this.penalty=penalty;
        this.scramble=scramble;

    }
    public Solve(){}

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
    public String getScramble(){
        return this.scramble;
    }



    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
