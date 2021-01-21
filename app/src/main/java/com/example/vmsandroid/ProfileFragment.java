package com.example.vmsandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ProfileFragment extends Fragment {

    TextView IC,Name,Address;
    String[] profiledata;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        View v =  inflater.inflate(R.layout.fragment_profile, container, false);

        IC = v.findViewById(R.id.username);
        Name = v.findViewById(R.id.address);
        Address = v.findViewById(R.id.icno);

        // Get data from activity
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String Pusername = pref.getString("Pusername", null);
        String Punit = pref.getString("Punit", null);
        String Pic = pref.getString("Pic", null);

        IC.setText(Pusername);
        Name.setText(Punit);
        Address.setText(Pic);

        Button logout = (Button) v.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),MainActivity.class));
                getActivity().finish();
            }
        });
        return v;
    }
}