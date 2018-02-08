package com.example.hp.test.New_UI_HHS.User.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
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

import com.example.hp.test.R;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.resultAdapter;
import com.example.hp.test.resultItemAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;


/**
 * Created by hhs
 */

public class U_Fragment2 extends Fragment {

    RecyclerView recyclerView2;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/";
    TextView t,t1,t2,t3,t4;
    SharedPreferences sharedPreferences;
    resultItemAdapter resultItemAdapter1;
    private ArrayList<resultAdapter> resultAdapters1=new ArrayList<resultAdapter>();
    ArrayList<String> kk=new ArrayList<>();
    private String dep;
    private String yea;
    private String reg;
    private String name;
    private String mail;

    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_fragment_2, container, false);

        t=(TextView)v.findViewById(R.id.name);
        t1=(TextView)v.findViewById(R.id.email);
        t2=(TextView)v.findViewById(R.id.regn);
        t3=(TextView)v.findViewById(R.id.syear);
        t4=(TextView)v.findViewById(R.id.depart);

        progressDialog = new ProgressDialog(getActivity());

        recyclerView2=(RecyclerView)v.findViewById(R.id.testresult);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getActivity()));
        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);
        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        dep=sharedPreferences.getString("department",null);
        yea=sharedPreferences.getString("year",null);
        reg=sharedPreferences.getString("regnum",null);
        name=sharedPreferences.getString("name",null);
        mail=sharedPreferences.getString("email",null);
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
        resultAdapters1.clear();
        progressDialog.setMessage("Fetching details...");
        new MyTask().execute();
        resultItemAdapter1=new resultItemAdapter(R.layout.protestcard,resultAdapters1);
        recyclerView2.setAdapter(resultItemAdapter1);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onResume() {
        super.onResume();
        resultItemAdapter1.setOnItemClickListener1(new resultItemAdapter.MyClickListener() {
            @Override
            public void onItemClick1(int position, View v) {
                dummy.setZz(position);
                getFragmentManager().beginTransaction().replace(R.id.container,new Fragment2_1()).addToBackStack(null).commit();
            }
        });
    }


    public class MyTask extends AsyncTask<String, Integer, String>
    {

        @Override
        protected String doInBackground(String... params) {

            fb.child("Result").child(dep).child(yea).child(reg).addListenerForSingleValueEvent(new ValueEventListener() {
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
