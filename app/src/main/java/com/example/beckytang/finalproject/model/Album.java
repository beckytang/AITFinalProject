package com.example.beckytang.finalproject.model;

import com.google.android.gms.maps.model.LatLng;

public class Album {

    private String name;
    private LatLng location;

    public Album() {
    }

    public Album(String name, LatLng location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
