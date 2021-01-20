package com.example.vmsandroid;

public class IterateIncidentUser {
    private String mTitle;
    private String mDate;
    private String mDescription;
    private String mStatus;

    public IterateIncidentUser (String Title, String Date, String Description, String Status){
        mTitle = Title;
        mDate = Date;
        mDescription = Description;
        mStatus = Status;
    }



    public String getTitle() { return mTitle; }

    public String getDate() {return mDate; }

    public String getDescription() { return mDescription; }

    public String getStatus() { return mStatus; }

}


