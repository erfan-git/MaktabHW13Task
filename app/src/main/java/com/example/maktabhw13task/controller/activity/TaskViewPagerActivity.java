package com.example.maktabhw13task.controller.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.TaskViewPagerAdapter;
import com.example.maktabhw13task.controller.fragments.dialogs.UserMangerDialog;
import com.example.maktabhw13task.controller.fragments.dialogs.DeleteAllTaskDialog;
import com.example.maktabhw13task.controller.fragments.dialogs.NewTaskDialog;
import com.example.maktabhw13task.controller.fragments.TaskListFragment;
import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class TaskViewPagerActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    public static final String TAG_DELETE_DIALOG = "DeleteTaskDialog";
    public static final String TAG_REMOVE_DIALOG = "RemoveUserDialog";
    private int mCurrentTab;

    public static Intent newIntent(Context context){
        return new Intent(context, TaskViewPagerActivity.class);
    }

    public static final int REQUEST_CODE_NEW_TASK = 0;
    public static final String TAG_NEW_TASK = "TagNewTask";



    public ViewPager2 mViewPager2;
    private Toolbar mToolbar;
    private FloatingActionButton mFabNewTask;

    private TabLayout mTabLayout;
    private BadgeDrawable mBadgeDrawable;
    public TaskViewPagerAdapter mTaskViewPagerAdapter;
    private UserRepository mUserRepository;
    private TaskRepository mTaskRepository;
    private ImageView mImageViewTaskEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view_pager);

        mUserRepository = UserRepository.getInstance();
        mTaskRepository = TaskRepository.getInstance();

        findViews();

        setListeners();

        setToolbar();

        setViewPager();

        setTabLayout();

        hideImage();

    }



    private void findViews(){

        mTabLayout = findViewById(R.id.tabLayout);
        mToolbar = findViewById(R.id.appToolbar);
        mViewPager2 = findViewById(R.id.viewPager);
        mFabNewTask = findViewById(R.id.fabNewTask);
        mImageViewTaskEmpty = findViewById(R.id.imageViewAddNewTask);
    }

    private void setToolbar(){

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitle(mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getUsername());
        mToolbar.setSubtitleTextColor(Color.WHITE);
    }

    private void setTabLayout(){

        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 1:{
                        tab.setText(R.string.doing);
                        tab.setIcon(R.drawable.ic_doing);
                     /*   mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setMaxCharacterCount(3);
                        mBadgeDrawable.setNumber(100);*/
                        break;
                    }
                    case 2:{
                        tab.setText(R.string.done);
                        tab.setIcon(R.drawable.ic_done);
                     /*   mBadgeDrawable = tab.getOrCreateBadge();
                        mBadgeDrawable.setVisible(true);
                        mBadgeDrawable.setNumber(45);*/
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

    public void setViewPager(){

        mTaskViewPagerAdapter = new TaskViewPagerAdapter(this);
        mViewPager2.setAdapter(mTaskViewPagerAdapter);





       /* mBadgeDrawable = mTabLayout.getTabAt(position).getOrCreateBadge();
        mBadgeDrawable.setVisible(false);*/

        //mViewPager2.setCurrentItem(mViewPager2.getCurrentItem(), false);
    }

    private void setListeners(){

        mFabNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTaskDialog();
            }
        });

        mImageViewTaskEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNewTaskDialog();
            }
        });

        mViewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                hideImage();
            }
        });

    }

    private void showNewTaskDialog(){
        NewTaskDialog.newInstance(mViewPager2.getCurrentItem()).show(getSupportFragmentManager(),TAG_NEW_TASK);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_menu, menu);
        MenuItem delete = menu.findItem(R.id.menuAdmin);

        if (mUserRepository.getCurrentUserIndex() == 0)
            delete.setEnabled(true);
        else
            delete.setEnabled(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.menuExit:
                finish();
                return true;
            case R.id.menuAdmin:
                item.getSubMenu().clearHeader();
                return true;
            case R.id.menuDelete:
                 DeleteAllTaskDialog.newInstance().show(getSupportFragmentManager(), TAG_DELETE_DIALOG);
                 return true;
            case R.id.menuManageUser:
                UserMangerDialog.newInstance().show(getSupportFragmentManager(), TAG_REMOVE_DIALOG);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUpdate(){
        int position = mViewPager2.getCurrentItem();

        setViewPager();
        mViewPager2.setCurrentItem(position, false);

        for (int i = 0; i <getSupportFragmentManager().getFragments().size() ; i++) {

            if (getSupportFragmentManager().getFragments().get(i) instanceof TaskListFragment){
                ((TaskListFragment) getSupportFragmentManager().getFragments().get(i)).setAdapter(getCurrentTabState());

                return;
            }
        }
        hideImage();
    }

    private TaskState getCurrentTabState(){

        if (mViewPager2.getCurrentItem() == 0)
            return TaskState.TODO;
        else if (mViewPager2.getCurrentItem() == 1)
            return TaskState.DOING;
        else
            return TaskState.DONE;
    }

    private void hideImage(){

        if (getTaskList(getCurrentTabState()).size() == 0) {
            mImageViewTaskEmpty.setVisibility(View.VISIBLE);
            //mImageViewTaskEmpty.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
            mImageViewTaskEmpty.bringToFront();
        }
        else
            mImageViewTaskEmpty.setVisibility(View.GONE);
    }

    private List<TaskModel> getTaskList(TaskState taskState) {
        List<TaskModel> list = new ArrayList<>();

        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mTaskRepository.getTaskList().get(i).getTaskState().equals(taskState) && mTaskRepository.getTaskList().get(i).getUserId().equals(mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getId()))
                list.add(mTaskRepository.getTaskList().get(i));
            else if (mUserRepository.getCurrentUserIndex() == 0 && mTaskRepository.getTaskList().get(i).getTaskState().equals(taskState))
                list.add(mTaskRepository.getTaskList().get(i));
        }

        return list;
    }

}