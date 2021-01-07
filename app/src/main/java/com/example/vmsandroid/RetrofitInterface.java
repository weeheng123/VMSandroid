package com.example.vmsandroid;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/app/login")
    Call<Login> executeLogin(@Body HashMap<String, String> map);

    @POST("/app/qr")
    Call<qrentry> qrdetailEntry(@Body HashMap<String, String> detailstore);

    @POST("/app/qr")
    Call<ResponseBody> qrcodeEntry(@Body HashMap<String, ImageView> qrstore);

    @POST("/app/qr")
    Call<qrentry> qrEntry(@Body qrentry qr);
}
