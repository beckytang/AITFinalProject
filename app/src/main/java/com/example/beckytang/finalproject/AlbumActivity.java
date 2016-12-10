package com.example.beckytang.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.beckytang.finalproject.adapter.GalleryRecyclerAdapter;
import com.example.beckytang.finalproject.adapter.GalleryTouchHelper;
import com.example.beckytang.finalproject.model.Photo;

import java.util.ArrayList;

/**
 * Created by beckytang on 12/10/16.
 */

public class AlbumActivity extends BaseActivity {
    private GalleryRecyclerAdapter galleryRecyclerAdapter;
    private RecyclerView recyclerList;

    ArrayList<Photo> galleryData = new ArrayList<>();
    public static String IMGS[] = {
            "https://images.unsplash.com/photo-1444090542259-0af8fa96557e?q=80&fm=jpg&w=1080&fit=max&s=4b703b77b42e067f949d14581f35019b",
            "https://images.unsplash.com/photo-1439546743462-802cabef8e97?dpr=2&fit=crop&fm=jpg&h=725&q=50&w=1300",
            "https://images.unsplash.com/photo-1441155472722-d17942a2b76a?q=80&fm=jpg&w=1080&fit=max&s=80cb5dbcf01265bb81c5e8380e4f5cc1",
            // Your image URLs here
    };

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main); //not sure about this one
        String albumName = getIntent().getStringExtra(MainActivity.KEY_GALLERY_NAME);

        //find corresponding album in array
        //get urls and add to IMGS

        setUpGallery();
    }

    private void setUpGallery() {
        for (int i = 0; i < IMGS.length; i++) {
            //adding images and title to POJO class and storing into array
            Photo photo = new Photo();
            photo.setPhotoName("Image " + i);
            photo.setUrl(IMGS[i]);
            galleryData.add(photo);
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
        finish(); //lol does this go here
    }
}
