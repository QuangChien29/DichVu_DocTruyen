package com.example.higo.dichvu_doctruyen.models;

public class User {
    private String userID,userName,password;

    public User(String userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.password = password;
    }

    public User() {
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
