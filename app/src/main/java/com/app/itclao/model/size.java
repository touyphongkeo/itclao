package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class size {


    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("value")
    private String value;


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }


}