package com.example.vmsandroid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class announcementList {

    @SerializedName("announcement")
    @Expose
    private List<announcementData> announcement = null;

    public List<announcementData> getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(List<announcementData> announcement) {
        this.announcement = announcement;
    }

}