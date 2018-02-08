package com.example.hp.test.New_UI_HHS.Admin;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.User.fragments.Fragment1_1;
import com.example.hp.test.R;
import com.example.hp.test.adapters.dashadapter;
import com.example.hp.test.adapters.dashitemadapter;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adapters.dummydummy;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;

/**
 * Created by HP on 9/15/2017.
 */

public class Admin_results extends Fragment {

    ProgressDialog progressDialog;
    EditText dept,year;
    Button get_results;
    RecyclerView recyclerView;
    String dept_string,year_string;
    String base_url = "https://image-a519f.firebaseio.com/";
    Firebase fb;
    ArrayList<dashadapter> result_nodes = new ArrayList<>();
    dashitemadapter dashitemadapter1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.aaa_admin_results,container,false);

        progressDialog = new ProgressDialog(getActivity());

        dept = (EditText) view.findViewById(R.id.dept);
        year = (EditText) view.findViewById(R.id.year);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        get_results = (Button) view.findViewById(R.id.get_results);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Year of Study");

                // add a list
                final String[] years = {"First", "Second", "Third", "Fourth"};
                builder.setItems(years, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                year.setText(years[0]);
                                break;
                            case 1:
                                year.setText(years[1]);
                                break;
                            case 2:
                                year.setText(years[2]);
                                break;
                            case 3:
                                year.setText(years[3]);
                                break;

                        }
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Department");

                // add a list
                final String[] depts = {"CSE", "ECE", "IT", "CIVIL", "MECH"};
                builder.setItems(depts, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                dept.setText(depts[0]);
                                break;
                            case 1:
                                dept.setText(depts[1]);
                                break;
                            case 2:
                                dept.setText(depts[2]);
                                break;
                            case 3:
                                dept.setText(depts[3]);
                                break;
                            case 4:
                                dept.setText(depts[4]);
                                break;

                        }
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Firebase.setAndroidContext(getActivity());

        get_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dept_string = dept.getText().toString();
                year_string = year.getText().toString();
                if(dept_string.equals("") || year_string.equals(""))
                    Toast.makeText(getActivity(),"Empty Fields",Toast.LENGTH_SHORT).show();
                else
                {
                    progressDialog.setMessage("Fetching details...");
                    progressDialog.show();
                    progressDialog.setCancelable(false);
                    result_nodes.clear();
                    new MyTask().execute();
                }

            }
        });

        dashitemadapter1=new dashitemadapter(R.layout.dashboardcard,result_nodes);
        recyclerView.setAdapter(dashitemadapter1);

        return view;
    }


    private class MyTask extends AsyncTask<String,Integer,String>
    {
        @Override
        protected String doInBackground(String... params) {
            String test_url = base_url+"Test"+"/"+dept_string+"/"+year_string;
            fb = new Firebase(test_url);
            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot child : dataSnapshot.getChildren())
                    {
                        String name = child.getKey();
                        System.out.println("Keys are "+name);
                        String name_split[] = name.split("@");
                        result_nodes.add(new dashadapter(name_split[3],name_split[1],name_split[0],name_split[2],Integer.parseInt(name_split[4])));
                    }
                    dashitemadapter1=new dashitemadapter(R.layout.dashboardcard,result_nodes);
                    recyclerView.setAdapter(dashitemadapter1);
                    progressDialog.dismiss();
                }


                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            return null;
        }
    }

    @Override
    public void onResume()
    {
        super.onResume();
        dashitemadapter1.setOnItemClickListener(new dashitemadapter.MyClickListener() {
            @Override
            public void onItemClick(final int position, View v) {
//                Toast.makeText(getActivity(), "Working", Toast.LENGTH_SHORT).show();
                dummydummy.dept = dept_string;
                dummydummy.year = year_string;
                dummydummy.testname = result_nodes.get(position).getTestCata();
                //System.out.println("Dummy "+dummydummy.testname);
                getFragmentManager().beginTransaction().replace(R.id.frame,new Admin_results2()).addToBackStack(null).commit();


            }
        });
    }
}
