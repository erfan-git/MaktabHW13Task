package com.example.maktabhw13task;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.maktabhw13task.adapter.ViewPagerAdapter;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 mViewPager2;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private BadgeDrawable mBadgeDrawable;
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        setSupportActionBar(mToolbar);
        setViewPager();
        setTabLayout();


    }
    private void findViews(){
        mTabLayout = findViewById(R.id.tabLayout);
        mToolbar = findViewById(R.id.appToolbar);
        mViewPager2 = findViewById(R.id.viewPager);
    }

    private void setTabLayout(){
        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:{
                        tab.setText(R.string.todo);
                        tab.setIcon(R.drawable.ic_todo);

                    }
                    case 1:{
                        tab.setText(R.string.doing);
                        tab.setIcon(R.drawable.ic_doing);
                        mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setMaxCharacterCount(3);
                        mBadgeDrawable.setNumber(100);
                    }
                    case 2:{
                        tab.setText(R.string.done);
                        tab.setIcon(R.drawable.ic_done);
                        mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setNumber(45);
                    }
                }
            }
        }).attach();
    }

    private void setViewPager(){
        mViewPagerAdapter = new ViewPagerAdapter(this);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menuExit:
                finish();
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