package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.locationhunt.models.HuntLocation;

public class InitialStreetViewActivity extends AppCompatActivity {

    private SeekBar distanceSeekBar;
    private TextView distanceFilterText;
    private DatabaseReference locationDb;
    private List<HuntLocation> allLocations;
    private List<HuntLocation> locations;
    private RecyclerView recyclerView;
    private RviewAdapter rviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_street_view);

        locationDb = FirebaseDatabase.getInstance().getReference();
        locationDb.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<HuntLocation>> t = new GenericTypeIndicator<List<HuntLocation>>() {};
                InitialStreetViewActivity.this.allLocations = snapshot.getValue(t);
                InitialStreetViewActivity.this.locations = snapshot.getValue(t);
                createRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(InitialStreetViewActivity.this, "Unable to fetch locations from database.", Toast.LENGTH_SHORT).show();
            }
        });

        distanceSeekBar = findViewById(R.id.distance_seek_bar);
        distanceFilterText = findViewById(R.id.distance_filter_text);

        distanceFilterText.setText(getString(R.string.distance_filter_text, distanceSeekBar.getProgress() + ""));
        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceFilterText.setText(getString(R.string.distance_filter_text, progress + ""));

                Location currentLocation = new Location("");
                currentLocation.setLatitude(42.33035768454704);
                currentLocation.setLongitude(-71.09758758572562);

                double distance;
                locations.clear();

                List<HuntLocation> filteredLocations = new ArrayList<>();

                for (HuntLocation location : allLocations) {

                    Location huntLocation = location.getLocation();
                    distance = huntLocation.distanceTo(currentLocation);

                    distance = (float) Math.round(0.00621371 * distance) / 10;

                    if (distance < progress) {
                        filteredLocations.add(location);
                    }
                }

                locations.addAll(filteredLocations);
                rviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.location_recycler_view);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new RviewAdapter(locations, this);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }


    public void startHunt(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}