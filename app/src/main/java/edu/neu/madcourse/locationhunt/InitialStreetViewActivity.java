package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InitialStreetViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_street_view);
    }

    public void startHunt(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}