package com.example.beckytang.finalproject.model;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Album implements Serializable{

    private String name;
    private double latitude;
    private double longitude;
    private List<String> photoUrls;

    public Album() {
    }

    public Album(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        photoUrls = new ArrayList<>();
        photoUrls.add("https://www.sitebuilderreport.com/assets/facebook-stock-up-08c6c9a855df26a3b13a34ac62bb75cc.jpg");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public LatLng getLocation() {
        return new LatLng(latitude, longitude);
    }

    public void addPhotoUrl(String url) {
        photoUrls.add(url);
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

}
