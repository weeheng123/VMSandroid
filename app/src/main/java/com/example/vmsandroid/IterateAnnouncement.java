package com.example.vmsandroid;

public class IterateAnnouncement {
    private String mTitle;
    private String mDate;
    private String mDescription;

    public IterateAnnouncement(String Title, String Date, String Description){
        mTitle = Title;
        mDate = Date;
        mDescription = Description;
    }

    public String getTitle(){
        return mTitle;
    }

    public String getDate(){
        return mDate;
    }

    public String getDescription(){
        return mDescription;
    }
}
