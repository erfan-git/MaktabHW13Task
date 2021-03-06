package com.example.maktabhw13task.repository;

import com.example.maktabhw13task.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserRepository {

    private static UserRepository sRepository;

    private UserRepository() {
        mUserList = new ArrayList<>();
    }

    public static UserRepository getInstance() {
        if (sRepository == null)
            sRepository = new UserRepository();
        return sRepository;
    }

    private List<UserModel> mUserList;

    private int mCurrentUserIndex;



    public void addUser(UserModel userModel) {
        mUserList.add(userModel);
    }

    public void setCurrentUserIndex(int index){
        mCurrentUserIndex = index;
    }

    public int getCurrentUserIndex(){
        return mCurrentUserIndex;
    }

    public List<UserModel> getUserList(){return mUserList;}

}
