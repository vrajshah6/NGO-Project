package com.example.dell.ngoproject;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.ngoproject.service.APIManager;
import com.example.dell.ngoproject.utility.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class CatagoriesFragment extends Fragment implements View.OnClickListener {

  CardView cv1,cv2,cv3,cv4,cv5,cv6;
  TextView education,children,health,woman,animal,food;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView =inflater.inflate(R.layout.fragment_catagories, container, false);

         mappingWidgets(rootView);
        addListners();

        return rootView;


    }
     private void mappingWidgets(View rootView)
    {
        cv1=rootView.findViewById(R.id.cv1);
        cv2=rootView.findViewById(R.id.cv2);
        cv3=rootView.findViewById(R.id.cv3);
        cv4=rootView.findViewById(R.id.cv4);
        cv5=rootView.findViewById(R.id.cv5);
        cv6=rootView.findViewById(R.id.cv6);

        education=rootView.findViewById(R.id.education);
        children=rootView.findViewById(R.id.children);
        health=rootView.findViewById(R.id.health);
        woman=rootView.findViewById(R.id.woman);
        food=rootView.findViewById(R.id.food);
        animal=rootView.findViewById(R.id.animal);

    }

    private void addListners()
    {
        cv1.setOnClickListener(this);
        cv2.setOnClickListener(this);
        cv3.setOnClickListener(this);
        cv4.setOnClickListener(this);
        cv5.setOnClickListener(this);
        cv6.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.cv1:
                categoryclick("education");
                //Toast.makeText(getActivity(),"Clicked",Toast.LENGTH_LONG).show();
                break;

            case R.id.cv2:
                categoryclick("children");
                break;

            case R.id.cv3:
                categoryclick("health");
                break;

            case R.id.cv4:
                categoryclick("woman");
                break;

            case R.id.cv5:
                categoryclick("animal");
                break;

            case R.id.cv6:
                categoryclick("food");
                break;

        }
    }

     void categoryclick(String cat) {

         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(Constant.BASE_URL) // Bas URL
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();

         // Sending param
         Map<String, String> params = new HashMap<>();
         params.put("category", cat);


         // Initializing APIManager
         APIManager api = retrofit.create(APIManager.class);

         //TODO: Note: Replace 'getDetails(param)' API method for every new API here
         Call<Map<String, Object>> call = api.category(params);

         final ProgressDialog progressDialog = new ProgressDialog(getActivity());
         progressDialog.setMessage("Please Wait...");
         progressDialog.show();

         call.enqueue(new Callback<Map<String, Object>>() {
             @Override
             public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                 if (progressDialog != null && progressDialog.isShowing()) {
                     progressDialog.dismiss();
                 }
//ahiya sudhi karelu che response ave ene get karvanu che and new activity ma listing karine e show karvanu che
                 try {
                     // Read response as follow
                     if (response != null && response.body() != null) {

                     }
                     else {

                     }


             } catch(Exception e)

             {

             }

         }
             @Override
             public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                 if (progressDialog != null && progressDialog.isShowing()) {
                     progressDialog.dismiss();
                 }


                 Log.d("Error", "onFailure: " + t.getMessage());
             }
         });
     }





}
