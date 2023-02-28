package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;

public class user {
    @SerializedName("Id")
    private String Id;

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

    public String getId() {
        return Id;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public String getfname() {
        return fname;
    }


    public String getPassword() {
        return Password;
    }

    public String getStock_id() {
        return stock_id;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_type() {
        return user_type;
    }

    public String getTel() {
        return tel;
    }

}