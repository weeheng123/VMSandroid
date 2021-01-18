package com.example.vmsandroid;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncidentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncidentFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_incident, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE);

        String Punit = pref.getString("Punit", null);
        String Pusername = pref.getString("Pusername", null);

        TextView Address = v.findViewById(R.id.tenantUnit);
        TextView Name = v.findViewById(R.id.tenantName);
        Address.setText(Punit);
        Name.setText(Pusername);

        return v;
    }
}