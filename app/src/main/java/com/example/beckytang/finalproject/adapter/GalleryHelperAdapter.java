package com.example.beckytang.finalproject.adapter;

/**
 * Created by beckytang on 10/17/16.
 */

public interface GalleryHelperAdapter {

    //since interface, don't need to implement methods
    //other class will implement them
    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);



}

