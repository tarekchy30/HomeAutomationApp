package com.example.demobottomnavigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);



        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if(id == R.id.nav_home){
                return true;
            }else if(id == R.id.nav_profile){
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }else if(id == R.id.nav_settings){
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        });


    }
}