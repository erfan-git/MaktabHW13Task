package com.example.maktabhw13task.controller.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.TaskRecyclerViewAdapter;
import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.controller.fragments.dialogs.NewTaskDialog;
import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListFragment extends Fragment implements TaskRecyclerViewAdapter.OnDeleteTaskListener {


    public static final String TAG = "tag";
    public static final int REQUEST_CODE_FAB_NEW_TASK_DIALOG = 5;
    public static final String TAG_FAB_SHOW_DIALOG = "TagFabShowDialog";
    public static final String BUNDLE_TASK_STATE = "BundleTaskState";
    private RecyclerView mRecyclerView;
    private TaskRepository mTaskRepository;
    private UserRepository mUserRepository;
    private TaskRecyclerViewAdapter mAdapter;
    private FloatingActionButton mFabNewTask;
    private ImageView mImageViewTaskEmpty;
    private TaskState mTaskState;

    public static TaskListFragment newInstance(TaskState taskState) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_TASK_STATE, taskState);
        Log.d(TAG, "newInstance:  " + taskState);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mTaskState = (TaskState) getArguments().getSerializable(BUNDLE_TASK_STATE);
        Log.d(TAG, "onCreate: " + mTaskState);

        mTaskRepository = TaskRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_task_list, container, false);


        mFabNewTask = ((TaskViewPagerActivity)getActivity()).mFabNewTask;
        mImageViewTaskEmpty = ((TaskViewPagerActivity)getActivity()).mImageViewTaskEmpty;

        findViews(view);

        setAdapter();

        setListener();


        return view;
    }

    private void setListener(){
        mFabNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NewTaskDialog taskDialog = NewTaskDialog.newInstance((TaskState) getArguments().getSerializable(BUNDLE_TASK_STATE));
                taskDialog.setTargetFragment(TaskListFragment.this, REQUEST_CODE_FAB_NEW_TASK_DIALOG);
                taskDialog.show(getFragmentManager(), TAG_FAB_SHOW_DIALOG);
            }
        });


    }


    private void findViews(View view) {

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setAdapter() {

        
        if (mAdapter == null) {
            mAdapter = new TaskRecyclerViewAdapter(getActivity(), getTaskList(), this);
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.setTaskList(getTaskList());
            mAdapter.notifyDataSetChanged();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
        ((TaskViewPagerActivity)getActivity()).hideImage(mTaskState);
        Log.d(TAG, "onResume: ");
    }

    private List<TaskModel> getTaskList() {

        Log.d(TAG, "getTaskList: TaskState " + mTaskState);

        List<TaskModel> list = new ArrayList<>();
        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mUserRepository.getCurrentUserIndex() != 0 && mTaskRepository.getTaskList().get(i).getTaskState().equals(mTaskState) && mTaskRepository.getTaskList().get(i).getUserId().equals(mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getId()))
                list.add(mTaskRepository.getTaskList().get(i));
            else if (mUserRepository.getCurrentUserIndex() == 0 && mTaskRepository.getTaskList().get(i).getTaskState().equals(mTaskState))
                list.add(mTaskRepository.getTaskList().get(i));
        }
        return list;
    }

    @Override
    public void sendTaskInfo(UUID taskId, int taskPosition) {

        deleteTask(taskId);
        mAdapter.setTaskList(getTaskList());
        mAdapter.notifyItemRemoved(taskPosition);
        ((TaskViewPagerActivity)getActivity()).hideImage(mTaskState);
    }

    private void deleteTask(UUID taskId){

        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mTaskRepository.getTaskList().get(i).getTaskId().equals(taskId)) {
                mTaskRepository.deleteTask(taskId);
                break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode != Activity.RESULT_OK && data == null)
            return;

        if (requestCode == REQUEST_CODE_FAB_NEW_TASK_DIALOG){
            setAdapter();
            Log.d(TAG, "onActivityResult: ");
            ((TaskViewPagerActivity)getActivity()).hideImage(mTaskState);
        }
    }

    /*public void setTaskState(int position){
        if (position == 0)
            mTaskState = TaskState.TODO;
        else if (position == 1)
            mTaskState = TaskState.DOING;
        else
            mTaskState = TaskState.DONE;
    }*/
}
