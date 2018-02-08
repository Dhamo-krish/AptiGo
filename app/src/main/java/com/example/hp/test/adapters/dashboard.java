package com.example.hp.test.adapters;

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

import com.example.hp.test.R;
import com.example.hp.test.StarTest;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by HP on 9/12/2017.
 */

public class dashboard extends Fragment {
    public ArrayList<dashadapter> dashboards=new ArrayList<dashadapter>();
    private  RecyclerView.Adapter myadapter;
    RecyclerView recyclerdash;
    dashitemadapter dashitemadapter1;
    Firebase fb,fb1;
    String url="https://image-a519f.firebaseio.com/";
    ArrayList<String> resultnodes=new ArrayList<String>();

    public dashboard() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);
        fb1 = new Firebase(url);
        new MyTask().execute();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aaa_dashboard,container,false);
        recyclerdash=(RecyclerView)view.findViewById(R.id.recyclerdash);
        recyclerdash.setLayoutManager(new LinearLayoutManager(view.getContext()));

//        new MyTask().execute();
//        dashboards.add(new dashadapter("Data Structure","","12/8/17","1 hours",2));
//        dashboards.add(new dashadapter("Algorithms","","12/8/17","2 hours",3));
//        dashboards.add(new dashadapter("Os","","12/8/17","3 hours",2));
//        dashboards.add(new dashadapter("DBMS","","12/8/17","4 hours",5));
//
        dashitemadapter1=new dashitemadapter(R.layout.dashboardcard,dashboards);
        recyclerdash.setAdapter(dashitemadapter1);
        return view;

    }
    @Override
    public void onResume()
    {
        super.onResume();
        dashitemadapter1.setOnItemClickListener(new dashitemadapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
               //Toast.makeText(v.getContext(), "Working", Toast.LENGTH_SHORT).show();
                dummy.setName(dashboards.get(position).getTestCata());
                dummy.setCount1(dashboards.get(position).getNq());
                dummy.setTdur(dashboards.get(position).getTestdura());
                dummy.setBd(dashboards.get(position).getTestdate());
                dummy.setBt(dashboards.get(position).getTestdes());
                String k=dummy.getBd()+"@"+dummy.getBt()+"@"+dummy.getTdur()+"@"+dummy.getName()+"@"+dummy.getCount1();
                dummy.setKey(k);
                getFragmentManager().beginTransaction().replace(R.id.frame_container,new StarTest()).addToBackStack(null).commit();


            }
        });
    }
    public class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            String d=sharedPreferences.getString("department",null);
            String y=sharedPreferences.getString("year",null);
            String reg=sharedPreferences.getString("regnum",null);
            System.out.println("ONE ONE ONE");
            fb.child("Result").child(d).child(y).child(reg).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        String keyvalue=child.getKey();
                        resultnodes.add(keyvalue);
                        System.out.println("hari"+resultnodes);
                    }
                    new MyTask2().execute();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            return null;
        }
    }
    public class MyTask2 extends AsyncTask<String, Integer , String> {

        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            String d=sharedPreferences.getString("department",null);
            String y=sharedPreferences.getString("year",null);
            String reg=sharedPreferences.getString("regnum",null);
            System.out.println("2 2 2 2 2");
            fb1.child("Test").child(d).child(y).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        String name=child.getKey();
//                        dummy.setName(child.getKey());
                        String[] ss=name.split("@");
                        System.out.println("bowwww "+ss.length);
                        dashboards.add(new dashadapter(ss[3],ss[1],ss[0],ss[2],Integer.parseInt(ss[4])));
                    }
                    dashitemadapter1=new dashitemadapter(R.layout.dashboardcard,dashboards);
                    recyclerdash.setAdapter(dashitemadapter1);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }
}
