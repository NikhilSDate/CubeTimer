package app.cubing.timer;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView mainNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Sessions.getSingletonInstance().writeToFile(this);
        Log.i("TAG",Sessions.getSingletonInstance().getSessionsMap().toString());
        mainNav=findViewById(R.id.mainNav);
        TimerFragment t = new TimerFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("puzzleType",Session.TYPE_2X2);
        t.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainFrag,t);
        transaction.commit();
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainFrag,new SessionFragment());
                transaction.commit();

                return true;
            }
        });
//        AssetManager am = this.getApplicationContext().getAssets();
//
//         Typeface typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "GoogleSans-Regular.ttf"));



    }
    public void replaceFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainFrag,new SessionFragment());
        transaction.commit();
    }

}
