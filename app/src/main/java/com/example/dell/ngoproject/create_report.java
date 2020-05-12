package com.example.dell.ngoproject;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class create_report extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =inflater.inflate(R.layout.fragment_create_report, container, false);

        mappingWidgets(rootView);
        addListener();

        return rootView;
    }

    private void mappingWidgets(View rootView) {
    }

    private void addListener(){

    }

}
