package com.example.maktabhw13task.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class UserModel implements Serializable {
    private String mUsername, mPassword;
    private UUID mUUID;
    private Date mDate;

    public UserModel(String username, String password) {
        this();
        mUsername = username;
        mPassword = password;
    }
    public UserModel(){
        mUUID = UUID.randomUUID();
        mDate = new Date();
    }

    public String getUsername() { return mUsername; }
    public void setUsername(String username) { mUsername = username; }

    public String getPassword() { return mPassword; }
    public void setPassword(String password) { mPassword = password; }

    public UUID getId() {
        return mUUID;
    }

    public Date getDate() {
        return mDate;
    }
}
