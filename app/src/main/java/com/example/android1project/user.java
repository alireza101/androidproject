package com.example.android1project;

public class user {
    private int userid;
    private String username,usermail;

    public user(int id, String username, String usermail) {
        this.userid = id;
        this.username = username;
        this.usermail = usermail;
    }

    public int getId() {
        return userid;
    }

    public String getUsername() {
        return username;
    }

    public String getUsermail() {
        return usermail;
    }
}
