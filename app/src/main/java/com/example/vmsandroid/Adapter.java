package com.example.vmsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<IterateAnnouncement> mIterateAnnouncementList;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView1;
        public TextView mTextView2;
        public TextView mTextView3;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textTitle);
            mTextView2 = itemView.findViewById(R.id.textDate);
            mTextView3 = itemView.findViewById(R.id.textDescription);

        }
    }

    public Adapter(ArrayList<IterateAnnouncement> iterateAnnouncementArrayList) {
        mIterateAnnouncementList = iterateAnnouncementArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcement_iterate, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IterateAnnouncement currentItem = mIterateAnnouncementList.get(position);

        holder.mTextView1.setText((currentItem.getTitle()));
        holder.mTextView2.setText((currentItem.getDate()));
        holder.mTextView3.setText((currentItem.getDescription()));


        }

    @Override
    public int getItemCount() {
        return mIterateAnnouncementList.size();
    }
}
