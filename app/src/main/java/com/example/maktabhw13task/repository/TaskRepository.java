package com.example.maktabhw13task.repository;

import com.example.maktabhw13task.model.TaskModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository {

    public static TaskRepository sRepository;
    private List<TaskModel> mTaskList;

    private TaskRepository() {
        mTaskList = new ArrayList<>();
    }

    public static TaskRepository getInstance() {
        if (sRepository == null)
            sRepository = new TaskRepository();
        return sRepository;
    }

    public void addTask(TaskModel taskModel) {
        mTaskList.add(taskModel);
    }

    public List<TaskModel> getTaskList() {
        return mTaskList;
    }

    public void updateTask(TaskModel taskModel) {

        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getTaskId().equals(taskModel.getTaskId())) {
                mTaskList.get(i).setTitle(taskModel.getTitle());
                mTaskList.get(i).setDescription(taskModel.getDescription());
                mTaskList.get(i).setDate(taskModel.getDate());
                return;
            }
        }

    }

    public void deleteTask(UUID taskId) {
        for (int i = 0; i < mTaskList.size(); i++) {
            if (mTaskList.get(i).getTaskId().equals(taskId)) {
                mTaskList.remove(i);
                return;
            }
        }
    }

    public void deleteAllTask() {
        for (int i = 0; i < mTaskList.size(); i++)
            mTaskList.remove(i--);
    }
}
