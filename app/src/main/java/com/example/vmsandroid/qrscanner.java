package com.example.vmsandroid;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

import java.util.StringTokenizer;

public class qrscanner extends AppCompatActivity {
    CodeScanner codeScanner;
    CodeScannerView scanView;
    TextView resultData, ictextview, nametextview, addresstextview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);

        scanView = findViewById(R.id.scannerView);
        resultData = findViewById(R.id.resultview);

        ictextview = findViewById(R.id.qrvisitorIC);
        nametextview = findViewById(R.id.qrvisitorName);
        addresstextview = findViewById(R.id.qrvisitorAddress);

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
                        String[] qrdetails = new String[];
                        qrdetails[1] = qric;
                        qrdetails[2] = qrname;
                        qrdetails[3] = qraddress;

                        Bundle bundle = new Bundle();
                        bundle.putStringArray("QRdetails", qrdetails);

                        CheckFragment fragobj = new CheckFragment();
                        fragobj.setArguments(bundle);

                        resultData.setText("IC:" + qric + "Name:" + qrname + "Address:" + qraddress);


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


