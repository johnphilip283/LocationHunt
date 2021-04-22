package edu.neu.madcourse.locationhunt;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.content.ContextCompat;

public class LocationService {

    public final Context ctx;
    private final LocationManager manager;

    public LocationService(Context ctx) {
        this.ctx = ctx;
        this.manager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }

    public Location getCurrentLocation() {

        boolean gpsEnabled = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (gpsEnabled) {
            if (ContextCompat.checkSelfPermission(ctx,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                return manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        return null;
    }
}
