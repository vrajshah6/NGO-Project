package com.example.dell.ngoproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.dell.ngoproject.adapter.FundRaiseAdapter;
import com.example.dell.ngoproject.service.APIManager;
import com.example.dell.ngoproject.utility.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FundRaisingFragment extends android.support.v4.app.Fragment implements FundRaiseAdapter.AdapterListeners, PaymentResultListener {

    private static final String TAG = FundRaisingFragment.class.getSimpleName();
    RecyclerView rvFundRaise;

    FundRaiseAdapter fundRaiseAdapter;
    private ArrayList<FundRaise> fundRaiseList = new ArrayList<>();
    FloatingActionButton fab;
    Session session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fund_raising, container, false);

        mappingWidgets(rootView);

        session = new Session(getActivity());

        if (session.getUserType().equals("NGO")) {
            Log.d("OKKKKKKKK", "OKKK");
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NGOCreatePost.class);
                startActivityForResult(intent, 1000);
            }
        });

        rvFundRaise.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callGetDetailAPI();
    }

    private void mappingWidgets(View rootView) {
        rvFundRaise = rootView.findViewById(R.id.rvFundRaise);
        fab = rootView.findViewById(R.id.fab);
    }

    @Override
    public void onEventItemClick(int position, int id) {
        Toast.makeText(getActivity(), "Item clicked - " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBtnDonateClick(int position) {
        startPayment();

    }

    private void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */


        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", "100");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(getActivity(), options);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(getActivity(), "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }
    }

    /**
     * The name of the function has to be
     * onPaymentError
     * Wrap your code in try catch, as shown, to ensure that this method runs correctly
     */
    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(getActivity(), "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }




    void callGetDetailAPI() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL) // Bas URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Sending param
        Map<String, String> params = new HashMap<>();

        // Initializing APIManager
        APIManager api = retrofit.create(APIManager.class);

        // TODO: Note: Replace 'getDetails(param)' API method for every new API here
        Call<Map<String, Object>> call = api.getPosts();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        call.enqueue(new Callback<Map<String, Object>>() {
            @Override
            public void onResponse(Call<Map<String, Object>> call, Response<Map<String, Object>> response) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

//                try {
                    // Read response as follow
                    if (response != null && response.body() != null) {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "onResponse: body: " + response.body());

                        // Read response as follow
                        Map<String, Object> map = response.body();

                        // Converting response map to JsonObject
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(map);

                        Log.d("error", "jsonString: " + jsonString);

                        JsonObject content = gson.fromJson(jsonString, JsonObject.class);

                        // Convert JsonArray to your custom model class list as follow
                        JsonArray fundRaisePosts = content.get("posts").getAsJsonArray();
//try {

                        fundRaiseList.clear();

                        for (JsonElement fundRaisingPost : fundRaisePosts) {
                            long x = Long.parseLong(fundRaisingPost.getAsJsonObject().get("n_id").getAsString());

                            ArrayList<String> imageUrlList = new ArrayList<>();
                            JsonArray photosArray = fundRaisingPost.getAsJsonObject().get("photoPost").getAsJsonArray();

                            for (JsonElement photo : photosArray) {
                                imageUrlList.add(photo.getAsJsonObject().get("photoname").getAsString());
                            }

                            fundRaiseList.add(new FundRaise(fundRaisingPost.getAsJsonObject().get("id").getAsInt(),
                                    fundRaisingPost.getAsJsonObject().get("title").getAsString(),
                                    fundRaisingPost.getAsJsonObject().get("desc").getAsString(),
                                    fundRaisingPost.getAsJsonObject().get("amt").getAsLong(),
                                    new NGO(Long.parseLong(fundRaisingPost.getAsJsonObject().get("n_id").getAsString())),
                                    fundRaisingPost.getAsJsonObject().get("date").getAsString(),
                                    imageUrlList));
                            Log.d("MyId",x + "");
                        }



                       /* for (int i = 0; i < fundRaiseList.size(); i++) {
                            switch (i) {
                                case 0:
                                    fundRaiseList.get(i).setImageUrl("https://scontent.famd1-1.fna.fbcdn.net/v/t1.0-9/54278394_2220091688014183_6782035011454369792_n.jpg?_nc_cat=108&_nc_ht=scontent.famd1-1.fna&oh=01f1835cb8b652dafb3e3a4ced1de810&oe=5D38328A");
//                                ImageLoader.getInstance().displayImage("https://scontent.famd1-2.fna.fbcdn.net/v/t1.0-9/53681256_2215825485107470_610624618687889408_n.jpg?_nc_cat=100&_nc_ht=scontent.famd1-2.fna&oh=eff5d1e99336577364987f8d24dbbba6&oe=5D4F92ED",
//                                        holder.ivNGOPhoto);
                                    break;

                                case 1:
                                    fundRaiseList.get(i).setImageUrl("https://scontent.famd1-1.fna.fbcdn.net/v/t1.0-9/53891098_2215824358440916_2830316815941369856_n.jpg?_nc_cat=111&_nc_ht=scontent.famd1-1.fna&oh=b8f27f4f6eebad2d3128e98030ceac97&oe=5D02359B");
                                    break;

                                case 2:
                                    fundRaiseList.get(i).setImageUrl("https://scontent.famd1-2.fna.fbcdn.net/v/t1.0-9/53681256_2215825485107470_610624618687889408_n.jpg?_nc_cat=100&_nc_ht=scontent.famd1-2.fna&oh=eff5d1e99336577364987f8d24dbbba6&oe=5D4F92ED");
                                    break;

                                case 3:
                                    fundRaiseList.get(i).setImageUrl("https://scontent.famd1-1.fna.fbcdn.net/v/t1.0-9/54278394_2220091688014183_6782035011454369792_n.jpg?_nc_cat=108&_nc_ht=scontent.famd1-1.fna&oh=01f1835cb8b652dafb3e3a4ced1de810&oe=5D38328A");
                                    break;

                                default:
                                    fundRaiseList.get(i).setImageUrl("https://scontent.famd1-1.fna.fbcdn.net/v/t1.0-9/53891098_2215824358440916_2830316815941369856_n.jpg?_nc_cat=111&_nc_ht=scontent.famd1-1.fna&oh=b8f27f4f6eebad2d3128e98030ceac97&oe=5D02359B");
                                    break;
                            }
                        }*/
                        
                        
//}
//catch (Exception e)
//{
//    Log.d("ExceptionIs", e.getMessage());
//}
                        fundRaiseAdapter = new FundRaiseAdapter(getActivity(), fundRaiseList, FundRaisingFragment.this);
                        rvFundRaise.setAdapter(fundRaiseAdapter);
                    } else {
                        Toast.makeText(getActivity(), "No response available.", Toast.LENGTH_SHORT).show();

                        Log.d("Error", "No response available");
                    }
//                } catch (Exception e) {
//                    Toast.makeText(getActivity(), "Error occurred.", Toast.LENGTH_SHORT).show();
//
//                    Log.d("Error", "Error in reading response: " + e.getMessage());
//                }
            }
            @Override
            public void onFailure(Call<Map<String, Object>> call, Throwable t) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();

                Log.d("Error", "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            callGetDetailAPI();
        }
    }
}
