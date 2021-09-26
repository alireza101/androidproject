package com.example.android1project;

public class user {
    private int id;
    private String username,usermail;

    public user(int id, String username, String usermail) {
        this.id = id;
        this.username = username;
        this.usermail = usermail;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUsermail() {
        return usermail;
    }
}
