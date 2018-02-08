package com.example.hp.test.New_UI_HHS.User.fragments;

import android.app.Fragment;
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

import com.example.hp.test.MainActivity;
import com.example.hp.test.New_UI_HHS.User.pages.User_Home;
import com.example.hp.test.R;
import com.example.hp.test.TestPage.testItemAdapter;
import com.example.hp.test.TestPage.testpageadapter;
import com.example.hp.test.adapters.Answer;
import com.example.hp.test.adapters.OverallResult;
import com.example.hp.test.adapters.TestAdapter;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adapters.result;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by hhs
 */

public class TestFragment extends Fragment {

    Firebase fb;
    String url="https://image-a519f.firebaseio.com/";
    public ArrayList<testpageadapter> testadap=new ArrayList<testpageadapter>();
    public ArrayList<String> correctanswer=new ArrayList<String>();
    //    public static ArrayList<String> answers1 =new ArrayList<String>();
    //    public static ArrayList<String> questionno=new ArrayList<>();
    RecyclerView recyclerView1;
    public Button button;
    SharedPreferences sharedP;
    String dep,yea,reg;
    TextView timer_text;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_test_page, container, false);
        Firebase.setAndroidContext(getActivity());

        timer_text = (TextView) v.findViewById(R.id.timer_text);

        fb=new Firebase(url);
        sharedP=getActivity().getSharedPreferences("data",MODE_PRIVATE);
        dep=sharedP.getString("department",null);
        yea=sharedP.getString("year",null);
        reg=sharedP.getString("regnum",null);


        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView1=(RecyclerView)view.findViewById(R.id.testRecycle);

        button=(Button)view.findViewById(R.id.submit);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity()));
        new MyTask().execute();


        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);

        testItemAdapter ta=new testItemAdapter(R.layout.aaa_testpagecard,testadap);

        recyclerView1.setAdapter(ta);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final iOSDialog iOSDialog = new iOSDialog(getActivity());
                iOSDialog.setTitle("Confirmation");
                iOSDialog.setSubtitle("End the Test?");
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

                        new Task().execute();
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.show();

            }
        });
    }

    public void startTimer(int noOfMinutes) {
        CountDownTimer  countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                timer_text.setText(hms);//set text
            }
            public void onFinish() {
                timer_text.setText("TIME'S UP!!"); //On finish change timer text
                Intent i = new Intent(getActivity(),User_Home.class);
                startActivity(i);
            }
        }.start();

    }

    public class MyTask extends AsyncTask<String, Integer, String>{


        @Override
        protected String doInBackground(String... params) {
            System.out.println("bow"+dummy.getName()+dummy.getCount1());
            String cu=dummy.getKey();
            System.out.println("bow"+cu);
            final String []bow = cu.split("@");
            sharedP=getActivity().getSharedPreferences("data",MODE_PRIVATE);

            String uurl=url+"Test/"+sharedP.getString("department",null)+"/"+sharedP.getString("year",null)+"/"+cu+"/";
            fb=new Firebase(uurl);
//            for(int i=0;i<=dummy.getCount1();i++){
//                answers1.add("");
//                questionno.add("");
//            }
            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        TestAdapter test=child.getValue(TestAdapter.class);
                        System.out.println("bow"+child.getKey());
                        testadap.add(new testpageadapter(test.getQuestion(),test.getC1(),test.getC2(),test.getC3(),test.getC4(),"",test.getCorrect()));
                        correctanswer.add(test.getCorrect());
                        testItemAdapter ta=new testItemAdapter(R.layout.aaa_testpagecard,testadap);
                        recyclerView1.setAdapter(ta);

                        startTimer((Integer.parseInt(bow[2]))*60 *1000);
                    }
                    System.out.println("answers"+correctanswer);
                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


            return null;
        }
    }
    public class Task extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            fb=new Firebase(url);
            int count = 0;


            for(int i=0;i<dummy.getQno().size();i++){
                System.out.println("ADAPTER IS "+dummy.getQno());
                Answer answ=new Answer();
                answ.setChosedans(dummy.getAns().get(i));
                System.out.println("bow"+dummy.getAns().get(i)+dummy.getQno().get(i));
                fb.child("Atest").child(dep).child(yea).child(reg).child(dummy.getKey()).child(dummy.getQno().get(i)).setValue(answ);
                System.out.println("bow:  upload");
                result resu=new result();
                System.out.println("AMELESH : "+dummy.getAns().get(i)+"   "+correctanswer.get(i));

                if(dummy.getAns().get(i).equals(correctanswer.get(i))){
                    resu.setVal(true);
                    resu.setCa(dummy.getAns().get(i));
                    fb.child("Result").child(dep).child(yea).child(reg).child(dummy.getKey()).child(dummy.getQno().get(i)).setValue(resu);
                    count+=1;
                }
                else{
                    resu.setVal(false);
                    resu.setCa(dummy.getAns().get(i));
                    fb.child("Result").child(dep).child(yea).child(reg).child(dummy.getKey()).child(dummy.getQno().get(i)).setValue(resu);
                }
                OverallResult or=new OverallResult();
                or.setTot(String.valueOf(count));
                fb.child("Result").child(dep).child(yea).child(reg).child(dummy.getKey()).child("Total").setValue(or);
            }
            startActivity(new Intent(getActivity(), User_Home.class));
            getActivity().finish();

            return null;
        }
    }
}
