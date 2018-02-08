package com.example.hp.test;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hp.test.New_UI_HHS.Login.New_login;

/**
 * Created by nadus on 19-10-2017.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_splash);

        new Handler().postDelayed(new Runnable() {

            // Using handler with postDelayed called runnable run method

            @Override
            public void run() {
                Intent i = new Intent(Splash.this, New_login.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 2*1000); // wait for 5 seconds
    }
}
