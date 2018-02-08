package com.example.hp.test.New_UI_HHS.User.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;

import com.example.hp.test.R;
import com.example.hp.test.adapters.OverallResult;
import com.example.hp.test.adapters.TestAdapter;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adapters.result;
import com.example.hp.test.resultAdapter;
import com.example.hp.test.resultItemAdapter;
import com.example.hp.test.viewansAdapter;
import com.example.hp.test.viewansItemAdapter;
import com.example.hp.test.viewanswer;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by hhs
 */

public class Fragment2_1 extends Fragment {

    ProgressDialog progressDialog;

    RecyclerView rc3;
    Firebase fb;
    SharedPreferences sharedPreferences;
    String url="https://image-a519f.firebaseio.com/";
    int kkk= dummy.getZz();
    String kk=dummy.getCkey().get(kkk);
    viewansItemAdapter via2;
    private ArrayList<viewansAdapter> vaA1=new ArrayList<viewansAdapter>();
    ArrayList<TestAdapter> lll=new ArrayList<TestAdapter>();
    ArrayList<result> rrr=new ArrayList<result>();
    int j=0,k=0;
    TextView scored;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_viewanswer, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching test results...");

        scored = (TextView) v.findViewById(R.id.scored);
        rc3=(RecyclerView)v.findViewById(R.id.viewansRecycler);
        rc3.setLayoutManager(new LinearLayoutManager(getActivity()));
        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);

        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        progressDialog.show();
        new MyTask().execute();

        via2=new viewansItemAdapter(R.layout.testresultcard,vaA1);
        rc3.setAdapter(via2);

        return v;
    }

    public class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            System.out.println("bow"+kkk+kk);
            String[] sssss=kk.split("@");
            final long[] kkkkk = new long[1];

            final String dep=sharedPreferences.getString("department",null);
            final String yea=sharedPreferences.getString("year",null);
            final String reg=sharedPreferences.getString("regnum",null);

            fb.child("Test").child(dep).child(yea).child(kk).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    long ch=dataSnapshot.getChildrenCount();
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        TestAdapter testAdapter=child.getValue(TestAdapter.class);
                        lll.add(testAdapter);
                    }
                    j=1;
                    kkkkk[0] =ch;
                    System.out.println("qqbow"+lll.get(0).getQuestion());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });System.out.println("jkbow"+j+k);
            fb.child("Result").child(dep).child(yea).child(reg).child(kk).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        System.out.println("bow"+child.getKey());
                        if(child.getKey().toString().equals("Total")){
                            OverallResult overall=child.getValue(OverallResult.class);
                            dummy.setOt(overall.getTot());
                            System.out.println("Total is "+overall.getTot());
                            scored.setText("You Scored : "+overall.getTot()+"/10");
                        }
                        else{

                            result re=child.getValue(result.class);
                            rrr.add(re);
                        }

                    }
                    System.out.println("bowbow"+rrr);
                    for(int i = 0; i< lll.size(); i++){
                        System.out.println("sticky"+i);
                        String q=lll.get(i).getQuestion();
                        String cc1=lll.get(i).getC1();
                        String cc2=lll.get(i).getC2();
                        String cc3=lll.get(i).getC3();
                        String cc4=lll.get(i).getC4();
                        String cor=lll.get(i).getCorrect();
                        String cha=rrr.get(i).getCa();
                        boolean e=rrr.get(i).isVal();

                        vaA1.add(new viewansAdapter(q,cc1,cc2,cc3,cc4,cor,cha,e));
                        via2=new viewansItemAdapter(R.layout.testresultcard,vaA1);
                        rc3.setAdapter(via2);
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });







            return null;
        }
    }

}
