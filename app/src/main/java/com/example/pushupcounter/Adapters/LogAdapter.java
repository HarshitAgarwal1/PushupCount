package com.example.pushupcounter.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pushupcounter.R;

import java.util.ArrayList;


public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogViewholder> {

    private LayoutInflater layoutInflater ;
    private ArrayList<Entry> list ;


    public LogAdapter(Context context, ArrayList<Entry> data) {
        this.layoutInflater = LayoutInflater.from(context);
        this.list = data ;
    }

    @NonNull
    @Override
    public LogViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_item, parent, false) ;
        return new LogViewholder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LogViewholder holder, int position) {

        int size = list.size();
        int i ;

        for (i = 0 ; i < size ; i++) {

            String slno, counts ;
            Integer pushup ;
            Float timer ;

            Entry dataarray = list.get(i);
            slno = dataarray.getSlno() ;
            counts = dataarray.getPushcount();
            pushup = dataarray.getCounter();
            timer = dataarray.getChronometercount();

            holder.serialno.setText(slno);
            holder.count.setText(counts);
            if(timer > 60) {
                holder.chronometercount.setText(pushup + " in " + timer.toString() + " mins");
            }else{
                holder.chronometercount.setText(pushup + " in " + timer.toString() + " secs");
            }

        }
    }

    @Override
    public int getItemCount() {
        return list.size() ;

    }

    public class LogViewholder extends RecyclerView.ViewHolder{
         TextView  serialno , count, chronometercount ;

        public LogViewholder (@NonNull View itemView) {
            super(itemView);

            serialno = itemView.findViewById(R.id.entryno);
            count = itemView.findViewById(R.id.count);
            chronometercount = itemView.findViewById(R.id.chronometertime) ;


        }
    }

}
