package com.example.dell.ngoproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.ngoproject.ModelClassForViewReports;
import com.example.dell.ngoproject.R;

import java.util.List;

public class AdapterForViewReports extends RecyclerView.Adapter<AdapterForViewReports.ViewHolder>
{

    List<ModelClassForViewReports> listitem;
    Context context;

    public AdapterForViewReports(List<ModelClassForViewReports> listitem, Context context) {
        this.listitem = listitem;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ModelClassForViewReports mr=listitem.get(position);

        holder.heading.setText(mr.getHeading());
        holder.description.setText(mr.getDescription());



    }

    @Override
    public int getItemCount() {
        return listitem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading,description;


        public ViewHolder(View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.tvHeading);
            description=itemView.findViewById(R.id.tvDescription);

        }
    }
}