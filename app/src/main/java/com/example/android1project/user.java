package com.example.android1project;

public class user {
    private int userid;
    private String username,usermail,userpassword,usergender;

    public user(int userid, String username, String usermail, String userpassword,String usergender) {
        this.userid = userid;
        this.username = username;
        this.usermail = usermail;
        this.userpassword = userpassword;
        this.usergender = usergender;
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

    public String getUsergender() {
        return usergender;
    }
}
