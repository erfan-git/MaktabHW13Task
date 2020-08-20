package com.example.maktabhw13task.controller.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.TaskRecyclerViewAdapter;
import com.example.maktabhw13task.controller.activity.TaskViewPagerActivity;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;
import com.example.maktabhw13task.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListFragment extends Fragment implements TaskRecyclerViewAdapter.OnDeleteTaskListener {


    public static final String TAG = "tag";
    private RecyclerView mRecyclerView;
    private TaskRepository mTaskRepository;
    private UserRepository mUserRepository;
    private TaskRecyclerViewAdapter mAdapter;

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mTaskRepository = TaskRepository.getInstance();
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);

        setAdapter();

        return view;
    }


    private void findViews(View view) {

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setAdapter() {

        if (mAdapter == null)
            mAdapter = new TaskRecyclerViewAdapter(getActivity(), getTaskList(), this);
        else
        {
            mAdapter.setTaskList(getTaskList());
            mAdapter.notifyDataSetChanged();
        }


        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    private List<TaskModel> getTaskList() {

        List<TaskModel> list = new ArrayList<>();
        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mUserRepository.getCurrentUserIndex() != 0 && mTaskRepository.getTaskList().get(i).getTaskState().equals(mTaskRepository.getCurrentTab()) && mTaskRepository.getTaskList().get(i).getUserId().equals(mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getId()))
                list.add(mTaskRepository.getTaskList().get(i));
            else if (mUserRepository.getCurrentUserIndex() == 0 && mTaskRepository.getTaskList().get(i).getTaskState().equals(mTaskRepository.getCurrentTab()))
                list.add(mTaskRepository.getTaskList().get(i));
        }
        return list;
    }

    @Override
    public void sendTaskInfo(UUID taskId, int taskPosition) {

        deleteTask(taskId);
        mAdapter.setTaskList(getTaskList());
        mAdapter.notifyItemRemoved(taskPosition);
        ((TaskViewPagerActivity)getActivity()).hideImage();
    }

    private void deleteTask(UUID taskId){

        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mTaskRepository.getTaskList().get(i).getTaskId().equals(taskId)) {
                mTaskRepository.deleteTask(taskId);
                break;
            }
        }
    }

}