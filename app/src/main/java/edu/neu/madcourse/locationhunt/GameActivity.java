package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;

import edu.neu.madcourse.locationhunt.models.Constants;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double destLng;
    private double destLat;
    private double curLng;
    private double curLat;
    private double distFromDest;
    private String destinationName;
    private String hint;
    private TextView distFromDestText;
    private TextView hintText;
    private GameActivity curGame = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        destLat = intent.getDoubleExtra("Latitude", Constants.DEFAULT_CURRENT_LAT);
        destLng = intent.getDoubleExtra("Longitude", Constants.DEFAULT_CURRENT_LNG);
        destinationName = intent.getStringExtra("Name");
        hint = intent.getStringExtra("Hint");

        mapView = findViewById(R.id.mapView);
        distFromDestText = findViewById(R.id.distance_from_dest_text);
        hintText = findViewById(R.id.hint_text);
        hintText.setText(new StringBuilder().append("Hint: ").append(hint).toString());
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        getLocationUpdates(googleMap);
        startLocationUpdates();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private void getLocationUpdates(GoogleMap gMap) {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(30000);
        this.locationRequest.setFastestInterval(20000);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        this.locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (!locationResult.getLocations().isEmpty()) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        curLat = location.getLatitude();
                        curLng = location.getLongitude();
                        LatLng latLng = new LatLng(curLat, curLng);
                        // check if latLng matches close to destination latLng
                        distFromDest = distBtwnCoordinates(curLat, curLng, destLat, destLng, "M");
                        DecimalFormat df = new DecimalFormat("#.##");
                        distFromDestText.setText("Distance from destination (miles):\n" + df.format(distFromDest));
                        if (distFromDest < 0.01) {
                            Toast.makeText(curGame, "You found the destination!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(curGame, EndGameActivity.class);
                            startActivity(intent);
                        }
                        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
                        gMap.addMarker(markerOptions);
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f));
                    }
                }
            }
        };
    }

    // Distance between two coordinates reference: https://dzone.com/articles/distance-calculation-using-3
    private double distBtwnCoordinates(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(degToRad(lat1)) * Math.sin(degToRad(lat2)) + Math.cos(degToRad(lat1)) * Math.cos(degToRad(lat2)) * Math.cos(degToRad(theta));
        dist = Math.acos(dist);
        dist = radToDeg(dist);
        dist = dist * 60 * 1.1515;
        if (unit.equals("K")) {
            dist = dist * 1.609344;
        } else if (unit.equals("M")) {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private double degToRad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double radToDeg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.fusedLocationClient.requestLocationUpdates(
                this.locationRequest,
                this.locationCallback,
                null);
    }

}