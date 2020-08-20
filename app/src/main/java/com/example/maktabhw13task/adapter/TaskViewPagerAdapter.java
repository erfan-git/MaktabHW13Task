package com.example.maktabhw13task.adapter;

import com.example.maktabhw13task.controller.fragments.TaskListFragment;
import com.example.maktabhw13task.enums.TaskState;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TaskViewPagerAdapter extends FragmentStateAdapter {


    public TaskViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
      /*  if (position == 0)
            return TaskListFragment.newInstance(TaskState.TODOj);
        else if (position == 1)
            return TaskListFragment.newInstance(TaskState.DOING);
        else*/
            return TaskListFragment.newInstance();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
