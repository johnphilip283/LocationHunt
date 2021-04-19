package edu.neu.madcourse.locationhunt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateAccActivity extends AppCompatActivity {

    private final String SERVER_KEY = "key=AAAAZ5n7XB8:APA91bFrywGMYLKWVCR7dwa3U2x7MdAqeXsF8fzsTcRoPvVV3C2EBCzOjgAd6db37IlI4ZI-SQLBHFyud5-ny3Ev3eCJr4sdKt_XVTCva-im9iR0dpwmwsMhhTwPkeIQiYn9EykdbvC_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_acc);
    }

    public void createAccount(View view) {
        // TODO need firebase(?) code for creating account
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}