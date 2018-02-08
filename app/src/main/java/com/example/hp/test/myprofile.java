package com.example.hp.test;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.test.adapters.dummy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HP on 9/16/2017.
 */

public class myprofile extends Fragment {
    RecyclerView recyclerView2;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/";
    TextView t,t1,t2,t3,t4;
    SharedPreferences sharedPreferences;
    resultItemAdapter resultItemAdapter1;
    private ArrayList<resultAdapter> resultAdapters1=new ArrayList<resultAdapter>();
    ArrayList<String> kk=new ArrayList<>();
    public myprofile() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        resultItemAdapter1.setOnItemClickListener1(new resultItemAdapter.MyClickListener() {
            @Override
            public void onItemClick1(int position, View v) {
                dummy.setZz(position);
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new viewanswer())
                        .addToBackStack(null).commit();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aaa_myprofile,container,false);
        //cardView=(CardView)view.findViewById(R.id.)
        t=(TextView)view.findViewById(R.id.name);
        t1=(TextView)view.findViewById(R.id.email);
        t2=(TextView)view.findViewById(R.id.regn);
        t3=(TextView)view.findViewById(R.id.syear);
        t4=(TextView)view.findViewById(R.id.depart);

        recyclerView2=(RecyclerView)view.findViewById(R.id.testresult);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);
        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String dep=sharedPreferences.getString("department",null);
        String yea=sharedPreferences.getString("year",null);
        String reg=sharedPreferences.getString("regnum",null);
        String name=sharedPreferences.getString("name",null);
        String mail=sharedPreferences.getString("email",null);
        t.setText(name);
        t1.setText(mail);
        t2.setText(reg);
        t4.setText(dep);
        t3.setText(yea);
//        resultAdapters1.add(new resultAdapter("",""));
//        resultAdapters1.add(new resultAdapter("",""));
//        resultAdapters1.add(new resultAdapter("",""));
//        resultAdapters1.add(new resultAdapter("",""));
//        resultAdapters1.add(new resultAdapter("",""));
        new MyTask().execute();
        resultItemAdapter1=new resultItemAdapter(R.layout.protestcard,resultAdapters1);
        recyclerView2.setAdapter(resultItemAdapter1);
        return view;
    }


    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String d=sharedPreferences.getString("department",null);
            String y=sharedPreferences.getString("year",null);
            String r=sharedPreferences.getString("regnum",null);
            fb.child("Result").child(d).child(y).child(r).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        System.out.println("bow"+child.getKey());
                        String str=child.getKey();
                        kk.add(str);
                        String[] ss=str.split("@");

                        dummy.setCkey(kk);
                        resultAdapters1.add(new resultAdapter(ss[3],ss[0]));
                        resultItemAdapter resultItemAdapter1=new resultItemAdapter(R.layout.protestcard,resultAdapters1);
                        recyclerView2.setAdapter(resultItemAdapter1);


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

