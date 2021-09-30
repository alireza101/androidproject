package com.example.android1project;

public class type {
    private String typeid;
    private String typename;
    private String typepicture;

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getTypepicture() {
        return typepicture;
    }

    public void setTypepicture(String typepicture) {
        this.typepicture = typepicture;
    }

    public type(String typeid, String typename, String typepicture) {
        this.typeid = typeid;
        this.typename = typename;
        this.typepicture = typepicture;
    }

}
