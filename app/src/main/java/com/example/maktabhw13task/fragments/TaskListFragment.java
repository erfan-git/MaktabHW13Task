package com.example.maktabhw13task.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.ViewPagerAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

public class TaskListFragment extends Fragment {

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();

        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ViewPager2 mViewPager2;
    private Toolbar mToolbar;
    private FloatingActionButton mFabNewTask;
    private TabLayout mTabLayout;
    private BadgeDrawable mBadgeDrawable;
    ViewPagerAdapter mViewPagerAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);
       setHasOptionsMenu(true);
        setViewPager();
        setTabLayout();

        return view;
    }

    private void findViews(View view){
        mTabLayout = view.findViewById(R.id.tabLayout);
        mToolbar = view.findViewById(R.id.appToolbar);
        mViewPager2 = view.findViewById(R.id.viewPager);
        mFabNewTask = view.findViewById(R.id.fabNewTask);
    }

    private void setTabLayout(){

        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 1:{
                        tab.setText(R.string.doing);
                        tab.setIcon(R.drawable.ic_doing);
                        mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setMaxCharacterCount(3);
                        mBadgeDrawable.setNumber(100);
                        break;
                    }
                    case 2:{
                        tab.setText(R.string.done);
                        tab.setIcon(R.drawable.ic_done);
                        mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setNumber(45);
                        break;
                    }
                    default:{
                        tab.setText(R.string.todo);
                        tab.setIcon(R.drawable.ic_todo);
                        break;
                    }
                }

            }
        }).attach();
    }

    private void setViewPager(){
        mViewPagerAdapter = new ViewPagerAdapter(getActivity());
        mViewPager2.setAdapter(mViewPagerAdapter);

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mBadgeDrawable = mTabLayout.getTabAt(position).getOrCreateBadge();
                mBadgeDrawable.setVisible(false);
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.app_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuExit:
                getActivity().finish();
                break;
            case R.id.menuShare:
                item.getSubMenu().clearHeader();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}