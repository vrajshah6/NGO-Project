package com.example.dell.ngoproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.dell.ngoproject.service.APIManager;
import com.example.dell.ngoproject.utility.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NGORegisterActivity extends Activity implements View.OnClickListener
{
    MaterialEditText edtNewNGOName,edtNewNGOEmail,edtNewNGOPassword,edtNewNGOReenterPassword,
                edtNewNGOAdminName,edtNewNGOOfficeLocation,edtNewNGOAbout,edtNewNGOMobileNo,edtNGOCatagory;

    RadioGroup rdgNewGovRegistered;
    RadioButton  rdNGOyes,rdNGOno;
    Button btnNGOnext,btnNGOback;
    LinearLayout form1,form2,form3;
    ImageView ngoLogo;
    Uri photoUri;

    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngoregister);

        mappingWidgets();
        addListners();
    }

    private void addListners()
    {
        btnNGOback.setOnClickListener(this);
        btnNGOnext.setOnClickListener(this);
        ngoLogo.setOnClickListener(this);
    }

    private void mappingWidgets()
    {
        edtNewNGOName=findViewById(R.id.edtNewNGOName);
        edtNewNGOEmail=findViewById(R.id.edtNewNGOEmail);
        edtNewNGOPassword=findViewById(R.id.edtNewNGOPassword);
        edtNewNGOReenterPassword=findViewById(R.id.edtNewNGOReenterPassword);
        edtNewNGOAdminName=findViewById(R.id.edtNewNGOAdminName);
        edtNewNGOOfficeLocation=findViewById(R.id.edtNewNGOOfficeLocation);
        edtNewNGOAbout=findViewById(R.id.edtNewNGOAbout);
        edtNewNGOMobileNo=findViewById(R.id.edtNewNGOMobileNo);
        edtNGOCatagory=findViewById(R.id.edtNGOCatagory);

        rdgNewGovRegistered=findViewById(R.id.rdgNewGovRegistered);

        rdNGOyes=findViewById(R.id.rdNGOyes);
        rdNGOno=findViewById(R.id.rdNGOno);

        btnNGOnext=findViewById(R.id.btnNGOnext);
        btnNGOback=findViewById(R.id.btnNGOback);

        form1=findViewById(R.id.form1);
        form2=findViewById(R.id.form2);
        form3=findViewById(R.id.form3);

        ngoLogo=findViewById(R.id.ngoLogo);

    }

    private boolean checkValidation1()
    {
        boolean isValid=true;

        if(edtNewNGOName.getText().toString().trim().isEmpty())
        {
            edtNewNGOName.setError("Enter name");
            isValid=false;
        }

        if(edtNewNGOEmail.getText().toString().trim().isEmpty())
        {
            edtNewNGOEmail.setError("Enter Email");
            isValid=false;
        }

        if(edtNewNGOPassword.getText().toString().trim().isEmpty())
        {
            edtNewNGOPassword.setError("Enter Password");
            isValid=false;
        }

        if(edtNewNGOReenterPassword.getText().toString().trim().isEmpty())
        {
            edtNewNGOReenterPassword.setError("Enter Password again");
            isValid=false;
        }

        return isValid;
    }

    private boolean checkValidation2()
    {
        boolean isValid=true;
        if(edtNewNGOAdminName.getText().toString().trim().isEmpty())
        {
            isValid=false;
            edtNewNGOAdminName.setError("Enter NGO Admin Name");
        }
        if( edtNewNGOOfficeLocation.getText().toString().trim().isEmpty())
        {
            edtNewNGOOfficeLocation.setError("Enter Location");
            isValid=false;
        }
        if(edtNewNGOAbout.getText().toString().trim().isEmpty())
        {
            edtNewNGOAbout.setError("Enter Data");
            isValid=false;
        }
        return isValid;
    }
    private boolean checkValidation3()
    {
        boolean isValid=true;
        if(edtNewNGOMobileNo.getText().toString().trim().isEmpty())
        {
            edtNewNGOMobileNo.setError("Enter Mobile No");
            isValid=false;
        }
        return isValid;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnNGOnext:

                count++;
                if(count==1)
                {
                    if(checkValidation1())
                    {
                        form1.setVisibility(View.GONE);
                        form2.setVisibility(View.VISIBLE);
                    }
                    else
                        count--;
                 }

                 if(count==2)
                 {
                     if (checkValidation2()) {
                         form2.setVisibility(View.GONE);
                         form3.setVisibility(View.VISIBLE);
                     }
                     else
                         count--;
                 }

                 if(count==3)
                 {
                     if (checkValidation3()) {
                         callRegister();
                     }
                 }

                break;

            case R.id.btnNGOback:
                count--;
                if(form1.getVisibility()==View.VISIBLE)
                {
                    finish();
                }
                else if(form2.getVisibility()==View.VISIBLE)
                {
                    form2.setVisibility(View.GONE);
                    form1.setVisibility(View.VISIBLE);
                }
                else if(form3.getVisibility()==View.VISIBLE)
                {
                    form3.setVisibility(View.GONE);
                    form2.setVisibility(View.VISIBLE);
                }

                break;

            case R.id.ngoLogo:
                checkAndroidVersion();
                break;

        }

    }


    public void checkAndroidVersion()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 555);
            } catch (Exception e)
            {

            }
        } else {
            pickImage();
        }

    }
    //PICK IMAGE METHOD
    public void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    //CROP REQUEST JAVA
    private void croprequest(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //RESULT FROM SELECTED IMAGE
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);
            croprequest(imageUri);
        }

        //RESULT FROM CROPING ACTIVITY
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    photoUri = result.getUri();

                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoUri);

                    ((ImageView)findViewById(R.id.ngoLogo)).setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 555 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickImage();
        } else {
            checkAndroidVersion();
        }
    }





    private void callRegister() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, RequestBody> params = new HashMap<>();


        params.put("name",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOName.getText().toString()));
        params.put("email",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOEmail.getText().toString().trim()));
        params.put("pass",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOPassword.getText().toString()));
        params.put("adminname",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOAdminName.getText().toString()));
        params.put("location",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOOfficeLocation.getText().toString()));
        params.put("govtreg",RequestBody.create(MediaType.parse("text/plain"), "Yes"));
        params.put("about",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOAbout.getText().toString()));
        params.put("contactno",RequestBody.create(MediaType.parse("text/plain"), edtNewNGOMobileNo.getText().toString().trim()));
        params.put("category",RequestBody.create(MediaType.parse("text/plain"), edtNGOCatagory.getText().toString()));

        if (photoUri != null) {

            File imageFile = new File(photoUri.getPath());
            String fileName = imageFile.getName();
            params.put("profilePic" + "\"; filename=\"" + fileName,
                    RequestBody.create(MediaType.parse("file"), imageFile));


        }


        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        //TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String,Object>> call = api.registerNGO(params);

        final ProgressDialog progressDialog = new ProgressDialog(NGORegisterActivity.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

              //  try {
                    // Read response as follow
                    if (response != null && response.body() != null) {

                        Log.d("Error", "onResponse: body: " + response.body());
                        Map<String,Object> map = response.body();
                        Gson gson=new Gson();
                        String jsonString=gson.toJson(map);

                        JsonObject content = gson.fromJson(jsonString,JsonObject.class);
                       // String st = content.getAsString();

//                        if(!st.equals("false")){
//                            // save session
//                            Toast.makeText(NGORegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }

                        if(!response.body().toString().equals("false")){
                            // save session
                            Toast.makeText(NGORegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        else{
                            Toast.makeText(NGORegisterActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(NGORegisterActivity.this, "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
//                } catch (Exception e) {
//                    Toast.makeText(NGORegisterActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();
//
//                    Log.d("Error", "Error in reading response: " + e.getMessage());
//                }
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(NGORegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
