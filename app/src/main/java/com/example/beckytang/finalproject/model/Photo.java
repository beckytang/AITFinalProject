package com.example.beckytang.finalproject.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Photo implements Parcelable {

    private String photoName;
    private String userUid;
    private String url;

    public Photo() {
    }

    protected Photo(Parcel in) {
        photoName = in.readString();
        userUid = in.readString();
        url = in.readString();
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

    public String getPhotoName() {
        return photoName;
    }

    public void setPhotoName(String photoName) {
        this.photoName = photoName;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(photoName);
        dest.writeString(userUid);
        dest.writeString(url);
    }

}
