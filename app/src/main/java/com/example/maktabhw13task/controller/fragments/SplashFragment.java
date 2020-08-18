package com.example.maktabhw13task.controller.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.maktabhw13task.R;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    private LottieAnimationView mLottieAnimationViewSplash;

    public static SplashFragment newInstance() {

        Bundle args = new Bundle();

        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_splash, container, false);

        findViews(view);
        startApp();

        return view;
    }

    private void findViews(View view) {
        mLottieAnimationViewSplash = view.findViewById(R.id.lottieSplash);
        mLottieAnimationViewSplash.setSpeed(0.6f);
    }

    private void startApp() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, SignInFragment.newInstance()).commit();
            }
        }, 3000);

    }


}