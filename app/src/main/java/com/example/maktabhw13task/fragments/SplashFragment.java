package com.example.maktabhw13task.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.maktabhw13task.R;

import androidx.fragment.app.Fragment;

public class SplashFragment extends Fragment {

    private TextView mTextViewPercent;
    private ProgressBar mProgressBar;
    private ImageView mImageViewIcon;

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

        setProgressBar();

        setAnimation();

        return view;
    }

    private void findViews(View view) {
        mProgressBar = view.findViewById(R.id.progressBarSplash);
        mTextViewPercent = view.findViewById(R.id.textViewPercent);
        mImageViewIcon = view.findViewById(R.id.imageViewSplash);
    }

    private void setProgressBar() {
        if (mProgressBar.getProgress() < 100) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setProgress(mProgressBar.getProgress() + 1);
                    mTextViewPercent.setText("Percent : " + mProgressBar.getProgress() + "%");
                    setProgressBar();
                }
            }, 15);
        }
        startApp();
    }

    private void setAnimation(){
        mImageViewIcon.startAnimation(new AnimationUtils().loadAnimation(getActivity(),R.anim.move_down));
    }

    private void startApp(){
        if (mProgressBar.getProgress() == 100){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,ViewPagerFragment.newInstance()).commit();
                }
            }, 1000);
        }
    }

}