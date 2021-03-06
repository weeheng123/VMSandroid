package com.example.vmsandroid;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainMenu_guard extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        bottomNavigationView=findViewById(R.id.BottomNav);

        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
        String intentFragment;
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                intentFragment = "Home";
            }
            else
            {
                intentFragment = extras.getString("toFrag");
            }
        }
        else
        {
            intentFragment = (String) savedInstanceState.getSerializable(("toFrag"));
        }

        switch (intentFragment){
            case "Home":
                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                break;
            case "CheckFragment":
                bottomNavigationView.setSelectedItemId(R.id.registration);
                Bundle userBundle = getIntent().getExtras();
                Fragment CheckFragment = new CheckFragment();
                CheckFragment.setArguments(userBundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.container,CheckFragment).commit();
                break;
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment fragment=null;
                    switch (item.getItemId())
                    {
                        case R.id.home:
                            fragment=new HomeFragment();
                            break;

                        case R.id.incident:
                            fragment=new Incident_GuardFragment();
                            break;

                        case R.id.registration:
                            fragment=new CheckFragment();
                            break;

                        case R.id.profile:
                            fragment=new ProfileFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment).commit();

                    return true;
                }
            };

}
