package com.example.vmsandroid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class qrList {

    @SerializedName("qrstatus")
    @Expose
    private List<qrData> qrstatus = null;

    public List<qrData> getQrstatus() {
        return qrstatus;
    }

    public void setQrstatus(List<qrData> qrstatus) {
        this.qrstatus = qrstatus;
    }

}