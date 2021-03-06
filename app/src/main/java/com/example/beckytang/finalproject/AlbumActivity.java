package com.example.beckytang.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.beckytang.finalproject.adapter.GalleryRecyclerAdapter;
import com.example.beckytang.finalproject.adapter.GalleryTouchHelper;
import com.example.beckytang.finalproject.model.Album;
import com.example.beckytang.finalproject.model.AlbumList;
import com.example.beckytang.finalproject.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class AlbumActivity extends BaseActivity {
    public static final String TAG_DATA = "TAG_DATA";
    public static final String TAG_POS = "TAG_POS";
    public static final String TAG_ALBUM_NAME = "TAG_ALBUM_NAME";
    private GalleryRecyclerAdapter galleryRecyclerAdapter;
    private RecyclerView recyclerList;

    ArrayList<Photo> galleryData = new ArrayList<>();
    public List<String> photoUrls;
    private String albumName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);

        int albumPos = getIntent().getIntExtra(MainActivity.KEY_ALBUM_POS, 0);

        Album album = AlbumList.getList().get(albumPos);
        albumName = album.getName();

        setTitle(albumName);

        photoUrls = album.getPhotoUrls();

        setUpGallery();
    }

    private void setUpGallery() {
        for (String url : photoUrls) {
            Photo photo = new Photo();
            photo.setPhotoName("example");
            photo.setUrl(url);
            galleryData.add(photo);
            Log.d("TAG_URL", url);
        }

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerList.setHasFixedSize(true); //for improved performance i guess

        galleryRecyclerAdapter = new GalleryRecyclerAdapter(galleryData);
        recyclerList.setAdapter(galleryRecyclerAdapter);

        recyclerList.addOnItemTouchListener(new GalleryTouchHelper(this,
                new GalleryTouchHelper.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(AlbumActivity.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra(TAG_DATA, galleryData);
                        intent.putExtra(TAG_POS, position);
                        intent.putExtra(TAG_ALBUM_NAME, albumName);
                        startActivity(intent);

                    }
                }));
    }

}
