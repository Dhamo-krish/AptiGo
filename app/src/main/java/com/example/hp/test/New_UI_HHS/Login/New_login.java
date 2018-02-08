package com.example.hp.test.New_UI_HHS.Login;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.Admin.Admin_Home;
import com.example.hp.test.New_UI_HHS.Admin.Admin_login;
import com.example.hp.test.New_UI_HHS.Register.New_register;
import com.example.hp.test.New_UI_HHS.User.pages.User_Home;
import com.example.hp.test.New_UI_HHS.User.pages.User_home2;
import com.example.hp.test.R;
import com.example.hp.test.adminNav;
import com.example.hp.test.credUpdate;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.gdacciaro.iOSDialog.iOSDialog;

/**
 * Created by Saravanan Saru on 9/18/2017.
 */

public class New_login extends AppCompatActivity {

    TextView tv;
    Button loginbutton;
    EditText ed,ed1;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/" ;
    int c=0;
    String year,dept;
    FloatingActionButton fab;

    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor sharedEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_login);

        loginbutton=(Button)findViewById(R.id.loginbutton);
        ed=(EditText)findViewById(R.id.userid);
        ed1=(EditText)findViewById(R.id.password);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setExitTransition(null);
                getWindow().setEnterTransition(null);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =
                            ActivityOptions.makeSceneTransitionAnimation(New_login.this, fab, fab.getTransitionName());
                    startActivity(new Intent(New_login.this, New_register.class), options.toBundle());
                } else {
                    startActivity(new Intent(New_login.this, New_register.class));
                }
            }
        });

        Firebase.setAndroidContext(this);
        fb=new Firebase(url);

        sharedPreferences=getSharedPreferences("data",MODE_PRIVATE);
        sharedEdit=sharedPreferences.edit();
        final String stats = sharedPreferences.getString("islogin","");

        if(stats.equals("yes"))
        {
            String t=sharedPreferences.getString("type","");
            if(t.equals("user")){
                startActivity(new Intent(New_login.this,User_Home.class));
                finish();
            }
            if(t.equals("admin")){
                startActivity(new Intent(New_login.this,Admin_Home.class));
                finish();
            }
        }

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                String s1=ed.getText().toString();
                String s2=ed1.getText().toString();
                c=c+1;
                if(c>=8){
                    startActivity(new Intent(New_login.this,Admin_login.class));
                    c=0;
                    sharedEdit.putString("type","admin");
                    sharedEdit.commit();
                    Toast.makeText(New_login.this, "Admin" , Toast.LENGTH_SHORT).show();
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
                    System.out.println("URL ---> "+url);
                    fb=new Firebase(url);
                    progressDialog.show();
                    new MyTask().execute();

                }

            }
        });
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public class MyTask extends AsyncTask<String , Integer, String> {

        @Override
        protected String doInBackground(String... params) {


            fb.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    credUpdate credUpdate = dataSnapshot.getValue(credUpdate.class);

                    if(credUpdate!=null)
                    {
                    if(((ed.getText().toString().equals(credUpdate.getRegnum()))&&(ed1.getText().toString().equals(credUpdate.getPass())))&&(credUpdate.getRegnum()!=null))
                    {
                        sharedEdit.putString("islogin","yes");
                        sharedEdit.putString("type","user");
                        sharedEdit.putString("regnum",credUpdate.getRegnum());
                        sharedEdit.putString("pass",credUpdate.getPass());
                        sharedEdit.putString("department",credUpdate.getDepartment());
                        sharedEdit.putString("year",credUpdate.getUyear());
                        sharedEdit.putString("name",credUpdate.getUsername());
                        sharedEdit.putString("email",credUpdate.getMailid());
                        sharedEdit.commit();
                        System.out.println("LOGGED IN");
                        Explode explode = new Explode();
                        explode.setDuration(500);

                        getWindow().setExitTransition(explode);
                        getWindow().setEnterTransition(explode);
                        ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(New_login.this);
                        //if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.LOLLIPOP)
                        //{
                            Intent i2 = new Intent(New_login.this,User_Home.class);
                            startActivity(i2, oc2.toBundle());
                            New_login.this.finish();
                        //}
//                        else
//                        {
//                            startActivity(new Intent(New_login.this,User_home2.class));
//                            New_login.this.finish();
//                        }

                        //startActivity(intent);

                    }
                    else
                    {
                        final iOSDialog iOSDialog = new iOSDialog(New_login.this);
                        iOSDialog.setTitle("Invalid Login");
                        iOSDialog.setSubtitle("Please enter correct credentials");
                        iOSDialog.setPositiveLabel("Dismiss");
                        iOSDialog.setBoldPositiveLabel(true);

                        iOSDialog.setPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                iOSDialog.dismiss();
                            }
                        });
                        iOSDialog.show();
                    }}
                    else
                    {
                        final iOSDialog iOSDialog = new iOSDialog(New_login.this);
                        iOSDialog.setTitle("Invalid Login");
                        iOSDialog.setSubtitle("Please enter correct credentials");
                        iOSDialog.setPositiveLabel("Dismiss");
                        iOSDialog.setBoldPositiveLabel(true);

                        iOSDialog.setPositiveListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                iOSDialog.dismiss();
                            }
                        });
                        iOSDialog.show();
                    }

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
