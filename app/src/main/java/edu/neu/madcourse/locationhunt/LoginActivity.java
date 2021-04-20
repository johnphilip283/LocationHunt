package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDatabase = FirebaseDatabase.getInstance("https://locationhunt-aa615-default-rtdb.firebaseio.com").getReference();
    }

    public void login(View view) {

        
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}