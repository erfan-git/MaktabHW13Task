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

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TaskListFragment extends Fragment {


    public static final String TAG = "tag";
    public static final String BUNDLE_TASK_STATE = "BundleTaskList";
    private RecyclerView mRecyclerView;
    private TaskRepository mTaskRepository;
    private TaskRecyclerViewAdapter mAdapter;
    private TaskState mTaskState;

    public static TaskListFragment newInstance(TaskState state) {

        Bundle args = new Bundle();
        args.putSerializable(BUNDLE_TASK_STATE, state);
        TaskListFragment fragment = new TaskListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTaskState = (TaskState) getArguments().getSerializable(BUNDLE_TASK_STATE);
        Log.d(TAG, "TaskState onCreate : " + mTaskState);
        mTaskRepository = TaskRepository.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);

        Log.d(TAG, "TaskState onCreateView : " + mTaskState);

        setAdapter(mTaskState);

        return view;
    }


    private void findViews(View view) {

        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    public void setAdapter(TaskState taskState) {

       /* if (mAdapter == null) {
            mAdapter = new TaskRecyclerViewAdapter(getTaskList(taskState));

        } else {
            mAdapter.notifyDataSetChanged();

        }*/



        Log.d(TAG, "TaskState setAdapter : " + taskState);
        Log.d(TAG, "in adapter");

        mAdapter = new TaskRecyclerViewAdapter(getTaskList(taskState));
        mRecyclerView.setAdapter(mAdapter);

    }


    private List<TaskModel> getTaskList(TaskState taskState) {
        List<TaskModel> list = new ArrayList<>();

        Log.d(TAG, "mTaskRepository.getTaskList().size() : " + mTaskRepository.getTaskList().size());
        for (int i = 0; i < mTaskRepository.getTaskList().size(); i++) {
            if (mTaskRepository.getTaskList().get(i).getTaskState().equals(taskState))
                list.add(mTaskRepository.getTaskList().get(i));
        }
        Log.d(TAG, "target list : " + list.size());
        return list;
    }

}