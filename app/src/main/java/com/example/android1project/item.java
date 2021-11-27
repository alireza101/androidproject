package com.example.android1project;

public class item {

    private String itemid;
    private String itemname;
    private String itempicture;
    private String itemexpiration;
    private String itemcalorie;
    private String itemsnname;
    private String itemsum;
    private String itemtype;
    private String itemgrading;
    private String itemuser;

    public item(String itemid, String itemname, String itempicture, String itemexpiration, String itemcalorie, String itemsnname, String itemsum, String itemtype, String itemgrading, String itemuser) {
        this.setItemid(itemid);
        this.setItempicture(itempicture);
        this.setItemname(itemname);
        this.setItemexpiration(itemexpiration);
        this.setItemtype(itemtype);
        this.setItemcalorie(itemcalorie);
        this.setItemsnname(itemsnname);
        this.setItemsum(itemsum);
        this.setItemgrading(itemgrading);
        this.setItemuser(itemuser);
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItempicture() {
        return itempicture;
    }

    public void setItempicture(String itempicture) {
        this.itempicture = itempicture;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getItemtype() {
        return itemtype;
    }

    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    public String getItemexpiration() {
        return itemexpiration;
    }

    public void setItemexpiration(String itemexpiration) {
        this.itemexpiration = itemexpiration;
    }

    public String getItemsum() {
        return itemsum;
    }

    public void setItemsum(String itemsum) {
        this.itemsum = itemsum;
    }
    public String getItemuser() {
        return itemuser;
    }

    public void setItemuser(String itemuser) {
        this.itemuser = itemuser;
    }
    public String getItemcalorie() {
        return itemcalorie;
    }

    public void setItemcalorie(String itemcalorie) {
        this.itemcalorie = itemcalorie;
    }

    public String getItemsnname() {
        return itemsnname;
    }

    public void setItemsnname(String itemsnname) {
        this.itemsnname = itemsnname;
    }

    public String getItemgrading() {
        return itemgrading;
    }

    public void setItemgrading(String itemgrading) {
        this.itemgrading = itemgrading;
    }
}
