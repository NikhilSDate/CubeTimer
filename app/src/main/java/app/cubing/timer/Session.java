package app.cubing.timer;

import java.util.ArrayList;

import java.util.LinkedHashMap;

import static java.lang.Float.NaN;


public class Session {
    final static int TYPE_2X2=0;
    final static int TYPE_3X3=1;
    final static int TYPE_4X4=2;
    final static int TYPE_5X5=3;
    final static int TYPE_6X6=4;
    final static int TYPE_7X7=5;
    final static int TYPE_PYRAMINX=6;
    final static int TYPE_SKEWB=7;
    final static int TYPE_SQUARE1=8;
    final static int TYPE_MEGAMINX=9;
    final static int TYPE_CLOCK=10;


    private String name;
    private int type;
    private LinkedHashMap<Integer,Solve> solves =new LinkedHashMap<Integer, Solve>();
    public Session(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int addSolve(Solve s){
        Integer code=solves.size();
        while(solves.containsKey(code)){
            code=code+1;
        }
        solves.put(code,s);
        return code;

    }
    public void editSolve(int code,Solve solve){
        solves.put(code,solve);
    }
    public Solve getSolve(int code){
        return solves.get(code);
    }
    public void removeSolve(int code){
        solves.remove(code);
    }
    public int getCount(){
        return solves.size();
    }
    public float getAo5(){
        if(solves.size()<5){
            return NaN;
        }
        Integer[] codesArray=solves.keySet().toArray(new Integer[solves.keySet().size()]);
        float[] times=new float[5];
        for(int i =codesArray.length-5;i<=codesArray.length-1;i++){
            times[i-(codesArray.length-5)]=solves.get(codesArray[i]).getTime();
        }
        int minIndex=0;
        int maxIndex=0;
        float min=Integer.MAX_VALUE;
        float max=Integer.MIN_VALUE;
        for(int i =0;i<5;i++){
            if(times[i]<min){
                min=times[i];
                minIndex=i;
            }
        }
        for(int i =0;i<5;i++){
            if(times[i]>max){
                max=times[i];
                maxIndex=i;
            }
        }
        float countingSum=0;
        for (int i=0;i<5;i++){
            if(i!=minIndex&&i!=maxIndex){
                countingSum+=times[i];
            }
        }
        return countingSum/3;
    }
    public float getAo12(){
        if(solves.size()<12){
            return NaN;
        }
        Integer[] codesArray=solves.keySet().toArray(new Integer[solves.keySet().size()]);
        float[] times=new float[12];
        for(int i =codesArray.length-12;i<=codesArray.length-1;i++){
            times[i-(codesArray.length-12)]=solves.get(codesArray[i]).getTime();
        }
        int minIndex=0;
        int maxIndex=0;
        float min=Integer.MAX_VALUE;
        float max=Integer.MIN_VALUE;
        for(int i =0;i<12;i++){
            if(times[i]<min){
                min=times[i];
                minIndex=i;
            }
        }
        for(int i =0;i<12;i++){
            if(times[i]>max){
                max=times[i];
                maxIndex=i;
            }
        }
        float countingSum=0;
        for (int i=0;i<12;i++){
            if(i!=minIndex&&i!=maxIndex){
                countingSum+=times[i];
            }
        }
        return countingSum/12;
    }
    public float getAo50(){
        if(solves.size()<50){
            return NaN;
        }
        Integer[] codesArray=solves.keySet().toArray(new Integer[solves.keySet().size()]);
        ArrayList<Float> times=new ArrayList<Float>();
        for(int i =codesArray.length-50;i<=codesArray.length-1;i++){
            times.add(solves.get(codesArray[i]).getTime());
        }
        for(int i=0;i<3;i++){
            int minIndex=0;
            int maxIndex=0;
            float min=Integer.MAX_VALUE;
            float max=Integer.MIN_VALUE;
            for(int j =0;j<50-i;j++){
                if(times.get(j)<min){
                    min=times.get(j);
                    minIndex=j;
                }
            }
            for(int j =0;j<50-i;j++){
                if(times.get(j)>max){
                    max=times.get(j);
                    maxIndex=j;
                }
            }
            times.remove(minIndex);
            times.remove(maxIndex);
        }
        float countingSum=0;
        for(int i =0;i<44;i++){
            countingSum+=times.get(i);
        }
        return countingSum/44;

    }
    public float getAo100(){
        if(solves.size()<100){
            return NaN;
        }
        Integer[] codesArray=solves.keySet().toArray(new Integer[solves.keySet().size()]);
        ArrayList<Float> times=new ArrayList<Float>();
        for(int i =codesArray.length-100;i<=codesArray.length-1;i++){
            times.add(solves.get(codesArray[i]).getTime());
        }
        for(int i=0;i<5;i++){
            int minIndex=0;
            int maxIndex=0;
            float min=Integer.MAX_VALUE;
            float max=Integer.MIN_VALUE;
            for(int j =0;j<50-i;j++){
                if(times.get(j)<min){
                    min=times.get(j);
                    minIndex=j;
                }
            }
            for(int j =0;j<50-i;j++){
                if(times.get(j)>max){
                    max=times.get(j);
                    maxIndex=j;
                }
            }
            times.remove(minIndex);
            times.remove(maxIndex);
        }
        float countingSum=0;
        for(int i =0;i<90;i++){
            countingSum+=times.get(i);
        }
        return countingSum/90;

    }
    public float getMo3(){
        if(solves.size()<3){
            return NaN;
        }
        Integer[] codesArray=solves.keySet().toArray(new Integer[solves.keySet().size()]);
        float[] times=new float[3];
        for(int i =codesArray.length-3;i<=codesArray.length-1;i++){
            times[i-codesArray.length-3]=solves.get(codesArray[i]).getTime();
        }
        return (times[0]+times[1]+times[2])/3;
    }
    public float getMean(){
        if(solves.size()<1){
            return NaN;
        }
        float sum=0;
        for(Solve solve:solves.values()){
            sum+=solve.getTime();
        }
        return sum/solves.size();

    }
    public float getStandardDeviation(){
        float mean=this.getMean();
        float totalSquaredError=0;
        for(Solve solve:solves.values()){
            totalSquaredError+=Math.pow(mean-solve.getTime(),2);
        }
        return (float)Math.sqrt(totalSquaredError/(solves.size()-1));

    }

    @Override
    public String toString() {
        String str="";
        str+=name;
        str+="~"+type;
        for(LinkedHashMap.Entry<Integer,Solve> entry:solves.entrySet()){
            str+="~"+entry.getKey();
            str+="~"+entry.getValue().getTime();
            str+="~"+entry.getValue().getPenalty();
            str+="~"+entry.getValue().getScramble();

        }
        return str;
    }
    public static Session toSession(String str){
        Session session=new Session();
        String[] tokens = str.split("~");
        session.name=tokens[0];
        session.type=Integer.parseInt(tokens[1]);
        for(int i=5;i<tokens.length;i+=4){
            int code=Integer.parseInt(tokens[i-3]);
            float time=Float.parseFloat(tokens[i-2]);
            String penalty=tokens[i-1];
            String scramble=tokens[i];
            Solve solve=new Solve(time,Integer.parseInt(penalty),scramble);
            session.solves.put(code,solve);

        }
        return session;
    }
    public LinkedHashMap<Integer,Solve> getSolvesMap(){
        return this.solves;
    }
}
