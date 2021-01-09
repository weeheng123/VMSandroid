package com.example.vmsandroid;

import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface RetrofitInterface {
    @POST("/app/login")
    Call<UserList> executeLogin(@Body HashMap<String, String> map);

//    @POST("/app/login")
//    Call<UserList> executeLogin(@Body JsonObject jsonObject);

    @POST("/app/qr")
    Call<qrentry> qrdetailEntry(@Body HashMap<String, String> detailstore);

    @POST("/app/qr")
    Call<ResponseBody> qrcodeEntry(@Body HashMap<String, ImageView> qrstore);

    @POST("/app/qr")
    Call<qrentry> qrEntry(@Body qrentry qr);

    @GET("/app/announcement")
    Call<announcementList> getAnn();
}
