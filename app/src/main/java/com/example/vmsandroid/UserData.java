package com.example.vmsandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("unit")
    @Expose
    private String unit;
    @SerializedName("ic")
    @Expose
    private String ic;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String username) {
        this.unit = username;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String username) {
        this.ic = username;
    }

}