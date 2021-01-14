package com.example.vmsandroid;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class qrData {

    @SerializedName("checkin")
    @Expose
    private String checkin;
    @SerializedName("id")
    @Expose
    private String id;

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}