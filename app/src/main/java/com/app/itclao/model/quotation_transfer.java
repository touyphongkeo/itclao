package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class quotation_transfer {
    @SerializedName("Id")
    private String Id;

    @SerializedName("transfer_id")
    private String transfer_id;

    @SerializedName("transfer_date")
    private String transfer_date;


    @SerializedName("stock_id")
    private String stock_id;


    @SerializedName("qty")
    private String qty;

    @SerializedName("price")
    private String price;

    @SerializedName("status")
    private String status;

    @SerializedName("bill_no")
    private String bill_no;

    @SerializedName("Product_ID")
    private String Product_ID;

    @SerializedName("Product_Name")
    private String Product_Name;

    @SerializedName("sumqty")
    private String sumqty;


    public String getID() {
        return Id;
    }
    public String gettransfer_id() {
        return transfer_id;
    }
    public String gettransfer_date() {
        return transfer_date;
    }
    public String getstock_id() {
        return stock_id;
    }
    public String getqty() {
        return qty;
    }
    public String getprice() {
        return price;
    }
    public String getstatus() {
        return status;
    }
    public String getbill_no() {
        return bill_no;
    }
    public String getProduct_ID() {
        return Product_ID;
    }
    public String getProduct_Name() {
        return Product_Name;
    }
    public String getSumqty() {
        return sumqty;
    }
}