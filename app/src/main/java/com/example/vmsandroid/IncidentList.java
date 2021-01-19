
package com.example.vmsandroid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IncidentList {

    @SerializedName("incident")
    @Expose
    private List<IncidentData> incident = null;

    public List<IncidentData> getIncident() {
        return incident;
    }

    public void setIncident(List<IncidentData> incident) {
        this.incident = incident;
    }

}