package com.example.dell.ngoproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class DonorProfileFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    ImageView ngoLogo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View rootView =inflater.inflate(R.layout.fragment_donor_profile, container, false);
        mappingWidgets(rootView);
        addListners();

        return rootView;


    }

    private void mappingWidgets(View rootView)
    {
        ngoLogo=rootView.findViewById(R.id.ngoLogo);
    }

    private void addListners()
    {
        ngoLogo.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {


    }
}
