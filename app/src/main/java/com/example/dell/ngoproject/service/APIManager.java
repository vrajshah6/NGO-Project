package com.example.dell.ngoproject.service;

import com.example.dell.ngoproject.utility.Constant;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

/**
 * Created by Chintan on 12-02-2018.
 */

public interface APIManager {

    @FormUrlEncoded
    @POST(Constant.URL_LOGIN)
    Call<Map<String,Object>> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Constant.URL_NGO_LOGIN)
    Call<Map<String,Object>> ngoLogin(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST(Constant.URL_REGISTER)
    Call<Map<String,Object>> register(@FieldMap Map<String, String> params);

//    @FormUrlEncoded
//    @POST(Constant.URL_REGISTER_NGO)
//    Call<Map<String,Object>> registerNGO(@FieldMap Map<String, String> params);
    @Multipart
    @POST(Constant.URL_REGISTER_NGO)
    Call<Map<String,Object>> registerNGO(@PartMap Map<String, RequestBody> params);

    @FormUrlEncoded
    @POST(Constant.URL_CREATE_FUND_RAISING)
    Call<Map<String, Object>> createFundRaising(@FieldMap Map<String, String> params);

    @GET(Constant.URL_GET_FUND_RAISING)
    Call<Map<String, Object>> getPosts();

    @GET(Constant.URL_GET_EVENT_POSTS)
    Call<Map<String, Object>> getEventPosts();


    @Multipart
    @POST(Constant.URL_FUND_RAISING_POST)
    Call<Map<String,Object>> uploadFundRaisingPost(@PartMap Map<String, RequestBody> params);
//    Call<Map<String,Object>> uploadFundRaisingPost(@FieldMap Map<String, Object> params);



//    @FormUrlEncoded
//    @POST(Constant.URL_EVENT_POST)
//    Call<Map<String,Object>> newevent(@FieldMap Map<String, Object> params);

    @Multipart
    @POST(Constant.URL_EVENT_POST)
    Call<Map<String,Object>> newevent(@PartMap Map<String, RequestBody> params);


    @FormUrlEncoded
    @POST(Constant.URL_CATEGORY)
    Call<Map<String,Object>> category(@FieldMap Map<String, String> params);

    // Add all your api calls here...
}
