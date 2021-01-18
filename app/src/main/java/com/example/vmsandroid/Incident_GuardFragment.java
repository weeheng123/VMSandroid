package com.example.vmsandroid;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Incident_GuardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Incident_GuardFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Incident_GuardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Incident_GuardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Incident_GuardFragment newInstance(String param1, String param2) {
        Incident_GuardFragment fragment = new Incident_GuardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);


            ArrayList<IterateIncident> IncidentList = new ArrayList<>();
            for (int i = 0; i < response.body().getIncident().size(); i++){
                IncidentList.add(new IterateAnnouncement(response.body().getIncident().get(i).getName(),
                        response.body().getIncident().get(i).getUnit(),
                        response.body().getIncident().get(i).getTitle(),
                        response.body().getIncident().get(i).getCreatedAt(),
                        response.body().getIncident().get(i).getRemarks(),
                        response.body().getIncident().get(i).getImage()
                        ));
            }

            mRecyclerView = getActivity().findViewById(R.id.RecyclerView1);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(getActivity());
            mAdapter = new IncidentAdapter(IncidentList);

            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mAdapter);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_incident__guard, container, false);
    }
}