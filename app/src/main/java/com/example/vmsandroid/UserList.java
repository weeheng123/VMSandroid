package com.example.vmsandroid;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserList {

    @SerializedName("user")
    @Expose
    private List<UserData> user = null;

    public List<UserData> getUser() {
        return user;
    }

    public void setUser(List<UserData> user) {
        this.user = user;
    }

}