package edu.neu.madcourse.locationhunt;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import edu.neu.madcourse.locationhunt.models.Constants;

public class LocationInfoActivity extends AppCompatActivity {

    private TextView distance;
    private TextView hint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        hint = findViewById(R.id.location_info_hint);
        distance = findViewById(R.id.location_info_distance);

        Intent intent = getIntent();
        hint.setText(getString(R.string.location_card_hint, intent.getStringExtra("Hint")));

        double lat = intent.getDoubleExtra("Latitude", Constants.DEFAULT_CURRENT_LAT);
        double lng = intent.getDoubleExtra("Longitude",  Constants.DEFAULT_CURRENT_LNG);

        Location dest = new Location("");
        dest.setLongitude(lng);
        dest.setLatitude(lat);

        float distanceInMiles = LocationService.distanceToCurLocation(dest);

        distance.setText(getString(R.string.location_card_distance, distanceInMiles));
    }

    public void startHunt(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        Intent curIntent = getIntent();

        double lat = intent.getDoubleExtra("Latitude", Constants.DEFAULT_CURRENT_LAT);
        double lng = intent.getDoubleExtra("Longitude",  Constants.DEFAULT_CURRENT_LNG);

        intent.putExtra("Latitude", lat);
        intent.putExtra("Longitude", lng);
        intent.putExtra("Name", curIntent.getStringExtra("Name"));
        intent.putExtra("Hint", curIntent.getStringExtra("Hint"));

        startActivity(intent);
    }
}
