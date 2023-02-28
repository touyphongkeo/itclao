package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class paymentorder {
    @SerializedName("sale_id")
    private String sale_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("customer_id")
    private String customer_id;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("fnamesale")
    private String fnamesale;

    @SerializedName("sale_time")
    private String sale_time;

    @SerializedName("sale_date")
    private String sale_date;

    @SerializedName("price")
    private String price;

    @SerializedName("qty")
    private String qty;

    @SerializedName("amount")
    private String amount;

    @SerializedName("payment")
    private String payment;

    @SerializedName("pro_name")
    private String pro_name;

    @SerializedName("bill_no")
    private String bill_no;

    @SerializedName("urlimage")
    private String urlimage;


    @SerializedName("amount2")
    private String amount2;

    public String getPsale_id() {
        return sale_id;
    }
    public String getPcustomer_id() {
        return customer_id;
    }

    public String getcustomer_name() {
        return customer_name;
    }


    public String getfnamesale() {
        return fnamesale;
    }
    public String getPproduct_id() {
        return product_id;
    }
    public String getPsale_time() {
        return sale_time;
    }
    public String getPsale_date() {
        return sale_date;
    }
    public String getPprice() {
        return price;
    }
    public String getPqty() {
        return qty;
    }
    public String getPamount() {
        return amount;
    }
    public String getPpayment() {
        return payment;
    }
    public String getPpro_name() {
        return pro_name;
    }
    public String getPbill_no() {
        return bill_no;
    }
    public String geturlimage() {
        return urlimage;
    }

    public String getamount2() {
        return amount2;
    }



}