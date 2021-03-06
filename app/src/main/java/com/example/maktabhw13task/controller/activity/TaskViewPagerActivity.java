package com.example.maktabhw13task.controller.activity;

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
import com.example.maktabhw13task.controller.fragments.TaskListFragment;
import com.example.maktabhw13task.controller.fragments.dialogs.DeleteAllTaskDialog;
import com.example.maktabhw13task.controller.fragments.dialogs.UserMangerDialog;
import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

public class TaskViewPagerActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    public static final String TAG_DELETE_DIALOG = "DeleteTaskDialog";
    public static final String TAG_REMOVE_DIALOG = "RemoveUserDialog";
    public ViewPager2 mViewPager2;
    public FloatingActionButton mFabNewTask;
    public ImageView mImageViewTaskEmpty;
    private Toolbar mToolbar;
    private TabLayout mTabLayout;
    private TaskViewPagerAdapter mTaskViewPagerAdapter;
    private UserRepository mUserRepository;
    private TaskRepository mTaskRepository;

    public static Intent newIntent(Context context) {

        return new Intent(context, TaskViewPagerActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view_pager);

        mUserRepository = UserRepository.getInstance();
        mTaskRepository = TaskRepository.getInstance();

        findViews();
        setToolbar();

        setViewPager();
        setTabLayout();

        hideImage(TaskState.TODO);
    }

    public void setViewPager() {
        mTaskViewPagerAdapter = new TaskViewPagerAdapter(this);
        mViewPager2.setAdapter(mTaskViewPagerAdapter);
    }

    private void setTabLayout() {

        new TabLayoutMediator(mTabLayout, mViewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position) {
                    case 1: {
                        tab.setText(R.string.doing);
                        tab.setIcon(R.drawable.ic_doing);
                        break;
                    }
                    case 2: {
                        tab.setText(R.string.done);
                        tab.setIcon(R.drawable.ic_done);
                        break;
                    }
                    default: {
                        tab.setText(R.string.todo);
                        tab.setIcon(R.drawable.ic_todo);
                        break;
                    }
                }

            }
        }).attach();
    }

    private void findViews() {

        mTabLayout = findViewById(R.id.tabLayout);
        mToolbar = findViewById(R.id.appToolbar);
        mViewPager2 = findViewById(R.id.viewPager);
        mFabNewTask = findViewById(R.id.fabNewTask);
        mImageViewTaskEmpty = findViewById(R.id.imageViewAddNewTask);

    }

    private void setToolbar() {

        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitle(mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getUsername());
        mToolbar.setSubtitleTextColor(Color.WHITE);
    }

    public void updateRecyclerView() {
        for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {

            if (getSupportFragmentManager().getFragments().get(i) instanceof TaskListFragment) {
                ((TaskListFragment) getSupportFragmentManager().getFragments().get(i)).setAdapter();
                hideImage(getCurrentTab());
                return;
            }
        }
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

        switch (item.getItemId()) {
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

    private TaskState getCurrentTab() {

        if (mViewPager2.getCurrentItem() == 0)
            return TaskState.TODO;
        else if (mViewPager2.getCurrentItem() == 1)
            return TaskState.DOING;
        else
            return TaskState.DONE;
    }

    public void hideImage(TaskState taskState) {

        if (getTaskList(taskState).size() == 0) {
            mImageViewTaskEmpty.setVisibility(View.VISIBLE);
            mImageViewTaskEmpty.bringToFront();
        } else
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