package com.example.vmsandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class qrData {

    @SerializedName("checkin")
    @Expose
    private String checkin;

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

}