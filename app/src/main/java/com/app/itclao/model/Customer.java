package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class Customer {


    @SerializedName("id")
    private String id;

    @SerializedName("customer_id")
    private String customer_id;


    @SerializedName("customer_type")
    private String customer_type;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("village")
    private String village;

    @SerializedName("district")
    private String district;

    @SerializedName("province")
    private String province;

    @SerializedName("phone")
    private String phone;

    @SerializedName("fax")
    private String fax;

    @SerializedName("email")
    private String email;
    @SerializedName("remark")
    private String remark;

    @SerializedName("OutletSize")
    private String OutletSize;

    @SerializedName("OutletType")
    private String OutletType;

    @SerializedName("Rount_NB")
    private String Rount_NB;

    @SerializedName("stock_id")
    private String stock_id;


    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }


    public String getCId() {
        return id;
    }
    public String getCcustomer_id() {
        return customer_id;
    }
    public String getCustomer_type() {
        return customer_type;
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public String getVillage() {
        return village;
    }
    public String getDistrict() {
        return district;
    }
    public String getprovince() {
        return province;
    }
    public String getphone() {
        return phone;
    }
    public String getfax() {
        return fax;
    }
    public String getemail() {
        return email;
    }
    public String getremark() {
        return remark;
    }
    public String getOutletSize() {
        return OutletSize;
    }
    public String getOutletType() {
        return OutletType;
    }
    public String getRount_NB() {
        return Rount_NB;
    }
    public String getstock_id() {
        return stock_id;
    }
}