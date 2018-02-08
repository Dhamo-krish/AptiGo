package com.example.hp.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class login extends AppCompatActivity {
    TextView tv;
    Button b;
    EditText ed,ed1;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/" ;
    int c=0;
    String year,dept;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        b=(Button)findViewById(R.id.loginbutton);
        tv=(TextView)findViewById(R.id.textView);
        ed=(EditText)findViewById(R.id.userid);
        ed1=(EditText)findViewById(R.id.password);
        Firebase.setAndroidContext(this);
        fb=new Firebase(url);
        boolean islog=false;
        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        sharedEdit=sharedPreferences.edit();
        islog=sharedPreferences.getBoolean("islogged",false);
        if(islog){
            String t=sharedPreferences.getString("type","");
            if(t.equals("user")){
                startActivity(new Intent(login.this,MainActivity.class));
                finish();
            }
            if(t.equals("admin")){
                startActivity(new Intent(login.this,adminNav.class));
                finish();
            }

        }


        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,Signup.class));
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1=ed.getText().toString();
                String s2=ed1.getText().toString();
                c=c+1;
                if(c>=8){
                    startActivity(new Intent(login.this,adminNav.class));
                    c=0;
                    sharedEdit.putBoolean("islogged",true);
                    sharedEdit.putString("type","admin");
                    sharedEdit.commit();
                    Toast.makeText(login.this, "Your logged in as the admin" , Toast.LENGTH_SHORT).show();
                }
                else if(c==1&&(!ed.getText().toString().equals("")))
                {    String str=ed.getText().toString();
                    System.out.println("bow"+s1+s2);
                     String[] ss=str.split("[Tt]");
                     switch(ss[0]){
                         case "14" : year="Fourth";break;
                         case "15" : year="Third";break;
                         case "16" : year="Second";break;
                         case "17" : year="First";break;
                     }

                     System.out.println("bow"+ss[1].charAt(0));
                     switch (ss[1].charAt(0)){
                         case 'D': dept="CSE";break;
                         case 'C': dept="ECE";break;
                         case 'A': dept="CIVIL";break;
                         case 'H': dept="IT";break;
                     }

                    //Toast.makeText(MainActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(login.this,MainActivity.class));
                    System.out.println("bow"+dept+year);
                    url=url+"User/"+dept+"/"+year+"/"+str;
                    fb=new Firebase(url);
                    new MyTask().execute();

                }}
            });


        }
        public class MyTask extends AsyncTask<String , Integer, String> {

            @Override
            protected String doInBackground(String... params) {
                fb.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        try {

                            if (dataSnapshot.exists()) {
                                credUpdate credUpdate = dataSnapshot.getValue(credUpdate.class);
                                String s1=ed.getText().toString();
                                String s2=ed1.getText().toString();
                                System.out.println("bow"+credUpdate.getRegnum()+credUpdate.getPass());
                                if (s1.equals(credUpdate.getRegnum())&&(s2.equals(credUpdate.getPass()))) {
                                    Intent intent = new Intent(login.this, MainActivity.class);
                                    sharedEdit.putBoolean("islogged",true);
                                    sharedEdit.putString("type","user");
                                    sharedEdit.putString("regnum",credUpdate.getRegnum());
                                    sharedEdit.putString("pass",credUpdate.getPass());
                                    sharedEdit.putString("department",credUpdate.getDepartment());
                                    sharedEdit.putString("year",credUpdate.getUyear());
                                    sharedEdit.putString("name",credUpdate.getUsername());
                                    sharedEdit.putString("email",credUpdate.getMailid());
                                    sharedEdit.commit();
                                    startActivity(intent);
                                    finish();

                                } else {
                                    Toast.makeText(login.this, "Invalid Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(login.this, "Invalid Username", Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (Exception e){
                            System.out.println(e);
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
