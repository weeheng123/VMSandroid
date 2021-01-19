package com.example.vmsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder> {
    private ArrayList<IterateIncident> mIncidentList;

    public static class IncidentViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mUnit;
        public TextView mTitle;
        public TextView mDate;
        public TextView mRemarks;
        public ImageView mIncidentImage;



        public IncidentViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.Name);
            mUnit = itemView.findViewById(R.id.Unit);
            mTitle = itemView.findViewById(R.id.IncidentTitle);
            mDate = itemView.findViewById(R.id.IncidentDate);
            mRemarks = itemView.findViewById(R.id.IncidentRemarks);
            mIncidentImage = itemView.findViewById(R.id.IncidentImage);

        }
    }

    public IncidentAdapter(ArrayList<IterateIncident> IncidentList) {
        mIncidentList = IncidentList;

    }

    @NonNull
    @Override
    public IncidentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.incident_iterate, parent, false);
        IncidentViewHolder ivh = new IncidentViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentViewHolder holder, int position) {
        IterateIncident currentItem = mIncidentList.get(position);

        holder.mName.setText(currentItem.getName());
        holder.mUnit.setText(currentItem.getUnit());
        holder.mTitle.setText(currentItem.getIncidentTitle());
        holder.mDate.setText(currentItem.getIncidentDate());
        holder.mRemarks.setText(currentItem.getIncidentRemarks());
        holder.mIncidentImage.setImageResource(currentItem.getIncidentImage());

    }

    @Override
    public int getItemCount() {
        return mIncidentList.size();
    }
}