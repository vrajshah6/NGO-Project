package com.example.dell.ngoproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class DonorRegisterActivity extends Activity
{

    EditText edtNewDonorEmail,edtNewDonorPassword;
    Button btnDonorRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_register);

        mappingWidgets();
        btnDonorRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(checkValidation())
                {
                    callRegister();

                } }

        });
    }

    private void callRegister() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();
        params.put("email", edtNewDonorEmail.getText().toString().trim());
        params.put("pass", edtNewDonorPassword.getText().toString().trim());

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        //TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String,Object>> call = api.register(params);

        final ProgressDialog progressDialog = new ProgressDialog(DonorRegisterActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow
                    if (response != null && response.body() != null) {

                        Log.d("Error", "onResponse: body: " + response.body());
                        Map<String,Object> map = response.body();
                        Gson gson=new Gson();
                        String jsonString=gson.toJson(map);

                        JsonObject content = gson.fromJson(jsonString,JsonObject.class);


                        if(!response.body().toString().equals("false")){
                            // save session
                            Toast.makeText(DonorRegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            Toast.makeText(DonorRegisterActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(DonorRegisterActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(DonorRegisterActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(DonorRegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }

    private boolean checkValidation()
    {
        boolean isValid=true;
        if(edtNewDonorEmail.getText().toString().trim().isEmpty())
        {
            edtNewDonorEmail.setError("Enter Email Id");
            isValid=false;
        }
        if(edtNewDonorPassword.getText().toString().trim().isEmpty())
        {
            edtNewDonorPassword.setError("Enter Password");
            isValid=false;
        }

        return isValid;
    }

    private void mappingWidgets()
    {
        edtNewDonorEmail=findViewById(R.id.edtNewDonorEmail);
        edtNewDonorPassword=findViewById(R.id.edtNewDonorPassword);

        btnDonorRegister=findViewById(R.id.btnDonorRegister);
    }
}
