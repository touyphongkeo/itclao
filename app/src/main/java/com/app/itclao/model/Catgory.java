package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class Catgory {
    @SerializedName("Id")
    private String Id;

    @SerializedName("Group_ID")
    private String Group_ID;

    @SerializedName("Group_Name")
    private String Group_Name;

    public String getID() {
        return Id;
    }

    public String getCategory_id() {
        return Group_ID;
    }

    public String getCategory_name() {
        return Group_Name;
    }





}