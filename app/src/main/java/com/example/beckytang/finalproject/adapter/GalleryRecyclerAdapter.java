package com.example.beckytang.finalproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.beckytang.finalproject.R;
import com.example.beckytang.finalproject.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class GalleryRecyclerAdapter extends
        RecyclerView.Adapter<GalleryRecyclerAdapter.ViewHolder> {

    private List<Photo> photoList;
    private Context context;

    public GalleryRecyclerAdapter(ArrayList<Photo> galleryData) {
        photoList = galleryData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View listRow = LayoutInflater.from(context).inflate(R.layout.list_photo, parent, false);
        return new ViewHolder(listRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Glide.with(context).load(photoList.get(position).getUrl())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(((ViewHolder) holder).mImg);
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImg;

        public ViewHolder(View itemView) {
            super(itemView);
            mImg = (ImageView) itemView.findViewById(R.id.ivListPhoto);
        }
    }

}
