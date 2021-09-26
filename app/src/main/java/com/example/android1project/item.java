package com.example.android1project;

public class item {

    private String itemid;
    private String itempicture;
    private String itemname;
    private String itemtype;
    private String itemremaining;
    private String itemexpiration;
    private String itemsum;
    private String itemuser;

    public String getItemuser() {
        return itemuser;
    }

    public void setItemuser(String itemuser) {
        this.itemuser = itemuser;
    }

    public item(String itemid, String itempicture, String itemname, String itemtype, String itemremaining, String itemexpiration, String itemsum, String itemuser) {
        this.setItemid(itemid);
        this.setItempicture(itempicture);
        this.setItemname(itemname);
        this.setItemtype(itemtype);
        this.setItemremaining(itemremaining);
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

    public String getItemremaining() {
        return itemremaining;
    }

    public void setItemremaining(String itemremaining) {
        this.itemremaining = itemremaining;
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
}
