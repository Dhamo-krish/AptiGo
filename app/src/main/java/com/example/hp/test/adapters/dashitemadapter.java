package com.example.hp.test.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.R;

import java.util.ArrayList;

/**
 * Created by HP on 9/12/2017.
 */

public class dashitemadapter extends RecyclerView.Adapter<dashitemadapter.ViewHolder>{
    private int listItemLayout;
    public ArrayList<dashadapter> itemList;
    public static RecyclerView.RecyclerListener re;
    int pos;

    private static MyClickListener myClickListener;
    public dashitemadapter(int layoutId, ArrayList<dashadapter> itemList) {
        listItemLayout = layoutId;
        this.itemList = itemList;
    }

    @Override
    public dashitemadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(dashitemadapter.ViewHolder holder, final int position)
    {
        TextView item1=holder.testcat;
        TextView item2=holder.testdur;
        TextView item3=holder.testdate;
        TextView item4=holder.noq;
        CardView item5=holder.card;
        item1.setText(itemList.get(position).getTestCata());
        item2.setText((itemList.get(position).getTestdura())+" minutes");
        item3.setText(itemList.get(position).getTestdate());
        pos=position;
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), ""+position+""+itemList.get(position).getTestCata(), Toast.LENGTH_SHORT).show();
                myClickListener.onItemClick(position, v);
            }
        });
        String n=String.valueOf(itemList.get(position).getNq());
        item4.setText(n);

    }

    @Override
    public int getItemCount()
    {
        return itemList==null?0:itemList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView testcat,testdur,testdate,noq;
        public CardView card;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            View t=itemView;

//            t.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    myClickListener.onItemClick(getLayoutPosition(), v);
//
//
//                }
//            });
            card=(CardView)itemView.findViewById(R.id.dashcard);
            testcat=(TextView)itemView.findViewById(R.id.textView);
            testdur=(TextView)itemView.findViewById(R.id.textView3);
            testdate=(TextView)itemView.findViewById(R.id.textdate);
            noq=(TextView)itemView.findViewById(R.id.textView8);

        }

        @Override
        public void onClick(View v) {


        }
    }
    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
