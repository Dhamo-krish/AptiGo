package com.example.hp.test;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by HP on 9/16/2017.
 */

public class resultItemAdapter extends RecyclerView.Adapter<resultItemAdapter.ViewHolder>
{
    private int listItemLayout4;
    private ArrayList<resultAdapter> itemList4;
    private static MyClickListener myClickListener1;
    public resultItemAdapter(int layoutId, ArrayList<resultAdapter> itemList) {
        listItemLayout4 = layoutId;
        this.itemList4 = itemList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout4, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        TextView tn=holder.t;
        TextView td=holder.t1;
        tn.setText(itemList4.get(position).getAttend());
        td.setText(itemList4.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return itemList4==null?0:itemList4.size();
    }




    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView t,t1;
        public ViewHolder(View itemView) {
            super(itemView);
            View tf=itemView;
            tf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Working", Toast.LENGTH_SHORT).show();
                    myClickListener1.onItemClick1(getLayoutPosition(),v);
                }
            });
            t=(TextView)itemView.findViewById(R.id.testname);
            t1=(TextView)itemView.findViewById(R.id.testdate);

        }

        @Override
        public void onClick(View v) {

        }
    }
    public void setOnItemClickListener1(MyClickListener myClickListener) {
        this.myClickListener1 = myClickListener;
    }
    public interface MyClickListener {
        public void onItemClick1(int position, View v);
    }
}
