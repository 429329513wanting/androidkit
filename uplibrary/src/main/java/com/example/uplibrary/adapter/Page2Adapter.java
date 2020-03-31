package com.example.uplibrary.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uplibrary.R;

import java.util.List;

public class Page2Adapter extends RecyclerView.Adapter<Page2Adapter.MyViewHolder> {

    private List<String>datas;

    public Page2Adapter(List<String>datas){

        this.datas = datas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.titTv.setText(this.datas.get(position));

    }

    @Override
    public int getItemCount() {

        return this.datas.size();
    }

     public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titTv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titTv = itemView.findViewById(R.id.title_tv);
        }
    }
}
