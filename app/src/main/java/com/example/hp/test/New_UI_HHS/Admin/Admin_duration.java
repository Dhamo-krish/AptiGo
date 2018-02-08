package com.example.hp.test.New_UI_HHS.Admin;


import android.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hp.test.R;
import com.example.hp.test.adapters.dummy;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;
import java.util.List;

/**
 * Created by HP on 9/15/2017.
 */

public class Admin_duration extends Fragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    Button next;
    EditText dept,year,date,time;
    List<String> d,h,dt,yea;
    ArrayAdapter<String> dd,hh,de,ye;
    View view;
    String dur="";
    int duration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.aaa_duration,container,false);
        next=(Button)view.findViewById(R.id.next);
        date=(EditText)view.findViewById(R.id.date);
        time=(EditText)view.findViewById(R.id.time);
        dept=(EditText)view.findViewById(R.id.dept);
        year=(EditText)view.findViewById(R.id.year);
//        hours = (EditText) view.findViewById(R.id.days);
//        mins = (EditText) view.findViewById(R.id.hours);
        next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(date.getText().toString().equals("")&& time.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Select the date and time...", Toast.LENGTH_SHORT).show();
                }
                else{
                   // dur=hours.getText().toString()+"h "+mins.getText().toString()+"m";
                    //System.out.println("Duration is "+dur);

                    String times = time.getText().toString(); //mm:ss
                    String[] units = times.split(":"); //will break the string up into an array
                    int hours = Integer.parseInt(units[0]);
                    int minutes = Integer.parseInt(units[1]);
                    int seconds = Integer.parseInt(units[2]);
                    duration = hours*60 + minutes + seconds/60;
                    System.out.println("DDDD "+duration);//add up our values
                    Toast.makeText(getActivity(),duration+" minutes",Toast.LENGTH_SHORT).show();

                }
                if(dept.getText().toString().equals("")|| year.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Empty Fields", Toast.LENGTH_SHORT).show();
                }
//                else if(hours.getText().toString().equals(""))
//                {
//                  if(mins.getText().toString().equals(""))
//                  {
//                    Toast.makeText(v.getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
//
//                 }
//                else{
//                    Toast.makeText(v.getContext(), "Empty Fields", Toast.LENGTH_SHORT).show();
//                 }
//                }
                else
                {
                    dummy.setTdept(dept.getText().toString());
                    dummy.setTyear(year.getText().toString());
                    dummy.setTdur(String.valueOf(duration));
                    dummy.setBd(date.getText().toString());
                    dummy.setBt(time.getText().toString());
                    getFragmentManager().beginTransaction().replace(R.id.frame,new Admin_test_name()).commit();
                }

            }
        });

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

//        hours.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Hours");
//
//                // add a list
//                final String[] hoursss = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//                builder.setItems(hoursss, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                hours.setText(hoursss[0]);
//                                break;
//                            case 1:
//                                hours.setText(hoursss[1]);
//                                break;
//                            case 2:
//                                hours.setText(hoursss[2]);
//                                break;
//                            case 3:
//                                hours.setText(hoursss[3]);
//                                break;
//                            case 4:
//                                hours.setText(hoursss[4]);
//                                break;
//                            case 5:
//                                hours.setText(hoursss[5]);
//                                break;
//                            case 6:
//                                hours.setText(hoursss[6]);
//                                break;
//                            case 7:
//                                hours.setText(hoursss[7]);
//                                break;
//                            case 8:
//                                hours.setText(hoursss[8]);
//                                break;
//                            case 9:
//                                hours.setText(hoursss[9]);
//                                break;
//
//                        }
//                    }
//                });
//
//                // create and show the alert dialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
//
//        mins.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                builder.setTitle("Minutes");
//
//                // add a list
//                final String[] minsss = {"1", "2", "3", "4", "5", "6", "7", "8", "9"};
//                builder.setItems(minsss, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which) {
//                            case 0:
//                                mins.setText(minsss[0]);
//                                break;
//                            case 1:
//                                mins.setText(minsss[1]);
//                                break;
//                            case 2:
//                                mins.setText(minsss[2]);
//                                break;
//                            case 3:
//                                mins.setText(minsss[3]);
//                                break;
//                            case 4:
//                                mins.setText(minsss[4]);
//                                break;
//                            case 5:
//                                mins.setText(minsss[5]);
//                                break;
//                            case 6:
//                                mins.setText(minsss[6]);
//                                break;
//                            case 7:
//                                mins.setText(minsss[7]);
//                                break;
//                            case 8:
//                                mins.setText(minsss[8]);
//                                break;
//                            case 9:
//                                mins.setText(minsss[9]);
//                                break;
//
//                        }
//                    }
//                });
//
//                // create and show the alert dialog
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });


//        dd=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,d);
//        dd.setDropDownViewResource(android.R.layout.simple_list_item_checked);
//        day.setAdapter(dd);
//        hh=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,h);
//        hh.setDropDownViewResource(android.R.layout.simple_list_item_checked);
//        hour.setAdapter(hh);
//        dt=new ArrayList<>();
//         dt.add("Department");
//        dt.add("CSE");
//        dt.add("ECE");
//        dt.add("IT");
//        dt.add("CIVIL");
//        dt.add("MECH");
//        yea=new ArrayList<>();
//        yea.add("Year");
//        yea.add("First");
//        yea.add("Second");
//        yea.add("Third");
//        yea.add("Fourth");
//        de=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,dt);
//        de.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//        dep.setAdapter(de);
//        ye=new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_dropdown_item,yea);
//        ye.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
//        y.setAdapter(ye);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                DatePickerDialog datepickerdialog = DatePickerDialog.newInstance(
                        Admin_duration.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                datepickerdialog.show(getFragmentManager(),"DatePickerDialog");

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now=Calendar.getInstance();
                TimePickerDialog timePickerDialog=TimePickerDialog.newInstance(
                        Admin_duration.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true

                );
                timePickerDialog.show(getFragmentManager(),"TimePickerDialog");
            }
        });
        return view;
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date1=dayOfMonth+"-"+(++monthOfYear)+"-"+year;
        date.setText(date1);

    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hh=hourOfDay<10? "0"+hourOfDay : ""+hourOfDay;
        String mm=minute<10? "0"+minute : ""+minute;
        String sec=second<10? "0"+second :""+second;

        String time1 = hh +":"+mm+":"+sec;

        time.setText(time1);

    }
}
