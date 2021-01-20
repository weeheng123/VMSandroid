package com.example.vmsandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.IncidentViewHolder> {
    private ArrayList<IterateIncident> mIncidentList;

    public static class IncidentViewHolder extends RecyclerView.ViewHolder {
        private Retrofit retrofit;
        private RetrofitInterface retrofitInterface;
        private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

        public TextView mName;
        public TextView mUnit;
        public TextView mTitle;
        public TextView mDate;
        public TextView mRemarks;
        public TextView mStatus;
        public Button mIncidentID;


        public IncidentViewHolder(@NonNull View itemView) {
            super(itemView);

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

            mName = itemView.findViewById(R.id.Name);
            mUnit = itemView.findViewById(R.id.Unit);
            mTitle = itemView.findViewById(R.id.IncidentTitle);
            mDate = itemView.findViewById(R.id.IncidentDate);
            mRemarks = itemView.findViewById(R.id.IncidentRemarks);
            mStatus = itemView.findViewById(R.id.IncidentStatus);
            mIncidentID = itemView.findViewById(R.id.ResolveIncident);

            mIncidentID.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> incident_status = new HashMap<>();
                    incident_status.put("id", mIncidentID.getTag().toString());
                    incident_status.put("status", "Resolved");

                    Call<IncidentList> call = retrofitInterface.incident_update(incident_status);
                    call.enqueue(new Callback<IncidentList>() {
                        @Override
                        public void onResponse(Call<IncidentList> call, Response<IncidentList> response) {
                            mIncidentID.setVisibility(View.INVISIBLE);
                            mStatus.setText("Resolved");
                        }

                        @Override
                        public void onFailure(Call<IncidentList> call, Throwable t) {
//                            Toast.makeText(, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
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
        holder.mStatus.setText(currentItem.getStatus());
        holder.mIncidentID.setTag(currentItem.getID());
    }

    @Override
    public int getItemCount() {
        return mIncidentList.size();
    }
}
