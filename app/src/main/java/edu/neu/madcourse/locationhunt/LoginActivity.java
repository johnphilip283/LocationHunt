package edu.neu.madcourse.locationhunt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.madcourse.locationhunt.models.User;

public class LoginActivity extends AppCompatActivity {

    private DatabaseReference userDb;
    private TextView username;
    private TextView password;
    private String clientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.login_username_field);
        password = findViewById(R.id.login_password_field);
        userDb = FirebaseDatabase.getInstance("https://locationhunt-aa615-default-rtdb.firebaseio.com").getReference();

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(LoginActivity.this, token -> {
            this.clientToken = token;
        });
    }

    public void login(View view) {

        String usernameTxt = username.getText().toString().trim();
        String passwordTxt = password.getText().toString().trim();

        if (usernameTxt.equals("") || passwordTxt.equals("")) {
            Toast.makeText(getApplicationContext(), "Need to provide a valid username and password.", Toast.LENGTH_SHORT).show();
        }

        userDb.child("users").child(usernameTxt).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                } else {

                    User requestedUser = task.getResult().getValue(User.class);

                    if (requestedUser.getPassword().equals(passwordTxt)) {
                        SharedPreferences sharedPref = LoginActivity.this.getSharedPreferences("userDetails", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();

                        editor.putString("Username", usernameTxt);
                        editor.putString("Client Token", clientToken);
                        editor.apply();

                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }
}