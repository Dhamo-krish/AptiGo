package com.example.hp.test.New_UI_HHS.Admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.New_UI_HHS.Login.New_login;
import com.example.hp.test.New_UI_HHS.User.pages.User_Home;
import com.example.hp.test.R;
import com.example.hp.test.adapters.TestAdapter;
import com.example.hp.test.adapters.dummy;
import com.example.hp.test.adminFrag.questionsadap;
import com.firebase.client.Firebase;
import com.gdacciaro.iOSDialog.iOSDialog;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;
import static com.example.hp.test.New_UI_HHS.Admin.XYValues.flag;

/**
 * Created by HP on 9/12/2017.
 */

public class Admin_questions_itemadapter extends RecyclerView.Adapter<Admin_questions_itemadapter.ViewHolder>
{
    private int listItemLayout;
    private ArrayList<questionsadap> itemList;
    public static Firebase fb;
    public static String url="https://image-a519f.firebaseio.com/";
    public static int cc=0;
    public static TestAdapter testAdapter;
    public static String tname;
    public static RadioButton op1;
    public static RadioButton op2;
    public static RadioButton op3;
    public static RadioButton op4;
    public static RadioButton ans;
    public static EditText opt1;
    public static EditText opt2;
    public static EditText opt3;
    public static EditText opt4;
    public static EditText q;
    public static Button confirm;
    public static Button upload;
    public static TextView tv;
    public static RadioGroup radioGroup;
    View view;
    public int count = 0;

    ArrayList<String> arraylist_question = new ArrayList<>();
    ArrayList<String> arraylist_option1 = new ArrayList<>();
    ArrayList<String> arraylist_option2 = new ArrayList<>();
    ArrayList<String> arraylist_option3 = new ArrayList<>();
    ArrayList<String> arraylist_option4 = new ArrayList<>();
    ArrayList<Integer> arraylist_number = new ArrayList<>();


    public Admin_questions_itemadapter(int layoutId, ArrayList<questionsadap> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }
    

    @Override
    public Admin_questions_itemadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(Admin_questions_itemadapter.ViewHolder holder, int position)
    {
        TextView item1=tv;
       // item1.setText(String.valueOf(++count));
        holder.setIsRecyclable(false);
        System.out.println("---------------> "+position);

        //item1.setText(itemList.get(position).getCt());
        if(flag) {
            tv.setText(String.valueOf(arraylist_number.get(position)-2));
            q.setText(arraylist_question.get(position));
            opt1.setText(arraylist_option1.get(position));
            opt2.setText(arraylist_option2.get(position));
            opt3.setText(arraylist_option3.get(position));
            opt4.setText(arraylist_option4.get(position));
        }
    }

    @Override
    public int getItemCount()
    {
        return itemList==null?0:itemList.size();
    }



    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView)
        {
            super(itemView);
            tv=(TextView)itemView.findViewById(R.id.number);
            q=(EditText)itemView.findViewById(R.id.question);
            op1=(RadioButton)itemView.findViewById(R.id.radioButton4);
            op2=(RadioButton)itemView.findViewById(R.id.radioButton3);
            op3=(RadioButton)itemView.findViewById(R.id.radioButton2);
            op4=(RadioButton)itemView.findViewById(R.id.radioButton);
            opt1=(EditText)itemView.findViewById(R.id.opt1);
            opt2=(EditText)itemView.findViewById(R.id.opt2);
            opt3=(EditText)itemView.findViewById(R.id.opt3);
            opt4=(EditText)itemView.findViewById(R.id.opt4);
            radioGroup=(RadioGroup)itemView.findViewById(R.id.radiogroup);
            confirm=(Button)itemView.findViewById(R.id.confirm);
            upload=(Button)itemView.findViewById(R.id.upload);
            Firebase.setAndroidContext(itemView.getContext());
            fb=new Firebase(url);
//            confirm.setOnClickListener(this);
//            upload.setOnClickListener(this);

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upload.setVisibility(View.VISIBLE);
                    if(!opt1.getText().toString().equals(""))
                    {
                        op1.setText(opt1.getText().toString());
                    }
                    if(!opt2.getText().toString().equals(""))
                    {
                        op2.setText(opt2.getText().toString());
                    }
                    if(!opt3.getText().toString().equals(""))
                    {
                        op3.setText(opt3.getText().toString());

                    }
                    if(!opt4.getText().toString().equals(""))
                    {
                        op4.setText(opt4.getText().toString());
                    }

                    opt1.setText("");
                    opt2.setText("");
                    opt3.setText("");
                    opt4.setText("");
                    Toast.makeText(view.getContext(), "Confirmed", Toast.LENGTH_SHORT).show();
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int chid=radioGroup.getCheckedRadioButtonId();
                    if(chid!=-1){

                        final iOSDialog iOSDialog = new iOSDialog(view.getContext());
                        iOSDialog.setTitle("Upload Confirmation");
                        iOSDialog.setSubtitle("Are you sure you want to upload?");
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

                                System.out.println("bow"+tv.getText());
                                testAdapter=new TestAdapter();
                                testAdapter.setQuestion(q.getText().toString());
                                testAdapter.setC1(op1.getText().toString());
                                testAdapter.setC2(op2.getText().toString());
                                testAdapter.setC3(op3.getText().toString());
                                testAdapter.setC4(op4.getText().toString());


                                System.out.println("bowhhs"+chid);
                                ans=(RadioButton)view.findViewById(chid);
                                testAdapter.setCorrect(ans.getText().toString());



                                Toast.makeText(view.getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                confirm.setVisibility(View.GONE);

                                fb.child("Test").child(dummy.getTdept()).child(dummy.getTyear()).child(tname).child(tv.getText().toString()).setValue(testAdapter);
                                cc=cc+1;
                                dummy.setC(cc);
                                iOSDialog.dismiss();
                            }
                        });
                        iOSDialog.show();

                    }
                    else{
                        Toast.makeText(view.getContext(), "Select the Correct Answer...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            tname= dummy.getBd()+"@"+dummy.getBt()+"@"+dummy.getTdur()+"@"+dummy.getName()+"@"+dummy.getCount1();
            System.out.println("bow"+tname);


        }


//        @Override
//        public void onClick(final View v) {
//            int id=v.getId();
//            if(id==R.id.confirm)
//            {
//
//
//            }
//            else if(id==R.id.upload)
//            {
//
//            }
//
//        }

    }

    public void setValues() {
        if (flag) {

            arraylist_question = XYValues.getList_question();
            arraylist_option1 = XYValues.getList_option1();
            arraylist_option2 = XYValues.getList_option2();
            arraylist_option3 = XYValues.getList_option3();
            arraylist_option4 = XYValues.getList_option4();
            arraylist_number = XYValues.getList_number();
            System.out.println("Value retd "+arraylist_question);
            System.out.println("Value op1 "+arraylist_option1);
            System.out.println("Value op2 "+arraylist_option2);
            System.out.println("Value op3 "+arraylist_option3);
            System.out.println("Value op4 "+arraylist_option4);


        }
    }


}
