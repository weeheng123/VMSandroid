package com.example.vmsandroid;

import android.widget.ImageView;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface RetrofitInterface {
    @POST("/app/login")
    Call<UserList> executeLogin(@Body HashMap<String, String> map);

//    @POST("/app/login")
//    Call<UserList> executeLogin(@Body JsonObject jsonObject);

    @POST("/app/qr")
    Call<qrentry> qrdetailEntry(@Body HashMap<String, String> detailstore);

//    @POST("/app/qr")
//    Call<ResponseBody> qrcodeEntry(@Body HashMap<String, ImageView> qrstore);

    @POST("/app/qrstatus")
    Call<qrList> qrStatus(@Body HashMap<String, String> qrstatusstore);

    @POST("/app/qr")
    Call<qrentry> qrEntry(@Body qrentry qr);

    @GET("/app/announcement")
    Call<announcementList> getAnn();

    @PUT("/app/checkin_out")
    Call<qrList> qrCheckin_Out(@Body HashMap<String, String> qrcheckin_out);

    @POST("/app/incident")
    Call<IncidentList> incidentUpload(@Body HashMap<String, String> incident_details);

    @GET("/app/incident/reported")
    Call<IncidentList> getInc();
}
