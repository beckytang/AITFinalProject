package com.example.beckytang.finalproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.beckytang.finalproject.MainActivity;
import com.example.beckytang.finalproject.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GalleryRecyclerAdapter extends
        RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder>
        implements GalleryHelperAdapter {

    private List<ImageModel> photoList;
    private Context context;

    public GalleryRecyclerAdapter(MainActivity mainActivity, ArrayList<ImageModel> galleryData) {
        photoList = new ArrayList<>();
        photoList = galleryData;
       // photoList = ImageModel.listAll(ImageModel.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View listRow = LayoutInflater.from(context).inflate(R.layout.list_photo,parent,false);
        //defaults to return null
        return new ViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //called by system as many times as there items in the list that android wants to load into the library

        Glide.with(context).load(photoList.get(position).getUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ViewHolder) holder).mImg);
    }


    @Override
    public int getItemCount() {
        //default to 0; need to change!
        return photoList.size();
    }

    @Override
    public void onItemDismiss(int position) {
       // photoList.get(position).delete();
        photoList.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAll() {
        //ImageModel.deleteAll(ImageModel.class);
        photoList.removeAll(photoList);

        notifyDataSetChanged();
    }


    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(photoList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(photoList, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.ivListPhoto);
        }


    }


    public void addItem(ImageModel photo) {
        //item.save();
        photoList.add(photo);
        int position = getItemCount() + 1;
        // refresh only one position
        notifyItemInserted(position);
    }



}
