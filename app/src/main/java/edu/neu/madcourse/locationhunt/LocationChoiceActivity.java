package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
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

import static edu.neu.madcourse.locationhunt.models.Constants.DEFAULT_CURRENT_LAT;
import static edu.neu.madcourse.locationhunt.models.Constants.DEFAULT_CURRENT_LNG;
import static edu.neu.madcourse.locationhunt.models.Constants.MILES_PER_METER;

public class LocationChoiceActivity extends AppCompatActivity {

    private SeekBar distanceSeekBar;
    private TextView distanceFilterText;
    private DatabaseReference locationDb;
    private List<HuntLocation> allLocations;
    private List<HuntLocation> locations;
    private RecyclerView recyclerView;
    private LocationRviewAdapter rviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_choice);

        locationDb = FirebaseDatabase.getInstance().getReference();
        locationDb.child("locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<HuntLocation>> t = new GenericTypeIndicator<List<HuntLocation>>() {};
                LocationChoiceActivity.this.allLocations = snapshot.getValue(t);
                LocationChoiceActivity.this.locations = snapshot.getValue(t);
                createRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LocationChoiceActivity.this, "Unable to fetch locations from database.", Toast.LENGTH_SHORT).show();
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
                currentLocation.setLatitude(DEFAULT_CURRENT_LAT);
                currentLocation.setLongitude(DEFAULT_CURRENT_LNG);

                double distance;
                locations.clear();

                List<HuntLocation> filteredLocations = new ArrayList<>();

                for (HuntLocation location : allLocations) {

                    Location huntLocation = location.getLocation();
                    distance = huntLocation.distanceTo(currentLocation);

                    distance = (float) Math.round(MILES_PER_METER * distance) / 10;

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

        rviewAdapter = new LocationRviewAdapter(locations, this);
        LocationClickListener locationClickListener = new LocationClickListener() {
            @Override
            public void onLocationClick(HuntLocation location) {
                Intent intent = new Intent(LocationChoiceActivity.this, LocationInfoActivity.class);

                intent.putExtra("Latitude", location.getLatitude());
                intent.putExtra("Longitude", location.getLongitude());
                intent.putExtra("Hint", location.getHint());
                intent.putExtra("Name", location.getName());

                startActivity(intent);
            }
        };

        rviewAdapter.setOnLinkClickListener(locationClickListener);
        recyclerView.setAdapter(rviewAdapter);

        recyclerView.setLayoutManager(rLayoutManager);
    }

}