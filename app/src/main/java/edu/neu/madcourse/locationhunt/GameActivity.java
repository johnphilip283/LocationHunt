package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.util.ArrayUtils;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import java.text.DecimalFormat;

import edu.neu.madcourse.locationhunt.models.Constants;
import edu.neu.madcourse.locationhunt.models.Hunt;
import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class GameActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private double destLng;
    private double destLat;
    private double curLng;
    private double curLat;

    private DatabaseReference huntRef;

    private double distFromDest;
    private String destinationName;
    private String hint;
    private TextView distFromDestText;
    private double startTimestamp;

    List<Hunt> hunts;
    private TextView hintText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        SharedPreferences sharedPreferences = getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("Username", "");
        startTimestamp = new Timestamp(System.currentTimeMillis()).getTime();
        huntRef = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("hunts");

        huntRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    GenericTypeIndicator<List<Hunt>> t = new GenericTypeIndicator<List<Hunt>>() {};
                    hunts = task.getResult().getValue(t);
                    if (hunts == null) {
                        hunts = new ArrayList<>();
                    }
                }
            }
        });

        Intent intent = getIntent();

        destLat = intent.getDoubleExtra("Latitude", Constants.DEFAULT_CURRENT_LAT);
        destLng = intent.getDoubleExtra("Longitude", Constants.DEFAULT_CURRENT_LNG);
        destinationName = intent.getStringExtra("Name");
        hint = intent.getStringExtra("Hint");

        mapView = findViewById(R.id.mapView);
        distFromDestText = findViewById(R.id.distance_from_dest_text);
        hintText = findViewById(R.id.hint_text);
        
        hintText.setText(getString(R.string.info_display, "Hint", hint));
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

                            double endTimestamp = new Timestamp(System.currentTimeMillis()).getTime();
                            double duration = (endTimestamp - startTimestamp) / 1000;

                            HuntLocation destination = new HuntLocation(hint, destLat, destLng, destinationName);

                            Hunt hunt = new Hunt(startTimestamp, duration, destination);
                            hunts.add(hunt);
                            huntRef.setValue(hunts);

                            Toast.makeText(GameActivity.this, "You found the destination!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(GameActivity.this, EndGameActivity.class);
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