package com.example.hp.test;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.User.fragments.Fragment1_1;
import com.example.hp.test.TestPage.testpage;
import com.example.hp.test.adapters.dashboard;

/**
 * Created by HP on 9/13/2017.
 */

public class StarTest extends Fragment {
    long totalSeconds = 30;
    long intervalSeconds = 1;
    CountDownTimer timer;
    TextView tv;
    View view;
    public StarTest() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.starttest,container,false);
        tv=(TextView)view.findViewById(R.id.timer);
       timer= new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tv.setText("done!");

                view.getContext().startActivity(new Intent(view.getContext(),testpage.class));
            }
        };
        timer.start();


        return view;
    }
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.starttest);
//            Button startnow=(Button)findViewById(R.id.startnow);
//        tv=(TextView)findViewById(R.id.timer);
//            timer = new CountDownTimer(totalSeconds * 1000, intervalSeconds * 1000) {
//
//                public void onTick(long millisUntilFinished) {
//                    Log.d("seconds elapsed: " , String.valueOf((totalSeconds * 1000 - millisUntilFinished) / 1000));
//
//                    tv.setText(""+String.valueOf((totalSeconds * 1000 - millisUntilFinished) / 1000));
//                }
//
//                public void onFinish() {
//                    Log.d( "done!", "Time's up!");
//                }
//
//            };
//            timer.start();
//            startnow.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    timer.cancel();
//                }
//            });

    }

