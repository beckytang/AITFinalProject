package com.example.beckytang.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.beckytang.finalproject.adapter.GalleryRecyclerAdapter;
import com.example.beckytang.finalproject.adapter.GalleryTouchHelper;
import com.example.beckytang.finalproject.model.AlbumList;
import com.example.beckytang.finalproject.model.Photo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by beckytang on 12/10/16.
 */

public class AlbumActivity extends BaseActivity {
    private GalleryRecyclerAdapter galleryRecyclerAdapter;
    private RecyclerView recyclerList;

    ArrayList<Photo> galleryData = new ArrayList<>();
    public List<String> photoUrls;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_album); //not sure about this one
        String albumName = getIntent().getStringExtra(MainActivity.KEY_GALLERY_NAME);
        int albumPos = getIntent().getIntExtra(MainActivity.KEY_ALBUM_POS, 0);

        photoUrls = AlbumList.albumList.get(albumPos).getPhotoUrls();

        //find corresponding album in array
        //get urls and add to IMGS

        setUpGallery();
    }

    private void setUpGallery() {
        for (String url : photoUrls) {
            //adding images and title to POJO class and storing into array
            Photo photo = new Photo();
            photo.setPhotoName("Cloud cloud");
            photo.setUrl(url);
            galleryData.add(photo);
            Log.d("TAG_URL", url);
        }

        recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        recyclerList.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerList.setHasFixedSize(true); //for improved performance i guess

        galleryRecyclerAdapter = new GalleryRecyclerAdapter(AlbumActivity.this, galleryData);
        recyclerList.setAdapter(galleryRecyclerAdapter);

        recyclerList.addOnItemTouchListener(new GalleryTouchHelper(this,
                new GalleryTouchHelper.OnItemClickListener() {

                    @Override
                    public void onItemClick(View view, int position) {

                        Intent intent = new Intent(AlbumActivity.this, DetailActivity.class);
                        intent.putParcelableArrayListExtra("data", galleryData);
                        intent.putExtra("pos", position);
                        startActivity(intent);

                    }
                }));
    }
}
