package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class Invoince {
    @SerializedName("pay_id")
    private String pay_id;

    @SerializedName("pay_date")
    private String pay_date;

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

    public String getIpay_id() {
        return pay_id;
    }
    public String getIpay_date() {
        return pay_date;
    }
    public String getIsale_id() {
        return sale_id;
    }
    public String getIpay_lak() {
        return pay_lak;
    }
    public String getItime() {
        return time;
    }
    public String getIbill_no() {
        return bill_no;
    }
    public String getIpay_name() {
        return pay_name;
    }
}