package com.example.beckytang.finalproject;

import android.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.example.beckytang.finalproject.location.MyLocationManager;
import com.example.beckytang.finalproject.model.Album;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        MyLocationManager.OnLocChaned {

    private GoogleMap mMap;
    private MyLocationManager myLocationManager = null;
    private Location myLocation = null;
    private List<Album> albumList;
    private boolean firstLocationUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        firstLocationUpdate = true;

        albumList = new ArrayList<>();
        albumList.add(new Album("AIT", new LatLng(47.562441, 19.054716)));
        albumList.add(new Album("Metro", new LatLng(47.565648, 19.049949)));
        albumList.add(new Album("Budapest", new LatLng(47.509176, 19.076193)));
        albumList.add(new Album("Hungary", new LatLng(47.355107, 19.059543)));
        albumList.add(new Album("Greenwich", new LatLng(0, 0)));

        myLocationManager = new MyLocationManager(this, getApplicationContext());
        requestNeededPermission();


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
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
                // If request is cancelled, the result arrays are empty.
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

        mMap.addMarker(new MarkerOptions().position(mLatLng).title("Current Location"));
        if (firstLocationUpdate) {
            firstLocationUpdate = false;
            mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLng));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        }

        showAlbumsInRange(10000);
    }

    private void showAlbumsInRange(int range) {
        for (Album album : albumList) {
            if (withinRange(album.getLocation(), range)) {
                mMap.addMarker(new MarkerOptions()
                        .position(album.getLocation())
                        .title(album.getName()));
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

}