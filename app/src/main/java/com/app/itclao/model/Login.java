package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("User_ID")
    private String User_ID;

    @SerializedName("User_Name")
    private String User_Name;

    @SerializedName("fname")
    private String fname;

    @SerializedName("Password")
    private String Password;

    @SerializedName("stock_id")
    private String stock_id;

    @SerializedName("status")
    private String status;

    @SerializedName("user_type")
    private String user_type;

    @SerializedName("tel")
    private String tel;
    @SerializedName("value")
    private String value;

    @SerializedName("message")
    private String massage;

    public String getValue() {
        return value;
    }
    public String getMassage() {
        return massage;
    }
    public String getUserid() {
        return User_ID;
    }
    public String getUser_Name() {
        return User_Name;
    }
    public String getFname() {
        return fname;
    }
    public String getPassword() {
        return Password;
    }
    public String getStock_id() {
        return stock_id;
    }
    public String getuser_type() {
        return user_type;
    }
    public String getel() {
        return tel;
    }
}