package com.example.hp.test.TestPage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.hp.test.MainActivity;
import com.example.hp.test.R;
import com.example.hp.test.adapters.Answer;
import com.example.hp.test.adapters.OverallResult;
import com.example.hp.test.adapters.TestAdapter;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adapters.result;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HP on 9/13/2017.
 */

public class testpage extends AppCompatActivity
{
    ProgressDialog progressDialog;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/";
    public ArrayList<testpageadapter> testadap=new ArrayList<testpageadapter>();
    public ArrayList<String> correctanswer=new ArrayList<String>();
//    public static ArrayList<String> answers1 =new ArrayList<String>();
//    public static ArrayList<String> questionno=new ArrayList<>();
    RecyclerView recyclerView1;
    public static Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testpage);

        progressDialog = new ProgressDialog(this);
        recyclerView1=(RecyclerView)findViewById(R.id.testRecycle);
        button=(Button)findViewById(R.id.submit);
        recyclerView1.setLayoutManager(new LinearLayoutManager(testpage.this));
        new MyTask().execute();


        Firebase.setAndroidContext(getApplicationContext());
        fb=new Firebase(url);

        testItemAdapter ta=new testItemAdapter(R.layout.aaa_testpagecard,testadap);

        recyclerView1.setAdapter(ta);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    progressDialog.setMessage("Submitting your answers...");
                    progressDialog.show();
                    new Task().execute();
            }
        });
    }

    public class MyTask extends AsyncTask<String, Integer, String>{


        @Override
        protected String doInBackground(String... params) {
            System.out.println("bow"+dummy.getName()+dummy.getCount1());
             String cu=dummy.getKey();
            System.out.println("bow"+cu);
            SharedPreferences sharedP=getSharedPreferences("data",MODE_PRIVATE);

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
        protected String doInBackground(String... params)
        {
            fb=new Firebase(url);
            int count = 0;
            SharedPreferences sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
            String dep=sharedPreferences.getString("department",null);
            String yea=sharedPreferences.getString("year",null);
            String reg=sharedPreferences.getString("regnum",null);
            System.out.println("Size is "+dummy.getQno());
            for(int i=0;i<dummy.getQno().size();i++){
                Answer answ=new Answer();
                answ.setChosedans(dummy.getAns().get(i));
                System.out.println("bow"+dummy.getAns().get(i)+dummy.getQno().get(i));
                fb.child("Atest").child(dep).child(yea).child(reg).child(dummy.getKey()).child(dummy.getQno().get(i)).setValue(answ);
                System.out.println("bow:  upload");
                result resu=new result();
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
            startActivity(new Intent(testpage.this, MainActivity.class));
            finish();
            progressDialog.dismiss();

            return null;
        }
    }

}
