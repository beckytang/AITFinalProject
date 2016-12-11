package com.example.beckytang.finalproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.beckytang.finalproject.model.Album;
import com.example.beckytang.finalproject.model.AlbumList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String KEY_ALBUM_POS = "KEY_ALBUM_POS";
    public static final String KEY_GALLERY_NAME = "KEY_GALLERY_NAME";
    public static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int REQUEST_CODE_ALBUM = 2;
    private ImageView ivNewPicture;
    private String mCurrentPhotoPath;
    private String fileName;
    private Album album;
    private String albumName;
    private int tookPicture = 0;
    private int accessGallery = 0;
    private int albumPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ivNewPicture = (ImageView) findViewById(R.id.ivNewPicture);

        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("albums");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(this, "Author: George Whiteside and Tang Chi Wei",
                    Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_take_photo) {
            dispatchTakePictureIntent();
        } else if (id == R.id.nav_map) {
            accessGallery = 1;
            dispatchChooseAlbumIntent();
        } else if (id == R.id.nav_slideshow) {
            Glide.with(this)
                    .load("http://www.w3schools.com/css/trolltunga.jpg")
                    .into(ivNewPicture);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.beckytang.finalproject.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_" + getUid();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        fileName = imageFileName;
        tookPicture = 1;
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                dispatchChooseAlbumIntent();
            }

            if (requestCode == REQUEST_CODE_ALBUM) {
                albumPos = data.getIntExtra(MapsActivity.KEY_ALBUM, 0);
                album = AlbumList.getList().get(albumPos);
                albumName = album.getName();
                Log.d("TAG_BLAH", albumName + "BEFORE");

                if (tookPicture == 1) {
                    tookPicture = 0;
                    try {
                        storePictureFB(fileName, mCurrentPhotoPath);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (accessGallery == 1) {
                    Log.d("TAG_BLAH", albumName + "AFTER");
                    accessGallery = 0;
                    Intent intentAlbumGallery = new Intent();
                    intentAlbumGallery.setClass(MainActivity.this, AlbumActivity.class);
                    intentAlbumGallery.putExtra(KEY_GALLERY_NAME, albumName);
                    intentAlbumGallery.putExtra(KEY_ALBUM_POS, albumPos);
                    Log.d("TAG_ENTER_ALBUM", "true");
                    startActivity(intentAlbumGallery);
                }
            }
        }
    }

    private void setIvSrc(String url) {
        Glide.with(MainActivity.this)
                .load(url)
                .into(ivNewPicture);
    }

    private void storePictureFB(String imageFileName, String currentPath)
            throws FileNotFoundException {
        showProgressDialog();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef =
                storage.getReferenceFromUrl("gs://aitfinalproject.appspot.com");
        StorageReference tempRef = storageRef
                .child("images")
                .child(album.getName())
                .child(imageFileName);

        Bitmap bmp = BitmapFactory.decodeFile(mCurrentPhotoPath);
        Matrix mat = new Matrix();
        mat.postRotate(270);
        Bitmap rtBmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                bmp.getHeight(), mat, true);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        rtBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = tempRef.putBytes(data);
        InputStream stream = new FileInputStream(new File(currentPath));
        uploadTask = tempRef.putStream(stream);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(MainActivity.this, "There was a problem uploading your photo",
                        Toast.LENGTH_SHORT).show();
                hideProgressDialog();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                addPhotoToAlbum(downloadUrl);
                setIvSrc(downloadUrl);
                hideProgressDialog();
            }
        });
    }

    private void addPhotoToAlbum(String downloadUrl) {
        album.addPhotoUrl(downloadUrl);
        DatabaseReference databaseReference =
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("albums");
        databaseReference.child(album.getName()).setValue(album);
    }

    public void dispatchChooseAlbumIntent() {
        Intent chooseAlbumIntent = new Intent(MainActivity.this, MapsActivity.class);
        startActivityForResult(chooseAlbumIntent, REQUEST_CODE_ALBUM);
    }

}
