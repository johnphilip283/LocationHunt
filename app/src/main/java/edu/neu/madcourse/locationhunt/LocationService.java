package edu.neu.madcourse.locationhunt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

public class LocationService {

    public final Context ctx;
    private final LocationManager manager;
    private final LocationListener listener;

    public LocationService(Context ctx) {
        this.ctx = ctx;
        this.manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        this.listener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };
    }

    public LocationService(Context ctx, LocationListener listener) {
        this.ctx = ctx;
        this.listener = listener;
        this.manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getCurrentLocation() {

        boolean gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsEnabled) {
            if (ContextCompat.checkSelfPermission(ctx,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                manager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000 * 5, // 5 seconds between updates
                        0, // don't require a change in distance to update
                        listener);

                return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        return null;
    }

}
