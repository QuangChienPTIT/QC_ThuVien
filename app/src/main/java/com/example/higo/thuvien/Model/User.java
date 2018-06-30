package com.example.higo.thuvien.Model;

public class User {
    private String userName;
    private String userID;
    private String userEmail;

    public User() {

    }
    public User(String userID, String userEmail) {

        this.userID = userID;
        this.userEmail = userEmail;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }




}
