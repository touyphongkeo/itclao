package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class Staff {
    @SerializedName("id")
    private String id;

    @SerializedName("staff_id")
    private String staff_id;

    @SerializedName("staff_name")
    private String staff_name;

    public String getSid() {
        return id;
    }

    public String getStaff_id() {
        return staff_id;
    }


    public String getStaff_name() {
        return staff_name;
    }

}