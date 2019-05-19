package com.jagadish.tweebrjagadish.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jagadish.tweebrjagadish.PreferenceUtil;
import com.jagadish.tweebrjagadish.R;

import java.util.Locale;

public class LangSelect extends AppCompatActivity {

    TextView eng,hindi;
    Button signin;


    int lan_selected=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lang_select);

        eng=(TextView)findViewById(R.id.lang_eng);
        hindi=(TextView)findViewById(R.id.lan_hindi);
        signin=(Button) findViewById(R.id.btn_continue);


        eng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lan_selected=1;

                PreferenceUtil.saveData("lang","",getApplicationContext());
                setLocatization();



                eng.setBackgroundResource(R.drawable.background);
                hindi.setBackgroundResource(R.drawable.edit_text_gray_background);
            }
        });


        hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                lan_selected=1;

                PreferenceUtil.saveData("lang","hi",getApplicationContext());
                setLocatization();

                hindi.setBackgroundResource(R.drawable.background);
                eng.setBackgroundResource(R.drawable.edit_text_gray_background);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (lan_selected==0)
                {

                    Snackbar.make(getWindow().getDecorView().getRootView(),"Select lang",Snackbar.LENGTH_LONG).show();
                }else {

                    startActivity(new Intent(getApplicationContext(),DashBoard.class));
                }
            }
        });




    }

    void setLocatization()
    {

        String lan=PreferenceUtil.getData("lang",getApplicationContext());

        String languageToLoad  = lan; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

    }
}
