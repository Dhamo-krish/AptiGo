package com.example.hp.test.TestPage;

import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.hp.test.R;
import com.example.hp.test.adapters.dummy;

import java.util.ArrayList;

import static com.example.hp.test.adapters.dummy.ans;
import static com.example.hp.test.adapters.dummy.qno;

/**
 * Created by HP on 9/13/2017.
 */

public class testItemAdapter extends RecyclerView.Adapter<testItemAdapter.ViewHolder>
{
    private int listItemLayout3;
    public int pos,chid;
    RadioGroup rg;
    public int c=0;
    public static  RadioButton as;
    public static View rview;
    public ArrayList<testpageadapter> itemList3;



    public testItemAdapter(int layoutId, ArrayList<testpageadapter> itemList) {
        listItemLayout3 = layoutId;
        this.itemList3 = itemList;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout3, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);

        if(c==0){
            ans=new ArrayList<>();
            qno=new ArrayList<>();
            for(int i=0;i<dummy.getCount1();i++){

                ans.add("");
                qno.add("");
            }
            c=1;
        }

        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        TextView item1=holder.tv1;
        TextView item2=holder.tv2;
        TextView item3=holder.tv4;

        final RadioButton op1=holder.o1;
        final RadioButton  op2=holder.o2;
        final RadioButton op3=holder.o3;
        final RadioButton op4= holder.o4;
          rg=holder.radioGroup;


        item1.setText(""+(position+1));
        item2.setText(itemList3.get(position).getQues());
        item3.setText(itemList3.get(position).getAnswer());
        op1.setText(itemList3.get(position).getOption1());
        op2.setText(itemList3.get(position).getOption2());
        op3.setText(itemList3.get(position).getOption3());
        op4.setText(itemList3.get(position).getOption4());
        pos=position+1;
        chid=rg.getCheckedRadioButtonId();
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.option1:
                          String ssw=op1.getText().toString();
                        //String s=as.getText().toString();
//                        testpage test=new testpage();
//
//                        test.answers1.set(pos,as.getText().toString());
//                        test.questionno.set(pos,String.valueOf(pos));
//                        System.out.println("bow"+test.answers1+test.questionno);
                        ans.set(position,ssw);
                        qno.set(position,String.valueOf(position+1));
                        System.out.println("bow"+ans+qno);
                        break;
                    case R.id.option2:
                        String ssw1=op2.getText().toString();
                        ans.set(position,ssw1);
                        qno.set(position,String.valueOf(position+1));
                        System.out.println("bow"+ans+qno);
                        break;
                    case R.id.option3:
                        String ssw2=op3.getText().toString();
                        ans.set(position,ssw2);
                        qno.set(position,String.valueOf(position+1));
                        System.out.println("bow"+ans+qno);
                        break;
                    case R.id.option4:
                        String ssw3=op4.getText().toString();
                        ans.set(position,ssw3);
                        qno.set(position,String.valueOf(position+1));
                        System.out.println("bow"+ans+qno);
                        break;
                }
            }
        });
        //new MyTask().execute();
    }

    @Override
    public int getItemCount() {
        return itemList3==null?0:itemList3.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tv1,tv2,tv3,tv4;
        public RadioButton o1,o2,o3,o4;

        public RadioGroup radioGroup;
        public ViewHolder(View itemView)
        {
            super(itemView);
            tv1=(TextView)itemView.findViewById(R.id.numbercount);
            tv2=(TextView)itemView.findViewById(R.id.questcontent);
            tv3=(TextView)itemView.findViewById(R.id.view);
            tv4=(TextView)itemView.findViewById(R.id.answer);
            o1=(RadioButton)itemView.findViewById(R.id.option1);
            o2=(RadioButton)itemView.findViewById(R.id.option2);
            o3=(RadioButton)itemView.findViewById(R.id.option3);
            o4=(RadioButton)itemView.findViewById(R.id.option4);
            radioGroup=(RadioGroup)itemView.findViewById(R.id.radiogroup);
            rview=itemView;
            tv3.setOnClickListener(this);
            radioGroup.setOnClickListener(this);

        }


        @Override
        public void onClick(View v)
        {
            String s=null;
            int id=v.getId();
            if(id==R.id.view)
            {
                tv4.setVisibility(View.VISIBLE);
                int chid=radioGroup.getCheckedRadioButtonId();


                as=(RadioButton)itemView.findViewById(chid);
                s=as.getText().toString();
                Toast.makeText(itemView.getContext(), ""+s, Toast.LENGTH_SHORT).show();
                //Toast.makeText(v.getContext(), ""+s, Toast.LENGTH_SHORT).show();
            }
            System.out.println("bow"+id+R.id.view);

            if(id==R.id.radiogroup){

//                     testpage test=new testpage();
//                     test.answers.add(s);
//                     System.out.println("answers"+test.answers);

            }
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
