package com.example.maktabhw13task.controller.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.maktabhw13task.R;
import com.example.maktabhw13task.adapter.TaskRecyclerViewAdapter;
import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.model.TaskModel;
import com.example.maktabhw13task.repository.TaskRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListFragment extends Fragment {


    public static final String TAG = "tag";
    public static final String BUNDLE_TASK_LIST = "BundleTaskList";

    public static TaskListFragment newInstance() {

        Bundle args = new Bundle();
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private RecyclerView mRecyclerView;
    private List<TaskModel> mTaskList;
    private TaskRepository mTaskRepository;
    TaskRecyclerViewAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTaskRepository = TaskRepository.getInstance();
        mTaskList = mTaskRepository.getTaskList();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);
        setAdapter();

        return view;
    }


    private void findViews(View view){

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setAdapter(){

        mTaskList = mTaskRepository.getTaskList();

        if (mAdapter != null)
            mAdapter.notifyDataSetChanged();
        else
            mAdapter = new TaskRecyclerViewAdapter(mTaskList);

        mRecyclerView.setAdapter(mAdapter);
    }


}