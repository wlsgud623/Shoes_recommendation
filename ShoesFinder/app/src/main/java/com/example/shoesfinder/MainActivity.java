package com.example.shoesfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.bumptech.glide.annotation.GlideModule;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{


    Fragment CameraF;
    Fragment HomeF;
    Fragment SearchF;
    Fragment ListF;
    BottomNavigationView navView;
    public static String id;


    private BottomNavigationView.OnNavigationItemSelectedListener bottomListner = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.home_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,HomeF).commit();
                    return true;
                case R.id.camera_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,CameraF).commit();
                    return true;
                case R.id.searching_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,SearchF).commit();
                    return true;
                case R.id.list_button:
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,ListF).commit();
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = (BottomNavigationView)findViewById(R.id.bottom_navigation_main);
        navView.setSelectedItemId(R.id.home_button);
        navView.setOnNavigationItemSelectedListener(bottomListner);

        CameraF = new CameraFragment();
        HomeF = new HomeFragment();
        SearchF = new SearchFragment();
        ListF = new ListFragment();



        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,HomeF).commit();
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

}