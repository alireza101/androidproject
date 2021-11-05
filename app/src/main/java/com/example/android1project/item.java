package com.example.android1project;

public class item {

    private String itemid;
    private String itempicture;
    private String itemname;
    private String itemtype;
    private String itemexpiration;
    private String itemsum;
    private String itemuser;


    public item(String itemid,String itemname , String itempicture, String itemexp, String typeitem) {
        this.setItemid(itemid);
        this.setItempicture(itempicture);
        this.setItemname(itemname);
        this.setItemexpiration(itemexp);
        this.setItemuser(typeitem);
    }

    public item(String itemid, String itempicture, String itemname, String itemtype, String itemexpiration, String itemsum, String itemuser) {
        this.setItemid(itemid);
        this.setItempicture(itempicture);
        this.setItemname(itemname);
        this.setItemtype(itemtype);
        this.setItemexpiration(itemexpiration);
        this.setItemsum(itemsum);
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
}
