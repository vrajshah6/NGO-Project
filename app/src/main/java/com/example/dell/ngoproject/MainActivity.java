package com.example.dell.ngoproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.ngoproject.service.APIManager;
import com.example.dell.ngoproject.utility.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import java.util.HashMap;
import java.util.Map;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends Activity implements View.OnClickListener
{
    MaterialEditText edtEmail,edtPassword;
    Button btnLogin;
    TextView tvRegisterDonor,tvRegisterNGO;
    Session session;
    RadioGroup loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check session
        session = new Session(this);
        if (!session.getEmail().isEmpty()) {
            Intent UserHome = new Intent(getApplicationContext(), UserHomeActivity.class);
            startActivity(UserHome);
            finish();
        }
        else{
            mappingWidgets();
            addListeners();
        }
    }

    private void addListeners()
    {
        btnLogin.setOnClickListener(this);
        tvRegisterDonor.setOnClickListener(this);
        tvRegisterNGO.setOnClickListener(this);
    }
    private void mappingWidgets()
    {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterDonor = findViewById(R.id.tvRegisterDonor);
        tvRegisterNGO = findViewById(R.id.tvRegisterNGO);
        loginType = findViewById(R.id.rdgLoginType);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnLogin:

                if(checkValidation())
                {
                    int checkedRadioButtonId = loginType.getCheckedRadioButtonId();
                    switch (checkedRadioButtonId) {
                        case R.id.rdDonor:
                            callDonorLogin();
                            break;

                        case R.id.rdNGO:
                            callNGOLogin();
                            break;
                    }

                }
                break;

            case R.id.tvRegisterNGO:
                Intent RegNGO=new Intent(getApplicationContext(),NGORegisterActivity.class);
                startActivity(RegNGO);
                break;

            case R.id.tvRegisterDonor:
                Intent RegDonor=new Intent(getApplicationContext(),DonorRegisterActivity.class);
                startActivity(RegDonor);
                break;
            //DonorRegisterActivity.class
        }
    }

    private boolean checkValidation()
    {
        boolean isValid=true;
        if(edtEmail.getText().toString().trim().isEmpty())
        {
            edtEmail.setError("Enter Email Id");
            isValid=false;
        }
        if(edtPassword.getText().toString().trim().isEmpty())
        {
            edtPassword.setError("Enter Password");
            isValid=false;
        }
        return isValid;
    }


    void callDonorLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();
        params.put("email", edtEmail.getText().toString().trim());
        params.put("pass", edtPassword.getText().toString().trim());

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        //TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.login(params);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow
                    if (response != null && response.body() != null) {
                        //Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        Gson gson = new Gson();
                        JsonObject content = gson.fromJson(gson.toJson(response.body()),
                                JsonObject.class);

                        if(content.get("status").getAsInt() == Constant.API_STATUS_SUCCESS){
                            // save session
                            Log.d("NGO ID===", content.get("userId").getAsString());
                            session.setEmail(edtEmail.getText().toString().trim());
                            session.setUserType("Donor");
                            session.setUserId(content.get("userId").getAsInt());
                            Log.d("KunjalSony= " , "" + session.getUserId());
                            Toast.makeText(MainActivity.this, content.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            Intent UserHome = new Intent(getApplicationContext(), UserHomeActivity.class);
                            startActivity(UserHome);
                            finish();
                        }

                        else{
                            if (content.has("message")) {
                                Toast.makeText(MainActivity.this, content.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }

    void callNGOLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();
        params.put("email", edtEmail.getText().toString().trim());
        params.put("pass", edtPassword.getText().toString().trim());

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        //TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.ngoLogin(params);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    // Read response as follow
                    if (response != null && response.body() != null) {
                        //Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        Gson gson = new Gson();
                        JsonObject content = gson.fromJson(gson.toJson(response.body()),
                                JsonObject.class);

                        if(content.get("status").getAsInt() == Constant.API_STATUS_SUCCESS)

                            if(content.get("status").getAsInt() == Constant.API_STATUS_SUCCESS){
                                session.setEmail(edtEmail.getText().toString().trim());
                                session.setUserType("NGO");
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                Intent UserHome = new Intent(getApplicationContext(), UserHomeActivity.class);
                                startActivity(UserHome);
                                finish();
                            }

                            else{
                                Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }

                    } else {
                        Toast.makeText(MainActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "Error in reading response: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
