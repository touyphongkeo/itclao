package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class payment {
    @SerializedName("Id")
    private String Id;

    @SerializedName("pay_date")
    private String pay_date;

    @SerializedName("time")
    private String time;

    @SerializedName("sale_id")
    private String sale_id;

    @SerializedName("pay_lak")
    private String pay_lak;

    @SerializedName("bill_no")
    private String bill_no;

    @SerializedName("user_id")
    private String user_id;


    public String getID() {
        return Id;
    }
    public String getpay_date() {
        return pay_date;
    }
    public String gettime() {
        return time;
    }
    public String getsale_id() {
        return sale_id;
    }
    public String getpay_lak() {
        return pay_lak;
    }
    public String getbill_no() {
        return bill_no;
    }
    public String getuser_id() {
        return user_id;
    }

}