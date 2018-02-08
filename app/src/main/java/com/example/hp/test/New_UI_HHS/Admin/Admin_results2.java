package com.example.hp.test.New_UI_HHS.Admin;

import android.app.Fragment;
import android.app.ProgressDialog;
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

import com.example.hp.test.New_UI_HHS.User.fragments.TestFragment;
import com.example.hp.test.R;
import com.example.hp.test.adapters.dashitemadapter;
import com.example.hp.test.adapters.dummydummy;
import com.example.hp.test.adapters.resultadapter;
import com.example.hp.test.adapters.resultitemadapter;
import com.example.hp.test.credUpdate;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;


/**
 * Created by hhs
 */

public class Admin_results2 extends Fragment {

    ProgressDialog progressDialog;
    View view;
    RecyclerView recyclerView;
    String dept,year;
    Firebase fb,fb2,fb3,fb4;
    String base_url = "https://image-a519f.firebaseio.com/";
    ArrayList<String> final_result = new ArrayList<>();
    ArrayList<String> final_result_names = new ArrayList<>();
    ArrayList<String> final_result_total = new ArrayList<>();

    resultitemadapter resultitemadapter1;
    int i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.aaa_admin_results2, container, false);

        progressDialog = new ProgressDialog(getActivity());

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dept = dummydummy.dept;
        year = dummydummy.year;
        Firebase.setAndroidContext(getActivity());
        System.out.println("Dept and year is "+dept+" "+year);
        progressDialog.setMessage("Fetching Results...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        new MyTask().execute();

        resultitemadapter1=new resultitemadapter(R.layout.result_card,final_result_names,final_result,final_result_total);
        recyclerView.setAdapter(resultitemadapter1);

        return v;
    }

    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... params) {
            final String reg_nodes = base_url+"Result"+"/"+dept+"/"+year;
            fb = new Firebase(reg_nodes);
            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        final String key = child.getKey();
                        //System.out.println("ADR2 "+key);
                        final String new_url =reg_nodes+"/"+key;
                        fb2 = new Firebase(new_url);
                        fb2.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for(DataSnapshot child_new : dataSnapshot.getChildren())
                                {
                                    String key2 = child_new.getKey();
                                    //System.out.println("ADR2 child of "+key+"is "+key2);
                                    String key_split[] = key2.split("@");
                                    //System.out.println("----> "+key_split[3]);
                                    if(dummydummy.testname.equals(key_split[3]))
                                    {
                                        System.out.println("DumDum "+dummydummy.testname);
                                        final_result.add(key);
                                        System.out.println("Amellesh is "+final_result);
                                        String total = String.valueOf(child_new.child("Total").getValue());
                                        final_result_total.add(total);
                                        System.out.println("Total is "+final_result_total);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError) {

                            }
                        });

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



    private class MyTask2 extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... params) {
            final String url = base_url+"User"+"/"+dept+"/"+year+"/";
            fb4 = new Firebase(url);
            i=0;
            fb4.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (i = 0; i < final_result.size(); i++)
                    {
                        for(DataSnapshot child : dataSnapshot.getChildren())
                        {
                            if(child.getKey().equals(final_result.get(i)))
                            {
                                String value_name = child.child("username").getValue().toString();
                                final_result_names.add(value_name);
                                System.out.println("Final result names is "+final_result_names);
                            }
                        }
                    }

                    resultitemadapter1=new resultitemadapter(R.layout.result_card,final_result_names,final_result,final_result_total);
                    recyclerView.setAdapter(resultitemadapter1);
                    progressDialog.dismiss();




                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return null;
        }
    }


}
