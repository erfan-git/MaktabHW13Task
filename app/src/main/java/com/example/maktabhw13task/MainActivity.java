package com.example.maktabhw13task;

import android.os.Bundle;

import com.example.maktabhw13task.fragments.SplashFragment;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SplashFragment.newInstance()).commit();

    }

}