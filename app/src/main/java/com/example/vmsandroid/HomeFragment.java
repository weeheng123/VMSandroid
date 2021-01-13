package com.example.vmsandroid;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {



//        Resources res = getResources();
//        String[] announcements = res.getStringArray(R.array.announcement);


        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getActivity().setTitle("Announcement");
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

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

        Call<announcementList> call = retrofitInterface.getAnn();

        call.enqueue(new Callback<announcementList>() {
            @Override
            public void onResponse(Call<announcementList> call, Response<announcementList> response) {
                if (response.code() == 200){

                    ArrayList<IterateAnnouncement> Announcement = new ArrayList<>();

                    for (int i = 0; i < response.body().getAnnouncement().size(); i++){
                        Announcement.add(new IterateAnnouncement(response.body().getAnnouncement().get(i).getTitle(), response.body().getAnnouncement().get(i).getCreatedAt(), response.body().getAnnouncement().get(i).getDescription()));

                    }
                    //Announcement.add(new IterateAnnouncement(response.body().getAnnouncement().get(0).getTitle(), response.body().getAnnouncement().get(0).getCreatedAt(), response.body().getAnnouncement().get(0).getDescription()));
                    //Announcement.add(new IterateAnnouncement(response.body().getAnnouncement().get(1).getTitle(), response.body().getAnnouncement().get(1).getCreatedAt(), response.body().getAnnouncement().get(1).getDescription()));

                    mRecyclerView = getActivity().findViewById(R.id.RecyclerView);
                    mRecyclerView.setHasFixedSize(true);
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    mAdapter = new Adapter(Announcement);

                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);


//                                startActivity(new Intent(getApplicationContext(),MainMenu.class));


//                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                                builder1.setTitle(loginresult.getRole());
//                                builder1.setMessage(loginresult.getUsername());
//                                builder1.show();

                    Toast.makeText(getActivity(), "Title:"+ response.body().getAnnouncement().get(0).getTitle(), Toast.LENGTH_SHORT).show();
                }
                if (response.code() ==404){
                    Toast.makeText(getActivity(), "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<announcementList> call, Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}