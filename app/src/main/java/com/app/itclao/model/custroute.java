package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class custroute {
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

    @SerializedName("route_id")
    private String route_id;

    @SerializedName("date_check")
    private String date_check;

    @SerializedName("status")
    private String status;

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }
    public String getcustid() {
        return id;
    }
    public String getcustomer_id() {
        return customer_id;
    }
    public String getcustomer_type() {
        return customer_type;
    }
    public String getcustomer_name() {
        return customer_name;
    }

    public String getcustvillage() {
        return village;
    }

    public String getcustdistrict() {
        return district;
    }

    public String getcustprovince() {
        return province;
    }

    public String getcustphone() {
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
    public String getroute_id() {
        return route_id;
    }
    public String getdate_check() {
        return date_check;
    }
    public String getstatus() {
        return status;
    }
}