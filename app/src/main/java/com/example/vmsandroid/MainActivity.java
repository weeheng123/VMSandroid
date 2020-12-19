package com.example.vmsandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL = "http://10.0.2.2:5000/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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

                Call<Login> call = retrofitInterface.executeLogin(map);

                call.enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                            if (response.code() == 200){
                                startActivity(new Intent(getApplicationContext(),Registration.class));
//                                Login result = response.body();
//
//                                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//                                builder1.setTitle("Log in success");
//                                builder1.setMessage(result.getUsername());
//                                builder1.show();
                            }
                 }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText( MainActivity.this, t.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}