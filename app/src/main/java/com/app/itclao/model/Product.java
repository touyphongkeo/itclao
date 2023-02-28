package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class Product {
    @SerializedName("Id")
    private String Id;

    @SerializedName("stockin_id")
    private String stockin_id;

    @SerializedName("stockin_date")
    private String stockin_date;

    @SerializedName("stock_id")
    private String stock_id;

    @SerializedName("product_id")
    private String product_id;

    @SerializedName("product_lot_id")
    private String product_lot_id;

    @SerializedName("price")
    private String price;

    @SerializedName("sell_price")
    private String sell_price;

    @SerializedName("qty")
    private String qty;

    @SerializedName("supplier_id")
    private String supplier_id;


    @SerializedName("staff_id")
    private String staff_id;

    @SerializedName("expert_date")
    private String expert_date;

    @SerializedName("Product_ID")
    private String Product_ID;

    @SerializedName("Product_Name")
    private String Product_Name;

    @SerializedName("Product_Name_EN")
    private String Product_Name_EN;


    @SerializedName("Bar_Code")
    private String Bar_Code;

    @SerializedName("Group_ID")
    private String Group_ID;

    @SerializedName("Unit")
    private String Unit;

    @SerializedName("QTY")
    private String QTY;

    @SerializedName("Price")
    private String Price;

    @SerializedName("s1_price")
    private String s1_price;

    @SerializedName("s2_price")
    private String s2_price;

    @SerializedName("s3_price")
    private String s3_price;

    @SerializedName("ups")
    private String ups;

    @SerializedName("size")
    private String size;

    @SerializedName("version")
    private String version;

    @SerializedName("Remark")
    private String Remark;

    @SerializedName("img")
    private String img;

    @SerializedName("img_name")
    private String img_name;

    public String getID() {
        return Id;
    }
    public String gettransfer_id() {
        return stockin_id;
    }
    public String gettransfer_date() {
        return stockin_date;
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
    public String getsell_price() {
        return sell_price;
    }
    public String getqty() {
        return qty;
    }
    public String getsupplier_id() {
        return supplier_id;
    }
    public String getstaff_id() {
        return staff_id;
    }
    public String getexpert_date() {
        return expert_date;
    }
    public String getProduct_ID() {
        return Product_ID;
    }
    public String getProduct_Name() {
        return Product_Name;
    }
    public String getProduct_Name_EN() {
        return Product_Name_EN;
    }
    public String getBar_Code() {
        return Bar_Code;
    }
    public String getGroup_ID() {
        return Group_ID;
    }
    public String getUnit() {
        return Unit;
    }
    public String getQTY() {
        return QTY;
    }
    public String getPrice() {
        return Price;
    }
    public String gets1_price() {
        return s1_price;
    }
    public String gets2_price() {
        return s2_price;
    }
    public String gets3_price() {
        return s3_price;
    }
    public String getups() {
        return ups;
    }
    public String getsize() {
        return size;
    }
    public String getversion() {
        return version;
    }
    public String getRemark() {
        return Remark;
    }
    public String getimg() {
        return img;
    }
    public String getimg_name() {
        return img_name;
    }

}