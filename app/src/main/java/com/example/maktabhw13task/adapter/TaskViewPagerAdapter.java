package com.example.maktabhw13task.adapter;

import com.example.maktabhw13task.fragments.DoingFragment;
import com.example.maktabhw13task.fragments.DoneFragment;
import com.example.maktabhw13task.fragments.TodoFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TaskViewPagerAdapter extends FragmentStateAdapter {

    public TaskViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return DoingFragment.newInstance();
            case 2:
                return DoneFragment.newInstance();
            default:
                return TodoFragment.newInstance();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
