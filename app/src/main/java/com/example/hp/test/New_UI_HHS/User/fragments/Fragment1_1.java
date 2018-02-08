package com.example.hp.test.New_UI_HHS.User.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.pages.User_Home;
import com.example.hp.test.R;
import com.example.hp.test.StarTest;
import com.example.hp.test.TestPage.testpage;
import com.example.hp.test.adapters.dashadapter;
import com.example.hp.test.adapters.dashitemadapter;
import com.example.hp.test.adapters.dummy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;


/**
 * Created by hhs
 */

public class Fragment1_1 extends Fragment {

    long totalSeconds = 30;
    long intervalSeconds = 1;
    CountDownTimer timer;
    TextView tv;
    Button start;
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_start_test, container, false);

        tv=(TextView)v.findViewById(R.id.timer);
        start=(Button)v.findViewById(R.id.start);
        timer= new CountDownTimer(25000, 1000) {

            public void onTick(long millisUntilFinished) {
                tv.setText("You can begin your assessment in : " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                //tv.setText("All The Best!");
                getFragmentManager().beginTransaction().replace(R.id.container,new TestFragment()).commit();
                //view.getContext().startActivity(new Intent(view.getContext(),aaa_Test_Page.class));
            }
        };
        timer.start();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final iOSDialog iOSDialog = new iOSDialog(getActivity());
                iOSDialog.setTitle("Confirmation");
                iOSDialog.setSubtitle("Start the test now?");
                iOSDialog.setNegativeLabel("No");
                iOSDialog.setPositiveLabel("Yes");
                iOSDialog.setBoldPositiveLabel(true);
                iOSDialog.setNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.setPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        timer.cancel();
                        getFragmentManager().beginTransaction().replace(R.id.container,new TestFragment()).commit();
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.show();


            }
        });
        return v;
    }


}
