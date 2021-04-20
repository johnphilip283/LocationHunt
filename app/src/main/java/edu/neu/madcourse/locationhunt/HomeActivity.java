package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Arrays;

public class HomeActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startAHunt(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this, InitialStreetViewActivity.class);
            startActivity(intent);
        } else {
            requestPermissions(new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    REQUEST_CODE);
        }
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
                    Intent intent = new Intent(this, InitialStreetViewActivity.class);
                    startActivity(intent);
                }  else {
                    // permission denied
                    Log.e("permission_error", "permission not granted " + Arrays.toString(grantResults));
                    Toast.makeText(this, "Location services permission not granted", Toast.LENGTH_LONG).show();
                }
        }

    }
}