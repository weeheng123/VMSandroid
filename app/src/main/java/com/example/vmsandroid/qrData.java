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
    @SerializedName("checkout")
    @Expose
    private String checkout;

    public String getCheckin() {
        return checkin;
    }

    public void setCheckin(String checkin) {
        this.checkin = checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    qrData(String id, String checkin){
        this.id = id;
        this.checkin = checkin;
    }

}