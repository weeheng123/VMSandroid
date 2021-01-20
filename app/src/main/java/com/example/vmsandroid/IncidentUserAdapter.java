package com.example.vmsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IncidentUserAdapter extends RecyclerView.Adapter<IncidentUserAdapter.IncidentUserViewHolder>{
    private ArrayList<IterateIncidentUser> mIterateIncidentUserList;

    public static class IncidentUserViewHolder extends RecyclerView.ViewHolder{

        public TextView mTitle;
        public TextView mDate;
        public TextView mDescription;
        public TextView mStatus;

        public IncidentUserViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.IncidentTitleUser);
            mDate = itemView.findViewById(R.id.IncidentDateUser);
            mDescription = itemView.findViewById(R.id.IncidentRemarksUser);
            mStatus = itemView.findViewById(R.id.IncidentStatusUser);

        }
    }

    public IncidentUserAdapter(ArrayList<IterateIncidentUser> IterateIncidentList){
        mIterateIncidentUserList = IterateIncidentList;
    }

    @NonNull
    @Override
    public IncidentUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.iterate_incidentcreated_user, parent, false);
        IncidentUserViewHolder ivh = new IncidentUserViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentUserViewHolder holder, int position) {
        IterateIncidentUser currentItem = mIterateIncidentUserList.get(position);

        holder.mTitle.setText(currentItem.getTitle());
        holder.mDate.setText(currentItem.getDate());
        holder.mDescription.setText(currentItem.getDescription());
        holder.mStatus.setText(currentItem.getStatus());

    }

    @Override
    public int getItemCount() {
        return mIterateIncidentUserList.size();
    }
}
