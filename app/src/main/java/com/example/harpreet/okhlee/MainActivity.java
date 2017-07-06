package com.example.harpreet.okhlee;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.example.harpreet.okhlee.BottomNavigationFragments.Account;
import com.example.harpreet.okhlee.BottomNavigationFragments.news_feed;
import com.example.harpreet.okhlee.TabsFragments.buy;
import com.example.harpreet.okhlee.TabsFragments.sell;
import com.r0adkll.slidr.Slidr;

public class MainActivity extends AppCompatActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.news_Feed:
                        selectedFragment = new news_feed();
                   //     setMainTitle("OKHLEE");


                        //  mTextMessage.setText(R.string.title_home);
                        break;
                    case R.id.buy:
                        selectedFragment=new buy();
                        //mTextMessage.setText(R.string.title_dashboard);
                     //   setMainTitle("Buy");
                        break;
                    case R.id.sell:

                        selectedFragment=new sell();
                        //mTextMessage.setText(R.string.title_notifications);
                       // setMainTitle("Sell");
                        break;

                    case R.id.account:
                       selectedFragment=new Account();
                        //setMainTitle("Account");



                        break;

                }
                getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_in,R.anim.slide_out,R.anim.slide_in,R.anim.slide_out).replace(R.id.frame,selectedFragment).commit();
                return true;
            }
        });

       getSupportFragmentManager().beginTransaction().replace(R.id.frame,new news_feed()).commit();
    }
    public AppCompatActivity getActivityf()
    {
        return MainActivity.this;
    }





    public void setMainTitle(String title)
    {
        getSupportActionBar().setTitle(title);
    }



}
