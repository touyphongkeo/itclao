package com.app.itclao.model;
import com.google.gson.annotations.SerializedName;
public class office {
    @SerializedName("Id")
    private String Id;

    @SerializedName("office_name")
    private String office_name;

    @SerializedName("office_logo")
    private String office_logo;

    @SerializedName("path")
    private String path;

    @SerializedName("signature1")
    private String signature1;

    @SerializedName("signature2")
    private String signature2;

    @SerializedName("signature3")
    private String signature3;

    @SerializedName("signature4")
    private String signature4;

    @SerializedName("signature5")
    private String signature5;

    @SerializedName("office_logo2")
    private String office_logo2;

    @SerializedName("path2")
    private String path2;

    public String getID() {
        return Id;
    }

    public String getoffice_name() {
        return office_name;
    }

    public String getoffice_logo() {
        return office_logo;
    }

    public String getpath() {
        return path;
    }

    public String getsignature1() {
        return signature1;
    }

    public String getsignature2() {
        return signature2;
    }

    public String getsignature3() {
        return signature3;
    }

    public String getsignature4() {
        return signature4;
    }

    public String getsignature5() {
        return signature5;
    }

    public String getoffice_logo2() {
        return office_logo2;
    }

    public String getpath2() {
        return path2;
    }
}