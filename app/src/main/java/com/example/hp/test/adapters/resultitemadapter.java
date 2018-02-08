package com.example.hp.test.adapters;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.test.R;

import java.util.ArrayList;

/**
 * Created by HP on 9/12/2017.
 */

public class resultitemadapter extends RecyclerView.Adapter<resultitemadapter.ViewHolder>{
    private int listItemLayout;
    public ArrayList<String> names,reg,tot;
    public static RecyclerView.RecyclerListener re;
    int pos;

    private static MyClickListener myClickListener;
    public resultitemadapter(int layoutId, ArrayList<String> names,ArrayList<String> reg,ArrayList<String> tot) {
        listItemLayout = layoutId;
        this.names=names;
        this.reg=reg;
        this.tot=tot;
    }

    @Override
    public resultitemadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(listItemLayout, parent, false);
        final ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(resultitemadapter.ViewHolder holder, final int position)
    {
        TextView result_name=holder.result_name;
        TextView result_reg=holder.result_reg;
        TextView result_tot=holder.result_tot;
        ImageView imageView=holder.imageView;
        LinearLayout tot_bg = holder.tot_bg;
        CardView cardView=holder.cardView;
        result_name.setText(names.get(position));
        result_reg.setText(reg.get(position));
        String str = tot.get(position);
        String numberOnly= str.replaceAll("[^0-9]", "");
        result_tot.setText(numberOnly+"/10");
        if(Integer.parseInt(numberOnly)>6 && Integer.parseInt(numberOnly)<10)
        {
            tot_bg.setBackgroundColor(Color.parseColor("#2ecc71"));
        }
        else if(Integer.parseInt(numberOnly)==10)
        {
            tot_bg.setBackgroundColor(Color.parseColor("#FFDF00"));
        }
        else
        {
            tot_bg.setBackgroundColor(Color.parseColor("#e74c3c"));
        }
        pos=position;
//        item5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(v.getContext(), ""+position+""+itemList.get(position).getTestCata(), Toast.LENGTH_SHORT).show();
//                myClickListener.onItemClick(position, v);
//            }
//        });



    }

    @Override
    public int getItemCount()
    {
        return names==null?0:names.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView result_name,result_reg,result_tot;
        public ImageView imageView;
        public CardView cardView;
        public LinearLayout tot_bg;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            View t=itemView;

            cardView=(CardView)itemView.findViewById(R.id.dashcard);
            result_name=(TextView)itemView.findViewById(R.id.result_name);
            result_reg=(TextView)itemView.findViewById(R.id.result_reg);
            result_tot=(TextView)itemView.findViewById(R.id.result_tot);
            imageView=(ImageView)itemView.findViewById(R.id.imageView);
            tot_bg = (LinearLayout)itemView.findViewById(R.id.tot_bg);
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
