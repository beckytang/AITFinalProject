package com.example.beckytang.finalproject.model;

public class Photo {

    private String name;
    private String userUid; // use for finding all photos from one person

    public Photo() {
    }

    public Photo(String name, String userUid) {
        this.name = name;
        this.userUid = userUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
