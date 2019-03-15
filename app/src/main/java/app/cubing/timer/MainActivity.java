package app.cubing.timer;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
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

        Sessions.getSingletonInstance().getFromFile(this);
        mainNav=findViewById(R.id.mainNav);
        TimerFragment t = new TimerFragment();

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.mainFrag,t);
        transaction.commit();
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.timer:
                        if(!(Utils.getVisibleFragment(MainActivity.this) instanceof TimerFragment)){
                            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.mainFrag,new TimerFragment());
                            transaction.commit();

                        }
                    break;
                    case R.id.log:
                        if(!(Utils.getVisibleFragment(MainActivity.this) instanceof LogFragment)){
                            FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.mainFrag,new LogFragment());
                            transaction.commit();

                        }
                        break;
                    case R.id.training:
                        break;
                    case R.id.settings:
                        break;
                }
            return true;
            }
        });
//        AssetManager am = this.getApplicationContext().getAssets();
//
//         Typeface typeface = Typeface.createFromAsset(am,
//                String.format(Locale.US, "fonts/%s", "GoogleSans-Regular.ttf"));



    }



}
