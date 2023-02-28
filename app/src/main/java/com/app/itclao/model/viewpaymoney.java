package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class viewpaymoney {
    @SerializedName("pay_id")
    private String pay_id;

    @SerializedName("pay_date")
    private String pay_date;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("customer_id")
    private String customer_id;

    @SerializedName("fnamesale")
    private String fnamesale;

    @SerializedName("sale_id")
    private String sale_id;

    @SerializedName("pay_lak")
    private String pay_lak;

    @SerializedName("time")
    private String time;

    @SerializedName("bill_no")
    private String bill_no;

    @SerializedName("pay_name")
    private String pay_name;

    public String getpay_id() {
        return pay_id;
    }

    public String getpay_date() {
        return pay_date;
    }

    public String getcustomer_name() {
        return customer_name;
    }

    public String getcustomer_id() {
        return customer_id;
    }

    public String getfnamesale() {
        return fnamesale;
    }

    public String getsale_id() {
        return sale_id;
    }

    public String getpay_lak() {
        return pay_lak;
    }

    public String gettime() {
        return time;
    }

    public String getbill_no() {
        return bill_no;
    }

    public String getpay_name() {
        return pay_name;
    }

}