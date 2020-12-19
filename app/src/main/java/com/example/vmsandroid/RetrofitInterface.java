package com.example.vmsandroid;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("/app/login")
    Call<Login> executeLogin(@Body HashMap<String, String> map);
}
