package com.example.maktabhw13task.adapter;

import com.example.maktabhw13task.controller.fragments.TaskListFragment;

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
        return TaskListFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
