package com.example.android1project;

public class cost {

    private String costname;
    private String costcost;
    private String itemid;
    private String costdate;

    public cost( String costname, String costcost, String itemid, String costdate) {

        this.setCostname(costname);
        this.setCostcost(costcost);
        this.setItemid(itemid);
        this.setCostdate(costdate);
    }


    public String getCostname() {
        return costname;
    }

    public void setCostname(String costname) {
        this.costname = costname;
    }

    public String getCostcost() {
        return costcost;
    }

    public void setCostcost(String costcost) {
        this.costcost = costcost;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getCostdate() {
        return costdate;
    }

    public void setCostdate(String costdate) {
        this.costdate = costdate;
    }
}
