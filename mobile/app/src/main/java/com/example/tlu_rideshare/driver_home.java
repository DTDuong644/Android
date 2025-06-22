package com.example.tlu_rideshare;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.adapter.TripAdapter;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class driver_home extends AppCompatActivity {
    private RecyclerView recyclerViewTrips;
    private TripAdapter tripAdapter;
    private List<Trip> tripList;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_driver_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerViewTrips = findViewById(R.id.recyclerViewTrips);
        recyclerViewTrips.setLayoutManager(new LinearLayoutManager(this));

        tripList = new ArrayList<>();
        tripAdapter = new TripAdapter(tripList);
        recyclerViewTrips.setAdapter(tripAdapter);

        db = FirebaseFirestore.getInstance();

        loadTripsFromFirebase();
    }
    private void loadTripsFromFirebase() {
        db.collection("trips")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        tripList.clear(); // Xoá cũ, load mới
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Trip trip = doc.toObject(Trip.class);
                            tripList.add(trip);
                        }
                        tripAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(driver_home.this, "Lỗi khi đọc dữ liệu!", Toast.LENGTH_SHORT).show();
                        Log.e("FirebaseError", "Lỗi: ", task.getException());
                    }
                });
    }
}