package app.cubing.timer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;

public class Sessions {
    private static Sessions sessions = new Sessions();
    private HashMap<String,Session> sessionsMap=new HashMap<String, Session>();
    private Sessions(){

    }
    public static Sessions getSingletonInstance(){
        return sessions;
    }
    public HashMap<String,Session> getSessionsMap(){
        return sessionsMap;
    }
    public void addSession(Session session){
        sessionsMap.put(session.getName(),session);

    }
    public void removeSession(String sessionName){
        sessionsMap.remove(sessionName);
    }
    public void writeToFile(Context context){
        try {
            FileOutputStream stream = context.openFileOutput("sessionsData.csv", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            BufferedWriter bufferedWriter=new BufferedWriter(writer);
            for(Session session:sessionsMap.values()){
                bufferedWriter.write(session.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            writer.close();
            stream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void getFromFile(Context context){
        try {
            FileInputStream stream = context.openFileInput("sessionsData.csv");
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while((line=bufferedReader.readLine())!=null){
                this.sessionsMap.put(Session.toSession(line).getName(),Session.toSession(line));

            }
            bufferedReader.close();
            reader.close();
            stream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
