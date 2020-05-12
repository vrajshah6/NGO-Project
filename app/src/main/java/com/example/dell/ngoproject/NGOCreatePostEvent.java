package com.example.dell.ngoproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.ngoproject.service.APIManager;
import com.example.dell.ngoproject.utility.Constant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
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

import static okhttp3.RequestBody.create;

public class NGOCreatePostEvent extends AppCompatActivity implements View.OnClickListener {
    ImageView createPost;
    TextView tvNGOName;
    MaterialEditText edtPostTitle, edtPostDesc, edtEventVenue;
    Button btnUpload;
    Uri photoUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngocreate_post_event);

        mappingWidgets();
        addListeners();


    }

    private void mappingWidgets() {
        btnUpload = findViewById(R.id.btnUpload);
        createPost = findViewById(R.id.createPost);
        tvNGOName = findViewById(R.id.tvNGOName);
        edtPostTitle = findViewById(R.id.edtPostTitle);
        edtPostDesc = findViewById(R.id.edtPostDesc);
        edtEventVenue = findViewById(R.id.edtEventVenue);
    }

    private void addListeners() {
        createPost.setOnClickListener(this);
        btnUpload.setOnClickListener(this);
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

                    ((ImageView)findViewById(R.id.createPost)).setImageBitmap(bitmap);


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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createPost:
                checkAndroidVersion();
                break;

            case R.id.btnUpload:
                uploadImage();
//                uploadPost();
                break;
        }
    }

    private void uploadImage()
    {
        if(photoUri!=null)
        {
            final FirebaseStorage storage = FirebaseStorage.getInstance();
            final StorageReference storageRef = storage.getReference();

            final String imageName = System.currentTimeMillis() + ".jpg";
            final StorageReference imagesRef = storageRef.child("events").child(imageName);

            //  final StorageReference imagesRef = storageRef.child("fundraise");

            createPost.setDrawingCacheEnabled(true);
            createPost.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) createPost.getDrawable()).getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data = baos.toByteArray();

            UploadTask uploadTask = imagesRef.putBytes(data);

            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please Wait...");
            progressDialog.show();

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    Log.d("tag", "Url: "+imagesRef.getDownloadUrl());
                    uploadPost(getString(R.string.event_image_url, "%2F", imageName));
                    // Continue with the task to get the download URL
                    return imagesRef.getDownloadUrl();

                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    uploadPost(getString(R.string.event_image_url, "%2F", imageName));
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                    } else {
                        // Handle failures
                        // ...
                        Log.d("tag", "Issue in getting url");
                    }
                }
            });



        }

    }


    private void uploadPost(String photoUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, RequestBody> params = new HashMap<>();
        params.put("title",create(MediaType.parse("text/plain"), edtPostTitle.getText().toString()));
        params.put("caption",create(MediaType.parse("text/plain"), edtPostDesc.getText().toString()));
        params.put("venue",create(MediaType.parse("text/plain"), edtEventVenue.getText().toString()));
        params.put("imgPath", RequestBody.create(MediaType.parse("text/plain"), photoUrl));

//        if (photoUri != null) {
//
//            File imageFile = new File(photoUri.getPath());
//            String fileName = imageFile.getName();
//            params.put("profilePic" + "\"; filename=\"" + fileName,
//                    RequestBody.create(MediaType.parse("file"), imageFile));
//
//
//        }



        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        //TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String,Object>> call = api.newevent(params);

        final ProgressDialog progressDialog = new ProgressDialog(NGOCreatePostEvent.this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String,Object>>() {
            @Override
            public void onResponse(Call<Map<String,Object>> call, Response<Map<String,Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

//                try {
                // Read response as follow
                if (response != null && response.body() != null) {

                    Log.d("Error", "onResponse: body: " + response.body());
                    Map<String,Object> map = response.body();
                    Gson gson=new Gson();
                    String jsonString=gson.toJson(map);

                    JsonObject content = gson.fromJson(jsonString,JsonObject.class);
//                        String st = content.getAsString();

//                        if(!st.equals("false")){
                    // save session
                    Toast.makeText(getApplicationContext(), content.get("message").getAsString(), Toast.LENGTH_SHORT).show();
                   setResult(RESULT_OK);

                    finish();
//                        }

//                        if(response.body().toString().equals("true")){
//                            // save session
//                            Toast.makeText(getApplicationContext(), "Upload successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }

//                        else{
//                            Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
//                        }

                } else {
                    Toast.makeText(getApplicationContext(), "No response available.", Toast.LENGTH_SHORT).show();

                    Log.d("Error", "No response available");
                }
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Error occurred.", Toast.LENGTH_SHORT).show();
//
//                    Log.d("Error", "Error in reading response: " + e.getMessage());
//                }
            }

            @Override
            public void onFailure(Call<Map<String,Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {



    }
}
