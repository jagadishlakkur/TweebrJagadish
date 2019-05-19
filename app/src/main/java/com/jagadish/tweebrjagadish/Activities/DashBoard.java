package com.jagadish.tweebrjagadish.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jagadish.tweebrjagadish.Fragments.Images_Frag;
import com.jagadish.tweebrjagadish.Fragments.VideoTrim_Frag;
import com.jagadish.tweebrjagadish.Fragments.Videos_Frag;
import com.jagadish.tweebrjagadish.R;

public class DashBoard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bnv);

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.framelayout,new Images_Frag());
        fragmentTransaction.commit();

        getSupportActionBar().setElevation(0);




        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {

                    case R.id.menu_images:

                    LoadFrag(new Images_Frag());

                    break;


                    case R.id.menu_videos:
                    LoadFrag(new VideoTrim_Frag());
                    break;

                    case R.id.menu_videos_text:
                        LoadFrag(new Videos_Frag());
                        break;

                }


                return false;
            }
        });


    }

    private void LoadFrag(Fragment sampleHome) {

        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,sampleHome);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lang,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.menu_changelang:

                startActivity(new Intent(getApplicationContext(),LangSelect.class));

                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
