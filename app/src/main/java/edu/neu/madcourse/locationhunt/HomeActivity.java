package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

import edu.neu.madcourse.locationhunt.models.Hunt;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;
    private TextView greeting;
    private String username;
    private TextView totalPoints;
    private DatabaseReference userHuntDb;
    private List<Hunt> hunts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        greeting = findViewById(R.id.greeting);
        totalPoints = findViewById(R.id.total_points);

        SharedPreferences sharedPref = this.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
        username = sharedPref.getString("Username", "friend");

        userHuntDb = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("hunts");
        userHuntDb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Hunt>> t = new GenericTypeIndicator<List<Hunt>>() {};
                hunts = snapshot.getValue(t);
                int total = 0;
                if (hunts != null) {
                    for (Hunt hunt: hunts) {
                        total += hunt.getScore();
                    }
                }
                totalPoints.setText(getString(R.string.info_display, "Total Points", total + ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        greeting.setText(getString(R.string.greeting, username));
    }

    public void startAHunt(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, LocationChoiceActivity.class);
            startActivity(intent);
        } else {
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_CODE);
        }
    }


    public void viewPastHunts(View view) {
        Intent intent = new Intent(this, PreviousHuntsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission is granted
                    Intent intent = new Intent(this, LocationChoiceActivity.class);
                    startActivity(intent);
                }  else {
                    // permission denied
                    Log.e("permission_error", "permission not granted " + Arrays.toString(grantResults));
                    Toast.makeText(this, "Location services permission not granted", Toast.LENGTH_LONG).show();
                }
        }

    }


}