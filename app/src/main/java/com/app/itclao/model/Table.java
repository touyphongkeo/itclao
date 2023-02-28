package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class Table {
    @SerializedName("Id")
    private String Id;

    @SerializedName("stock_id")
    private String stock_id;

    @SerializedName("stock_name")
    private String stock_name;

    @SerializedName("status")
    private String status;

    public String getId() {
        return Id;
    }

    public String geStock_id() {
        return stock_id;
    }

    public String getStock_name() {
        return stock_name;
    }

    public String getStatus() {
        return status;
    }
}