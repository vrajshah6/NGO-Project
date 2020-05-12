package com.example.dell.ngoproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.dell.ngoproject.adapter.AdapterForViewReports;

import java.util.ArrayList;
import java.util.List;

class ViewReportFragment extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ModelClassForViewReports>listitem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        listitem=new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            ModelClassForViewReports mr=new ModelClassForViewReports("heading "+(i+1),"hello");
            listitem.add(mr);
        }
        adapter=new AdapterForViewReports(listitem,this);
        recyclerView.setAdapter(adapter);
    }
}
