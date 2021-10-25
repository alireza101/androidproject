package com.example.android1project;

public class user {
    private int userid;
    private String username,usermail,userpassword;

    public user(int id, String username, String usermail) {
        this.userid = id;
        this.username = username;
        this.usermail = usermail;
    }

    public user(int userid, String username, String usermail, String userpassword) {
        this.userid = userid;
        this.username = username;
        this.usermail = usermail;
        this.userpassword = userpassword;
    }

    public String getUserpassword() {
        return userpassword;
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
