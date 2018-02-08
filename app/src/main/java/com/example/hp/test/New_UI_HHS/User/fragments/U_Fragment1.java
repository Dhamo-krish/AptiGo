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

import com.example.hp.test.R;
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

public class U_Fragment1 extends Fragment {

    public ArrayList<dashadapter> dashboards=new ArrayList<dashadapter>();
    private  RecyclerView.Adapter myadapter;
    RecyclerView recyclerdash;
    dashitemadapter dashitemadapter1;
    Firebase fb,fb1;
    String url="https://image-a519f.firebaseio.com/";
    ArrayList<String> resultNodes = new ArrayList<String>();
    SharedPreferences sharedPreferences;
    String d,y,reg;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_fragment_1, container, false);
        Firebase.setAndroidContext(getActivity());
        fb=new Firebase(url);
        fb1=new Firebase(url);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Fetching Tests...");

        sharedPreferences=getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        d=sharedPreferences.getString("department",null);
        y=sharedPreferences.getString("year",null);
        reg = sharedPreferences.getString("regnum",null);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerdash=(RecyclerView)view.findViewById(R.id.recyclerdash);
        recyclerdash.setLayoutManager(new LinearLayoutManager(getActivity()));

        dashboards.clear();
        progressDialog.show();
        new MyTask().execute();
//        dashboards.add(new dashadapter("Data Structure","","12/8/17","1 hours",2));
//        dashboards.add(new dashadapter("Algorithms","","12/8/17","2 hours",3));
//        dashboards.add(new dashadapter("Os","","12/8/17","3 hours",2));
//        dashboards.add(new dashadapter("DBMS","","12/8/17","4 hours",5));
//
        dashitemadapter1=new dashitemadapter(R.layout.dashboardcard,dashboards);
        recyclerdash.setAdapter(dashitemadapter1);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        dashitemadapter1.setOnItemClickListener(new dashitemadapter.MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
                //Toast.makeText(v.getContext(), "Working", Toast.LENGTH_SHORT).show();

                final iOSDialog iOSDialog = new iOSDialog(getActivity());
                iOSDialog.setTitle("Confirmation");
                iOSDialog.setSubtitle("Are you sure you want to attend this test?");
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
                        dummy.setName(dashboards.get(position).getTestCata());
                        dummy.setCount1(dashboards.get(position).getNq());
                        dummy.setTdur(dashboards.get(position).getTestdura());
                        dummy.setBd(dashboards.get(position).getTestdate());
                        dummy.setBt(dashboards.get(position).getTestdes());
                        String k=dummy.getBd()+"@"+dummy.getBt()+"@"+dummy.getTdur()+"@"+dummy.getName()+"@"+dummy.getCount1();
                        dummy.setKey(k);
                        getFragmentManager().beginTransaction().replace(R.id.container,new Fragment1_1()).addToBackStack(null).commit();
                        iOSDialog.dismiss();
                    }
                });
                iOSDialog.show();

            }
        });
    }
    public class MyTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {


            fb.child("Result").child(d).child(y).child(reg).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child:dataSnapshot.getChildren())
                    {
                        String value = child.getKey();
                        resultNodes.add(value);

                    }
                    System.out.println("Result --> "+resultNodes);
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

            fb.child("Test").child(d).child(y).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int i;
                    Boolean flag=true;
                    for(DataSnapshot child:dataSnapshot.getChildren()){
                        String name=child.getKey();
//                        dummy.setName(child.getKey());
                        for(i=0;i<resultNodes.size();i++)
                        {
                            if(name.equals(resultNodes.get(i))) {
                                flag = false;
                            }
                        }
                        if(flag)
                        {
                            String[] ss=name.split("@");
                            dashboards.add(new dashadapter(ss[3],ss[1],ss[0],ss[2],Integer.parseInt(ss[4])));
                        }
                        else
                        {
                            flag=true;
                        }
                    }
                    progressDialog.dismiss();
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
