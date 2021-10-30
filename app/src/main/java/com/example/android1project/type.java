package com.example.android1project;

public class type {
    private String typeid;
    private String typename;
    private String typepicture;
    private String typepicture1;



    public type(String typeid, String typename, String typepicture, String typepicture1) {
        this.setTypeid(typeid);
        this.setTypename(typename);
        this.setTypepicture(typepicture);
        this.setTypepicture1(typepicture1);


    }


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


    public String getTypepicture1() {
        return typepicture1;
    }

    public void setTypepicture1(String typepicture1) {
        this.typepicture1 = typepicture1;
    }

}
