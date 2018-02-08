package com.example.hp.test;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.adapters.dashitemadapter;

import java.util.ArrayList;

/**
 * Created by HP on 9/16/2017.
 */

public class viewansItemAdapter extends RecyclerView.Adapter<viewansItemAdapter.ViewHolder> {
    private int listItemLayout5;
    private ArrayList<viewansAdapter> itemList5;

    public viewansItemAdapter(int layoutId, ArrayList<viewansAdapter> itemList) {
        listItemLayout5 = layoutId;
        this.itemList5 = itemList;
        setHasStableIds(true);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout5, parent, false);
        final ViewHolder myViewHolder3 = new ViewHolder(view);
        return myViewHolder3;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView item1=holder.tv1;
        TextView item2=holder.tv2;
        TextView item3=holder.tv4;
        TextView item4=holder.yourans;
        TextView item5=holder.crctans;
        TextView item6=holder.option11;
        TextView item7=holder.option22;
        TextView item8=holder.option33;
        TextView item9=holder.option44;



        final RadioButton op1=holder.o1;
        final RadioButton op2=holder.o2;
        final RadioButton op3=holder.o3;
        final RadioButton op4= holder.o4;



        item1.setText(""+(position+1));
        item2.setText(itemList5.get(position).getQues());
        System.out.println("bow"+itemList5.get(position).getQues());
        //item3.setText(itemList5.get(position).getCorrect());
        item4.setText("Your answer : "+itemList5.get(position).getChosenans());
        item5.setText("Correct answer : "+itemList5.get(position).getCorrect());
        op1.setText(itemList5.get(position).getCh1());
        op2.setText(itemList5.get(position).getCh2());
        op3.setText(itemList5.get(position).getCh3());
        op4.setText(itemList5.get(position).getCh4());

        if(itemList5.get(position).getChosenans().equals(itemList5.get(position).getCorrect()))
        {
            if(op1.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item6.setVisibility(View.VISIBLE);
                item6.setText("Correct");
                item6.setTextColor(Color.parseColor("#2ecc71"));
            }
            else if(op2.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item7.setVisibility(View.VISIBLE);
                item7.setText("Correct");
                item7.setTextColor(Color.parseColor("#2ecc71"));
            }
            else if(op3.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item8.setVisibility(View.VISIBLE);
                item8.setText("Correct");
                item8.setTextColor(Color.parseColor("#2ecc71"));
            }
            else if(op4.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item9.setVisibility(View.VISIBLE);
                item9.setText("Correct");
                item9.setTextColor(Color.parseColor("#2ecc71"));
            }
        }
        else
        {
            if(op1.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item6.setVisibility(View.VISIBLE);
                item6.setText("Incorrect");
                item6.setTextColor(Color.parseColor("#e74c3c"));
            }
            else if(op2.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item7.setVisibility(View.VISIBLE);
                item7.setText("Incorrect");
                item7.setTextColor(R.color.red);
                item7.setTextColor(Color.parseColor("#e74c3c"));
            }
            else if(op3.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item8.setVisibility(View.VISIBLE);
                item8.setText("Incorrect");
                item8.setTextColor(R.color.red);
                item8.setTextColor(Color.parseColor("#e74c3c"));
            }
            else if(op4.getText().toString().equals(itemList5.get(position).getChosenans()))
            {
                item9.setVisibility(View.VISIBLE);
                item9.setText("Incorrect");
                item9.setTextColor(R.color.red);
                item9.setTextColor(Color.parseColor("#e74c3c"));
            }
        }

//        pos=position+1;

    }

    @Override
    public int getItemCount() {
        return itemList5==null?0:itemList5.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView tv1,tv2,tv3,tv4,yourans,crctans,option11,option22,option33,option44;
        public RadioButton o1,o2,o3,o4;

        public ViewHolder(View itemView)
        {
            super(itemView);
            tv1=(TextView)itemView.findViewById(R.id.numbercount);
            tv2=(TextView)itemView.findViewById(R.id.questcontent);
            tv3=(TextView)itemView.findViewById(R.id.view);
            tv4=(TextView)itemView.findViewById(R.id.answer);
            yourans=(TextView)itemView.findViewById(R.id.yourans);
            crctans=(TextView)itemView.findViewById(R.id.crctans);
            option11=(TextView)itemView.findViewById(R.id.option11);
            option22=(TextView)itemView.findViewById(R.id.option22);
            option33=(TextView)itemView.findViewById(R.id.option33);
            option44=(TextView)itemView.findViewById(R.id.option44);

            option11.setVisibility(View.INVISIBLE);
            option22.setVisibility(View.INVISIBLE);
            option33.setVisibility(View.INVISIBLE);
            option44.setVisibility(View.INVISIBLE);

            o1=(RadioButton)itemView.findViewById(R.id.option1);
            o2=(RadioButton)itemView.findViewById(R.id.option2);
            o3=(RadioButton)itemView.findViewById(R.id.option3);
            o4=(RadioButton)itemView.findViewById(R.id.option4);

        }

        @Override
        public void onClick(View v) {

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
