package com.app.itclao.model;

import com.google.gson.annotations.SerializedName;

public class tbroute {
    @SerializedName("Id")
    private String Id;

    @SerializedName("route_id")
    private String route_id;

    @SerializedName("route_name")
    private String route_name;

    @SerializedName("User_ID")
    private String User_ID;

    public String getID() {
        return Id;
    }
    public String getroute_id() {
        return route_id;
    }
    public String getroute_name() {
        return route_name;
    }
    public String getUser_ID() {
        return User_ID;
    }





}