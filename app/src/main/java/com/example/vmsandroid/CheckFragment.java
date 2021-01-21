package com.example.vmsandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 200;

    TextView qrIC,qrName,qrAddress;
    String[] qrdata;
    boolean isCheckedIn= false;
    int id;

    String cameraPermission[];

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Registration");
        View v =  inflater.inflate(R.layout.fragment_check, container, false);


        HttpLoggingInterceptor loggingInterceptor =  new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        qrIC = v.findViewById(R.id.qrvisitorIC);
        qrName = v.findViewById(R.id.qrvisitorName);
        qrAddress = v.findViewById(R.id.qrvisitorAddress);
        SharedPreferences pref = getActivity().getSharedPreferences("CheckPref", Context.MODE_PRIVATE);


        String Punit = pref.getString("Punit", null);
        // Get data from activity
        Bundle QRdata = getArguments();
        if (QRdata != null){
            qrdata = QRdata.getStringArray("qrdetails");
            isCheckedIn = pref.getBoolean("isCheckedIn", false);
            id = pref.getInt("id", 0);

        }


        if (qrdata != null){
            qrIC.setText(qrdata[0]);
            qrName.setText(qrdata[1]);
            qrAddress.setText(qrdata[2]);
        }



        Button checkin_out = (Button) v.findViewById(R.id.checkin);
        if(isCheckedIn == false){
            checkin_out.setText("Check In");
        }
        else{
            checkin_out.setText("Check Out");
        }
        checkin_out.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
                HashMap<String, String> qrcheckin_out = new HashMap<>();

                if(isCheckedIn == false){
                    qrcheckin_out.put("id", Integer.toString(id));
                    qrcheckin_out.put("checkin", currentDateTimeString);
                }
                else{
                    qrcheckin_out.put("id", Integer.toString(id));
                    qrcheckin_out.put("checkout", currentDateTimeString);
                }

                Call<qrList> call = retrofitInterface.qrCheckin_Out(qrcheckin_out);
                call.enqueue(new Callback<qrList>() {
                    @Override
                    public void onResponse(Call<qrList> call, Response<qrList> response) {
                        if(response.code() == 400){
                            Toast.makeText(getActivity(), "QRCode already been used", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), "Succesfully " + checkin_out.getText(), Toast.LENGTH_SHORT).show();
                            checkin_out.setText("Scan QR Again");
                        }

                    }
                    @Override
                    public void onFailure(Call<qrList> call, Throwable t) {
                        Toast.makeText(getActivity(), "Error " + checkin_out.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        Button registeruser = (Button) v.findViewById(R.id.registeruser);
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegistrationFragment_guard());
                fragmentTransaction.commit();
            }
        });

        CardView checkin = (CardView) v.findViewById(R.id.openQR);
        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkCameraPermission()) {
                    //camera permission not allowed, request it
                    requestCameraPermission();
                } else {
                    startActivity(new Intent(getActivity(),qrscanner.class));
                }

            }
        });
         return v;
    }

    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST_CODE);
    }

    private boolean checkCameraPermission() {
        boolean result = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean writeStorageAccepted = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && writeStorageAccepted) {
                        startActivity(new Intent(getActivity(),qrscanner.class));
                    } else {
                        Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }
}