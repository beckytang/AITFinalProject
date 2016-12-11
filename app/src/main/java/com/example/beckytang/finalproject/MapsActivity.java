package com.example.beckytang.finalproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.beckytang.finalproject.location.MyLocationManager;
import com.example.beckytang.finalproject.model.Album;
import com.example.beckytang.finalproject.model.AlbumList;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        MyLocationManager.OnLocChaned, GoogleMap.OnMarkerClickListener {

    public static final String KEY_ALBUM = "KEY_ALBUM";
    public static final int RANGE = 10000;
    private GoogleMap mMap;
    private MyLocationManager myLocationManager = null;
    private Location myLocation = null;
    private boolean firstLocationUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firstLocationUpdate = true;

        myLocationManager = new MyLocationManager(this, getApplicationContext());
        requestNeededPermission();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        // ADD LOADING UNITL FIRST LOCATION UPDATE

    }

    public void requestNeededPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(MapsActivity.this,
                        "I need it for gps", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    101);
        } else {
            myLocationManager.startLocationMonitoring();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MapsActivity.this, "FINELOC perm granted", Toast.LENGTH_SHORT).show();

                    myLocationManager.startLocationMonitoring();

                } else {
                    Toast.makeText(MapsActivity.this,
                            "FINE perm NOT granted", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocationManager.stopLocationMonitoring();
    }

    @Override
    public void locationChanged(Location location) {
        LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        myLocation = location;

        mMap.clear();

        // mMap.setMyLocationEnabled(true);
        // ADD CURRENT LOCATION MARKER
        if (firstLocationUpdate) {
            firstLocationUpdate = false;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        }

        showAlbumsInRange(RANGE);
    }

    private void showAlbumsInRange(int range) {
        for (Album album : AlbumList.getList()) {
            if (withinRange(album.getLocation(), range)) {
                Marker mMarker = mMap.addMarker(new MarkerOptions()
                        .position(album.getLocation())
                        .title(album.getName()));
                mMarker.showInfoWindow();
                mMarker.setTag(AlbumList.getList().indexOf(album));
            }
        }
    }

    private boolean withinRange(LatLng album, int range) {
        double myLat = myLocation.getLatitude();
        double myLng = myLocation.getLongitude();
        double lat = album.latitude;
        double lng = album.longitude;

        float[] results = new float[1];
        Location.distanceBetween(myLat, myLng, lat, lng, results);

        return results[0] <= range;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        int albumPos = (int) marker.getTag();

        Intent result = new Intent();
        result.putExtra(KEY_ALBUM, albumPos);
        setResult(RESULT_OK, result);
        finish();

        return true;
    }
}
