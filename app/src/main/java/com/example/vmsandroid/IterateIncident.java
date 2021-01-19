package com.example.vmsandroid;

import com.google.gson.internal.$Gson$Preconditions;

public class IterateIncident {
    private String mName;
    private String mUnit;
    private String mIncidentTitle;
    private String mIncidentDate;
    private String mIncidentRemarks;
    private String mStatus;
    private int mId;

    public IterateIncident (String Name, String Unit, String IncidentTitle, String IncidentDate, String IncidentRemarks, String Status, int Id) {
        mName = Name;
        mUnit = Unit;
        mIncidentTitle = IncidentTitle;
        mIncidentDate = IncidentDate;
        mIncidentRemarks = IncidentRemarks;
        mStatus = Status;
        mId = Id;
    }

    public String getName(){
        return mName;
    }

    public String getUnit(){
        return mUnit;
    }

    public String getIncidentTitle(){
        return mIncidentTitle;
    }

    public String getIncidentDate(){
        return mIncidentDate;
    }

    public String getIncidentRemarks(){
        return mIncidentRemarks;
    }

    public String getStatus() { return mStatus; }

    public int getID() {return mId; }


}
