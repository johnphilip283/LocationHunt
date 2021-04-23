package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import edu.neu.madcourse.locationhunt.models.User;

public class CreateAccActivity extends AppCompatActivity {

    private final String SERVER_KEY = "key=AAAAZ5n7XB8:APA91bFrywGMYLKWVCR7dwa3U2x7MdAqeXsF8fzsTcRoPvVV3C2EBCzOjgAd6db37IlI4ZI-SQLBHFyud5-ny3Ev3eCJr4sdKt_XVTCva-im9iR0dpwmwsMhhTwPkeIQiYn9EykdbvC_";
    private DatabaseReference mDatabase;

    private TextView newUsername;
    private TextView newPassword;
    private String clientToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        newPassword = findViewById(R.id.create_account_password_field);
        newUsername = findViewById(R.id.create_account_username_field);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(CreateAccActivity.this, token -> {
            this.clientToken = token;
        });
    }

    public void createAccount(View view) {

        String passwordTxt = newPassword.getText().toString().trim();
        String usernameTxt = newUsername.getText().toString().trim();

        if (usernameTxt.equals("") || passwordTxt.equals("")) {
            Toast.makeText(getApplicationContext(), "Need to provide a valid username and password.", Toast.LENGTH_SHORT).show();
        } else {
            User newUser = new User(usernameTxt, passwordTxt, clientToken);
            mDatabase.child("users").child(usernameTxt).setValue(newUser);
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }

    }
}