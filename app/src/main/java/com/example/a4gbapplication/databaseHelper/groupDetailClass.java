package com.example.a4gbapplication.databaseHelper;

import java.sql.Date;

public class groupDetailClass {
    private String gd_ID;
    private Date gd_joinDate;
    private int gd_qty;
    private String gd_cus_ID;
    private String gd_pg_ID;

    public void groupDetailClass() {

    }

    public groupDetailClass(String gd_ID, Date gd_joinDate, int gd_qty, String gd_cus_ID, String gd_pg_ID) {
        this.gd_ID = gd_ID;
        this.gd_joinDate = gd_joinDate;
        this.gd_qty = gd_qty;
        this.gd_cus_ID = gd_cus_ID;
        this.gd_pg_ID = gd_pg_ID;
    }

    public String getGd_ID() {
        return gd_ID;
    }

    public Date getGd_joinDate() {
        return gd_joinDate;
    }

    public int getGd_qty() {
        return gd_qty;
    }

    public String getGd_cus_ID() {
        return gd_cus_ID;
    }

    public String getGd_pg_ID() {
        return gd_pg_ID;
    }
}


