package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class iteminvoice {

    @SerializedName("bill_no")
    private String bill_no;

    @SerializedName("pro_name")
    private String pro_name;

    @SerializedName("unit")
    private String unit;

    @SerializedName("price")
    private String price;

    @SerializedName("qty")
    private String qty;

    @SerializedName("amount")
    private String amount;

    @SerializedName("payment")
    private String payment;

    @SerializedName("urlimage")
    private String urlimage;

    @SerializedName("pay_name")
    private String pay_name;

    public String getIbill_nos() {
        return bill_no;
    }

    public String getIpro_name() {
        return pro_name;
    }

    public String getIunit() {
        return unit;
    }

    public String getIprice() {
        return price;
    }

    public String getIqty() {
        return qty;
    }

    public String getIamount() {
        return amount;
    }

    public String getIpayment() {
        return payment;
    }

    public String getIurlimage() {
        return urlimage;
    }
    public String getIpay_name() {
        return pay_name;
    }
}