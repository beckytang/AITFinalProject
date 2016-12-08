package com.example.beckytang.finalproject.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class Album implements Serializable{

    private String name;
    private LatLng location;
    private List<Photo> photoList;

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

    public List<Photo> getPhotoList() {
        return photoList;
    }

    public void setPhotoList(List<Photo> photoList) {
        this.photoList = photoList;
    }
}
