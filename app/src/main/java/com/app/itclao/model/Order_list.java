package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class Order_list {
    @SerializedName("pro_name")
    private String pro_name;

    @SerializedName("unit")
    private String unit;

    @SerializedName("qty")
    private String qty;

    @SerializedName("price")
    private String price;

    @SerializedName("amount")
    private String amount;

    @SerializedName("urlimage")
    private String urlimage;

    public String getpro_name() {
        return pro_name;
    }
    public String getunit() {
        return unit;
    }
    public String getqty() {
        return qty;
    }
    public String getprice() {
        return price;
    }
    public String getamount() {
        return amount;
    }
    public String geturlimage() {
        return urlimage;
    }
}