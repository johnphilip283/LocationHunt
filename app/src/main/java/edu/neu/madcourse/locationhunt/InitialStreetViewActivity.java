package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class InitialStreetViewActivity extends AppCompatActivity {

    private SeekBar distanceSeekBar;
    private TextView distanceFilterText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_street_view);

        distanceSeekBar = findViewById(R.id.distance_seek_bar);
        distanceFilterText = findViewById(R.id.distance_filter_text);

        distanceFilterText.setText(getString(R.string.distance_filter_text, distanceSeekBar.getProgress() + ""));

        distanceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                distanceFilterText.setText(getString(R.string.distance_filter_text, progress + ""));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void startHunt(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}