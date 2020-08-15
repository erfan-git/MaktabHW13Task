package com.example.maktabhw13task.controller.activity;

import android.os.Bundle;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.controller.fragments.SplashFragment;

import androidx.appcompat.app.AppCompatActivity;

public class InitialActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) == null)
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SplashFragment.newInstance()).commit();




    }

}