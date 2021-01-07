package com.example.vmsandroid;

import android.widget.ImageView;

public class qrentry {
    private String oriname ;
    private String oriic ;
    private String oriaddress ;
    private String name ;
    private String ic ;
    private String address ;
    private ImageView qrimage;

    public qrentry(String oriname, String oriic, String oriaddress, String name, String ic, String address) {
        this.oriname = oriname;
        this.oriic = oriic;
        this.oriaddress = oriaddress;
        this.name = name;
        this.ic = ic;
        this.address = address;
    }
}
