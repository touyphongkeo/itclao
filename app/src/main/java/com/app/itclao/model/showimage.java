package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class showimage {
    @SerializedName("Product_Name")
    private String Product_Name;

    @SerializedName("qtys")
    private String qtys;

    @SerializedName("img")
    private String img;

    @SerializedName("s1_price")
    private String s1_price;

    @SerializedName("s2_price")
    private String s2_price;

    @SerializedName("s3_price")
    private String s3_price;

    public String getProduct_Namesssss() {
        return Product_Name;
    }
    public String getqtyds() {
        return qtys;
    }
    public String getimgs() {
        return img;
    }
    public String getss1_price() {
        return s1_price;
    }

    public String getsss2_price() {
        return s2_price;
    }
    public String gets3_prices() {
        return s3_price;
    }



}