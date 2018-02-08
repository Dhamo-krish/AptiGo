package com.example.hp.test;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.IntegerRes;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.ArrayList;

public class Signup extends AppCompatActivity {

    EditText name,reg,pas,cpas,email;
    Spinner year,dept;
    ArrayList<String> d,y;
    ArrayAdapter<String> de,ye;
    Button b;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/" ;
    ProgressDialog progressdialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=(EditText)findViewById(R.id.editText);
        reg=(EditText)findViewById(R.id.editText4);
        pas=(EditText)findViewById(R.id.editText6);
        cpas=(EditText)findViewById(R.id.editText7);
        email=(EditText)findViewById(R.id.Emailid);
        year=(Spinner)findViewById(R.id.year);
        dept=(Spinner)findViewById(R.id.spinner);
        b=(Button)findViewById(R.id.button2);
        Firebase.setAndroidContext(this);

        fb=new Firebase(url);


        d=new ArrayList<>();
        d.add("Department");
        d.add("CSE");
        d.add("ECE");
        d.add("IT");
        d.add("CIVIL");
        d.add("MECH");
        y=new ArrayList<>();
        y.add("Year");
        y.add("First");
        y.add("Second");
        y.add("Third");
        y.add("Fourth");
        de=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,d);
        de.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dept.setAdapter(de);
        ye=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,y);
        ye.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(ye);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=pas.getText().toString();
                String cp=cpas.getText().toString();
                if(p.equals(cp)){
                     new MyTask().execute();
                }
                else{
                    Toast.makeText(Signup.this, "Password Mismatch" , Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    public class MyTask extends AsyncTask<String , Integer, String>{

        @Override
        protected void onPreExecute() {
            progressdialog=new ProgressDialog(Signup.this);
            progressdialog.setMessage("Signing In....");
            progressdialog.setCancelable(false);
            progressdialog.show();


        }

        @Override
        protected String doInBackground(String... params) {
            String n=name.getText().toString();
            String r=reg.getText().toString();
            String yea=year.getSelectedItem().toString();
            String dep=dept.getSelectedItem().toString();
            String mail=email.getText().toString();
            credUpdate credupdate=new credUpdate();
            credupdate.setUsername(n);
            credupdate.setDepartment(dep);
            credupdate.setRegnum(r);
            credupdate.setUyear(yea);
            credupdate.setMailid(mail);
            credupdate.setPass(pas.getText().toString());
            fb.child("User").child(dep).child(yea).child(r).setValue(credupdate);
            progressdialog.dismiss();
            startActivity(new Intent(Signup.this, login.class));
            finish();

            return null;
        }
    }
}
