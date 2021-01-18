package com.example.vmsandroid;

public class IterateIncident {
    private String mName;
    private String mUnit;
    private String mIncidentTitle;
    private String mIncidentDate;
    private String mIncidentRemarks;
    private int mIncidentImage;

    public IterateIncident (String Name, String Unit, String IncidentTitle, String IncidentDate, String IncidentRemarks, int IncidentImage) {
        mName = Name;
        mUnit = Unit;
        mIncidentTitle = IncidentTitle;
        mIncidentDate = IncidentDate;
        mIncidentRemarks = IncidentRemarks;
        mIncidentImage = IncidentImage;
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

    public int getIncidentImage(){
        return mIncidentImage;
    }

}
