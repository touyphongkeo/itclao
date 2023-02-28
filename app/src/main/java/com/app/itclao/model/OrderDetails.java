package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class OrderDetails {
    @SerializedName("Id")
    private String Id;

    @SerializedName("sale_id")
    private String sale_id;

    @SerializedName("sale_date")
    private String sale_date;

    @SerializedName("sale_time")
    private String sale_time;

    @SerializedName("refer_no")
    private String refer_no;

    @SerializedName("customer_id")
    private String customer_id;

    @SerializedName("stock_id")
    private String stock_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("product_lot_id")
    private String product_lot_id;

    @SerializedName("price")
    private String price;

    @SerializedName("qty")
    private String qty;

    @SerializedName("amount")
    private String amount;

    @SerializedName("payment")
    private String payment;

    @SerializedName("discount")
    private String discount;

    @SerializedName("bill_discount")
    private String bill_discount;

    @SerializedName("total")
    private String total;

    @SerializedName("free")
    private String free;

    @SerializedName("lak")
    private String lak;

    @SerializedName("pro_name")
    private String pro_name;

    @SerializedName("unit")
    private String unit;

    @SerializedName("amounprice")
    private String amounprice;

    @SerializedName("user_id")
    private String user_id;

    @SerializedName("fnamesale")
    private String fnamesale;

    @SerializedName("telsale")
    private String telsale;


    @SerializedName("customer_name")
    private String customer_name;

    public String getid() {
        return Id;
    }
    public String getsale_id() {
        return sale_id;
    }
    public String getsale_date() {
        return sale_date;
    }
    public String getsale_time() {
        return sale_time;
    }
    public String getrefer_no() {
        return refer_no;
    }
    public String getcustomer_id() {
        return customer_id;
    }
    public String getstock_id() {
        return stock_id;
    }
    public String getproduct_id() {
        return product_id;
    }
    public String getproduct_lot_id() {
        return product_lot_id;
    }
    public String getprice() {
        return price;
    }
    public String getqty() {
        return qty;
    }
    public String getamount() {
        return amount;
    }
    public String getpayment() {
        return payment;
    }
    public String getdiscount() {
        return discount;
    }
    public String getbill_discount() {
        return bill_discount;
    }
    public String gettotal() {
        return total;
    }
    public String getfree() {
        return free;
    }
    public String getlak() {
        return lak;
    }
    public String getpro_name() {
        return pro_name;
    }
    public String getunit() {
        return unit;
    }
    public String getamounprice() {
        return amounprice;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getfnamesale() {
        return fnamesale;
    }

    public String gettelsale() {
        return telsale;
    }

    public String getcus_name() {
        return customer_name;
    }

}