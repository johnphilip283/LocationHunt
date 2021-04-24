package edu.neu.madcourse.locationhunt;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.madcourse.locationhunt.models.Hunt;

public class PreviousHuntsActivity extends AppCompatActivity {

    private DatabaseReference userHuntsDb;
    private List<Hunt> prevHunts;

    private RecyclerView recyclerView;
    private HuntRviewAdapter rviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_past_hunts);

        SharedPreferences sharedPreferences = this.getSharedPreferences("userDetails", Context.MODE_PRIVATE);

        String username = sharedPreferences.getString("Username", "");
        prevHunts = new ArrayList<>();
        userHuntsDb = FirebaseDatabase.getInstance().getReference().child("users").child(username).child("hunts");

        if (userHuntsDb != null) {
            userHuntsDb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    GenericTypeIndicator<List<Hunt>> t = new GenericTypeIndicator<List<Hunt>>() {};
                    prevHunts = snapshot.getValue(t);
                    createRecyclerView();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(PreviousHuntsActivity.this, "Unable to fetch locations from database.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void createRecyclerView() {
        RecyclerView.LayoutManager rLayoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.past_hunts_recycler_view);
        recyclerView.setHasFixedSize(true);

        rviewAdapter = new HuntRviewAdapter(prevHunts, this);
        recyclerView.setAdapter(rviewAdapter);
        recyclerView.setLayoutManager(rLayoutManager);
    }

}
