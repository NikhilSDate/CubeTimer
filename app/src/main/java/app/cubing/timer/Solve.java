package app.cubing.timer;

public class Solve {
    final static int PENALTY_NONE=0;
    final static int PENALTY_PLUSTWO=1;
    final static int PENALTY_DNF=2;
    private float time;
    private int penalty;
    public Solve(float time,int penalty){
        this.time=time;
        this.penalty=penalty;

    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }



    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}
