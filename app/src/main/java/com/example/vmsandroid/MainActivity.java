package com.example.vmsandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "https://aqueous-hollows-89178.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick(View view){
            handleLoginDialog();
            }
        });

    }

    private void handleLoginDialog() {

        View view = getLayoutInflater().inflate(R.layout.login, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(view).show();

        Button loginBtn = view.findViewById(R.id.login);
        EditText usernameedit = view.findViewById(R.id.username);
        EditText passwordedit = view.findViewById(R.id.password);

        loginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();

                map.put("username", usernameedit.getText().toString());
                map.put("password", passwordedit.getText().toString());

                Call<UserList> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<UserList>() {
                    @Override
                    public void onResponse(Call<UserList> call, Response<UserList> response) {
                            if (response.code() == 200){
                                if ((response.body().getUser().get(0).getRole()).contains("Guard"))
                                {
                                    builder.setOnDismissListener(DialogInterface::dismiss);
                                    startActivity(new Intent(getApplicationContext(),MainMenu_guard.class));
                                }
                                else
                                {
                                    builder.setOnDismissListener(DialogInterface::dismiss);
                                    startActivity(new Intent(getApplicationContext(),MainMenu.class));
                                }



//                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                                builder1.setTitle(loginresult.getRole());
//                                builder1.setMessage(loginresult.getUsername());
//                                builder1.show();

//                                Toast.makeText(MainActivity.this, "role:"+ response.body().getUser().get(0).getRole(), Toast.LENGTH_SHORT).show();
                            }
                            if (response.code() ==404){
                                Toast.makeText(MainActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            }
                 }

                    @Override
                    public void onFailure(Call<UserList> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "error" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}