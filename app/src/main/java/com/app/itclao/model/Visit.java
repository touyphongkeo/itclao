package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class Visit {
    @SerializedName("Id")
    private String Id;

    @SerializedName("visit_id")
    private String visit_id;

    @SerializedName("fname")
    private String fname;

    @SerializedName("leader")
    private String leader;

    @SerializedName("phone")
    private String phone;


    @SerializedName("date")
    private String date;

    @SerializedName("customer_id")
    private String customer_id;

    @SerializedName("customer_name")
    private String customer_name;

    @SerializedName("onc")
    private String onc;

    @SerializedName("note")
    private String note;

    @SerializedName("User_ID")
    private String User_ID;

    @SerializedName("time")
    private String time;

    @SerializedName("red_calabash")
    private String red_calabash;

    @SerializedName("red_calabashqty_check")
    private String red_calabashqty_check;

    @SerializedName("red_calabash_in")
    private String red_calabash_in;

    @SerializedName("same_as_before")
    private String same_as_before;

    @SerializedName("same_as_before_check")
    private String same_as_before_check;

    @SerializedName("same_as_before_in")
    private String same_as_before_in;

    @SerializedName("coffeestrong")
    private String coffeestrong;

    @SerializedName("coffeestrong_check")
    private String coffeestrong_check;

    @SerializedName("coffeestrong_in")
    private String coffeestrong_in;

    @SerializedName("youngcoffee")
    private String youngcoffee;

    @SerializedName("youngcoffee_check")
    private String youngcoffee_check;

    @SerializedName("youngcoffee_in")
    private String youngcoffee_in;

    @SerializedName("value")
    private String value;

    public String getValue() {
        return value;
    }


    public String getId() {
        return Id;
    }

    public String getvisit_id() {
        return visit_id;
    }

    public String getfname() {
        return fname;
    }

    public String getleader() {
        return leader;
    }

    public String getphone() {
        return phone;
    }

    public String getdate() {
        return date;
    }

    public String getcustomer_id() {
        return customer_id;
    }

    public String getcustomer_name() {
        return customer_name;
    }

    public String getonc() {
        return onc;
    }

    public String getnot() {
        return note;
    }

    public String getUser_ID() {
        return User_ID;
    }

    public String getTime() {
        return time;
    }

    public String getred_calabash() {
        return red_calabash;
    }

    public String getred_calabashqty_check() {
        return red_calabashqty_check;
    }

    public String getred_calabash_in() {
        return red_calabash_in;
    }

    public String getsame_as_before() {
        return same_as_before;
    }

    public String getsame_as_before_check() {
        return same_as_before_check;
    }

    public String getsame_as_before_in() {
        return same_as_before_in;
    }

    public String getcoffeestrong() {
        return coffeestrong;
    }

    public String getcoffeestrong_check() {
        return coffeestrong_check;
    }

    public String getcoffeestrong_in() {
        return coffeestrong_in;
    }

    public String getyoungcoffee() {
        return youngcoffee;
    }

    public String getyoungcoffee_check() {
        return youngcoffee_check;
    }

    public String getyoungcoffee_in() {
        return youngcoffee_in;
    }
}