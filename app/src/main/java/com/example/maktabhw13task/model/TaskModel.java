package com.example.maktabhw13task.model;

import com.example.maktabhw13task.enums.TaskState;
import com.example.maktabhw13task.repository.UserRepository;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class TaskModel implements Serializable {
    private String mTitle, mDescription;
    private UUID mUserId;
    private UUID mTaskId;
    private TaskState mTaskState;
    private Date mDate;
    UserRepository mUserRepository;


    public TaskModel(String title, String description, TaskState taskState, Date date){

        this();
        mTitle = title;
        mDescription = description;
        mTaskState = taskState;
        mDate = date;
    }

    public TaskModel(){
        mUserRepository = UserRepository.getInstance();
        mTaskId = UUID.randomUUID();

        mUserId = mUserRepository.getUserList().get(mUserRepository.getCurrentUserIndex()).getId();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public TaskState getTaskState() {
        return mTaskState;
    }

    public void setTaskState(TaskState taskState) {
        mTaskState = taskState;
    }

    public UUID getUserId(){return mUserId;}

    public UUID getTaskId(){return mTaskId;}

    public UserModel getUser(UUID userId){

        for (int i = 0; i < mUserRepository.getUserList().size(); i++) {
            if (mUserRepository.getUserList().get(i).getId().equals(userId))
                return mUserRepository.getUserList().get(i);
        }
        return null;
    }
}
