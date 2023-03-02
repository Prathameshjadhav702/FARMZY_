package com.simmi.foundation.farmtofresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FramHomescren extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fram_homescren);
        bottomNavigationView=findViewById(R.id.bottomNavigationView);
         BottomNavigationView.OnNavigationItemSelectedListener listener=
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment=null;
                        switch (item.getItemId()){
                            case R.id.home:
                                selectedFragment= new MapsFragment();
                                break;
                            case R.id.orders:
                                selectedFragment= new FarmeOrder();
                                break;

                        }

                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment,selectedFragment).commit();
                        return  true;
                    }
                };
    }
}