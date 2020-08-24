package com.example.maktabhw13task.adapter;


import com.example.maktabhw13task.controller.fragments.TaskListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TaskViewPagerAdapter extends FragmentStateAdapter {

    private List<TaskListFragment> mFragmentList;

    public TaskViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<TaskListFragment> fragmentList) {
        super(fragmentActivity);
        mFragmentList = fragmentList;
    }

    public List<TaskListFragment> getFragmentList(){
        return mFragmentList;
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
