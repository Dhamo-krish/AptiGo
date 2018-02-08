package com.example.hp.test.New_UI_HHS.Register;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.fragments.Fragment1_1;
import com.example.hp.test.R;
import com.example.hp.test.Signup;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.credUpdate;
import com.example.hp.test.login;
import com.firebase.client.Firebase;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;

/**
 * Created by Saravanan Saru on 9/18/2017.
 */

public class New_register extends AppCompatActivity {

    ArrayList<String> d,y;
    ArrayAdapter<String> de,ye;
    Button register;
    Firebase fb;
    String url="https://image-a519f.firebaseio.com/" ;
    EditText fullname,regno,email,password,repeatpassword,year,dept;
    String n,r,yea,dep,mail;
    FloatingActionButton fab;
    CardView cvAdd;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaa_register);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait...");

        regno = (EditText) findViewById(R.id.regno);
        password = (EditText) findViewById(R.id.password);
        repeatpassword = (EditText) findViewById(R.id.repeatpassword);
        email = (EditText) findViewById(R.id.email);
        fullname = (EditText) findViewById(R.id.fullname);
        year=(EditText)findViewById(R.id.year);
        dept=(EditText)findViewById(R.id.dept);
        register=(Button)findViewById(R.id.register);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cvAdd = (CardView) findViewById(R.id.cv_add);
        Firebase.setAndroidContext(this);

        fb=new Firebase(url);

        year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // setup the alert builder
                AlertDialog.Builder builder = new AlertDialog.Builder(New_register.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(New_register.this);
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




//        d=new ArrayList<>();
//        d.add("Department");
//        d.add("CSE");
//        d.add("ECE");
//        d.add("IT");
//        d.add("CIVIL");
//        d.add("MECH");
//        y=new ArrayList<>();
//        y.add("Year");
//        y.add("First");
//        y.add("Second");
//        y.add("Third");
//        y.add("Fourth");


//        de=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,d);
//        de.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dept.setAdapter(de);
//        ye=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,y);
//        ye.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        year.setAdapter(ye);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();


                String name = regno.getText().toString();
                String pass = password.getText().toString();
                String rep = repeatpassword.getText().toString();
                String mail = email.getText().toString();
                String fname = fullname.getText().toString();
                String yearrr = year.getText().toString();
                String depp = dept.getText().toString();
                if(((pass.equals(rep)) && (pass.length()!=0 && rep.length()!=0)&&(name.length()<10)) && ((mail.contains("@"))&&(mail.contains(".")) && (mail.contains("com"))) && fname.length()>5 && !depp.equals("") && !yearrr.equals("")){
                    final iOSDialog iOSDialog = new iOSDialog(New_register.this);
                    iOSDialog.setTitle("Confirmation");
                    iOSDialog.setSubtitle("Register with the given details?");
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
                            progressDialog.show();
                            new MyTask().execute();
                            startActivity(new Intent(New_register.this, New_login.class));
                            New_register.this.finish();
                            iOSDialog.dismiss();
                        }
                    });
                    iOSDialog.show();
//                    String n=fullname.getText().toString();
//                    String r=regno.getText().toString();
//                    String yea=year.getText().toString();
//                    String dep=dept.getText().toString();
//                    String mail=email.getText().toString();

                }
                else{
                    if(!(pass.equals(rep)))
                        Toast.makeText(New_register.this,"Password Mismatch",Toast.LENGTH_SHORT).show();
                    if(pass.length()>5)
                        Toast.makeText(New_register.this,"Password length is small",Toast.LENGTH_SHORT).show();
                    if(name.length()==0)
                        Toast.makeText(New_register.this,"Enter valid Register Number",Toast.LENGTH_SHORT).show();
                    if(!(mail.contains("@")) || !(mail.contains(".")) || !(mail.contains("com")))
                        Toast.makeText(New_register.this,"Invalid Email Address",Toast.LENGTH_SHORT).show();
                    if(fname.length()<5)
                        Toast.makeText(New_register.this,"Enter more characters for Full Name",Toast.LENGTH_SHORT).show();
                    if(depp.equals(""))
                        Toast.makeText(New_register.this,"Choose Department",Toast.LENGTH_SHORT).show();
                    if(yearrr.equals(""))
                        Toast.makeText(New_register.this,"Choose Year",Toast.LENGTH_SHORT).show();
                }

            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
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

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                New_register.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

    public class MyTask extends AsyncTask<String , Integer, String> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            credUpdate credupdate=new credUpdate();
            credupdate.setUsername(fullname.getText().toString());
            credupdate.setDepartment(dept.getText().toString());
            credupdate.setRegnum(regno.getText().toString());
            credupdate.setUyear(year.getText().toString());
            credupdate.setMailid(email.getText().toString());
            credupdate.setPass(password.getText().toString());
            fb.child("User").child(credupdate.getDepartment()).child(credupdate.getUyear()).child(credupdate.getRegnum()).setValue(credupdate);
            //Toast.makeText(New_register.this,"Account Created",Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return null;
        }
    }
}
