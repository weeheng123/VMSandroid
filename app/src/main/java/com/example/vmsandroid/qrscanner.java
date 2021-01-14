package com.example.vmsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.HashMap;
import java.util.StringTokenizer;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class qrscanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scanView;
    TextView resultData, ictextview, nametextview, addresstextview;


    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);

        scanView = findViewById(R.id.scannerView);
        resultData = findViewById(R.id.resultview);

        ictextview = findViewById(R.id.qrvisitorIC);
        nametextview = findViewById(R.id.qrvisitorName);
        addresstextview = findViewById(R.id.qrvisitorAddress);

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

        codeScanner = new CodeScanner(this, scanView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        StringTokenizer tokens = new StringTokenizer(result.getText(), ";");
                        String qric = tokens.nextToken();
                        String qrname = tokens.nextToken();
                        String qraddress = tokens.nextToken();
                        String[] qrdetails = new String[3];
                        qrdetails[0] = qric;
                        qrdetails[1] = qrname;
                        qrdetails[2] = qraddress;

                        Bundle bundle = new Bundle();
                        bundle.putStringArray("QRdetails", qrdetails);
                        Intent i = new Intent(qrscanner.this, MainMenu_guard.class);
                        String toFrag = "CheckFragment";
                        i.putExtra("toFrag", toFrag);
                        i.putExtra("qrdetails",qrdetails);

                        //starting of POST request to get check in
                        HashMap<String, String> qrstatusstore = new HashMap<>();
                        qrstatusstore.put("ic", qric);

                        Call<qrList> call = retrofitInterface.qrStatus(qrstatusstore);

                        call.enqueue(new Callback<qrList>() {
                            @Override
                            public void onResponse(Call<qrList> call, Response<qrList> response) {
                                for(int j = 0; j<response.body().getQrstatus().size(); j++) {
                                    boolean isCheckedIn = false;
                                    int id = 0;
                                    if ((response.body().getQrstatus().get(j).getCheckin()) == null) {
                                        isCheckedIn = false;
                                        id = Integer.parseInt(response.body().getQrstatus().get(j).getId());
                                    } else {
                                        isCheckedIn = true;
                                    }
                                    i.putExtra("isCheckedIn", isCheckedIn);
                                    i.putExtra("id", id);
                                }
                            }

                            @Override
                            public void onFailure(Call<qrList> call, Throwable t) {
                                Toast.makeText(qrscanner.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //ending of Post request
                        startActivity(i);
                    }
                });
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();

    }
}


