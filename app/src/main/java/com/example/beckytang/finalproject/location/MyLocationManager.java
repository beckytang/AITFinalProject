package com.example.beckytang.finalproject.location;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

public class MyLocationManager implements LocationListener {

    private OnLocChaned onLocChaned;
    private Context context;
    private LocationManager locationManager = null;

    public MyLocationManager(OnLocChaned onLocChaned, Context context) {
        this.onLocChaned = onLocChaned;
        this.context = context;
    }

    public void startLocationMonitoring() {
        locationManager = (LocationManager) context.getSystemService(
                Context.LOCATION_SERVICE);
        try {
            if (ActivityCompat.checkSelfPermission(context,
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    0, 0, this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, this);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void stopLocationMonitoring() {
        try {
            locationManager.removeUpdates(this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        onLocChaned.locationChanged(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public interface OnLocChaned {
        public void locationChanged(Location location);
    }

}
