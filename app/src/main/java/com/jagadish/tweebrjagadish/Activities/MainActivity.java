package com.jagadish.tweebrjagadish.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jagadish.tweebrjagadish.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sharedPreferences=getSharedPreferences("Tweebr",MODE_PRIVATE);

                if (sharedPreferences.contains("lang"))
                {
                    startActivity(new Intent(getApplicationContext(),DashBoard.class));
                }else {
                    startActivity(new Intent(getApplicationContext(),LangSelect.class));
                }



            }
        }, 10000);


    }
}
