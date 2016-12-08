package com.example.beckytang.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

    private String name;
    private String userUid; // use for finding all photos from one person

    public Photo() {
    }

    public Photo(String name, String userUid) {
        this.name = name;
        this.userUid = userUid;
    }

    protected Photo(Parcel in) {
        name = in.readString();
        userUid = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(userUid);
    }
}
