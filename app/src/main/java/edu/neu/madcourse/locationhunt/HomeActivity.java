package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startAHunt(View view) {
        Intent intent = new Intent(this, InitialStreetViewActivity.class);
        startActivity(intent);
    }
}