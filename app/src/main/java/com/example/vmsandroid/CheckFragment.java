package com.example.vmsandroid;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

public class CheckFragment extends Fragment {

    private static final int CAMERA_REQUEST_CODE = 200;

    TextView qrIC,qrName,qrAddress;

    String cameraPermission[];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_check, container, false);

        qrIC = v.findViewById(R.id.qrvisitorIC);
        qrName = v.findViewById(R.id.qrvisitorName);
        qrAddress = v.findViewById(R.id.qrvisitorAddress);

        // Get data from activity
        String[] qrdetails = new String[0];
        if (getArguments() != null) {
            qrdetails = getArguments().getStringArray("QRDetails");
            qrIC.setText(qrdetails[0]);
            qrName.setText(qrdetails[1]);
            qrAddress.setText(qrdetails[2]);
        }

        Button registeruser = (Button) v.findViewById(R.id.registeruser);
        registeruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container, new RegistrationFragment_guard());
                fragmentTransaction.commit();
            }
        });

        Button checkin = (Button) v.findViewById(R.id.checkin);
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