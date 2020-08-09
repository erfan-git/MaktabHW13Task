package com.example.maktabhw13task.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import io.supercharge.shimmerlayout.ShimmerLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maktabhw13task.R;

public class SplashFragment extends Fragment {

    public static SplashFragment newInstance() {
        
        Bundle args = new Bundle();
        
        SplashFragment fragment = new SplashFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private TextView mTextViewPercent;
    private ProgressBar mProgressBar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_splash, container, false);

        findViews(view);

        return view;
    }

    private void findViews(View view) {
        mProgressBar = view.findViewById(R.id.progressBarSplash);
        mTextViewPercent = view.findViewById(R.id.progressBarSplash);
    }

}