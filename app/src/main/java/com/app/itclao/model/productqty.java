package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;
//touy phongkeosee mr:
public class productqty {
    @SerializedName("qty")
    private String qty;

    @SerializedName("stockin_date")
    private String stockin_date;

    @SerializedName("stock_name")
    private String stock_name;

    @SerializedName("Product_ID")
    private String Product_ID;

    @SerializedName("Product_Name")
    private String Product_Name;

    @SerializedName("size")
    private String size;

    @SerializedName("Unit")
    private String Unit;

    @SerializedName("Group_Name")
    private String Group_Name;

    @SerializedName("version")
    private String version;

    @SerializedName("ups")
    private String ups;

    @SerializedName("Price")
    private String Price;

    public String getproductqty() {
        return qty;
    }

    public String getstockin_date() {
        return stockin_date;
    }

    public String getstockProduct_ID() {
        return Product_ID;
    }

    public String getstockProduct_Name() {
        return Product_Name;
    }

    public String getproductUnit() {
        return Unit;
    }

    public String getproductGroup_Name() {
        return Group_Name;
    }

    public String getproductversion() {
        return version;
    }

    public String getproduups() {
        return ups;
    }

    public String getprosPrice() {
        return Price;
    }

}