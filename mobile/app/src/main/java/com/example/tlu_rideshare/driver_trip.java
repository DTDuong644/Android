package com.example.tlu_rideshare;

import android.os.Bundle;
import android.util.Log;

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

public class driver_trip extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TripAdapter tripAdapter;
    private List<Trip> tripList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_trip);

        recyclerView = findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tripList = new ArrayList<>();
        tripAdapter = new TripAdapter(tripList);
        recyclerView.setAdapter(tripAdapter);

        loadTrips(); // load data demo
    }

    private void loadTrips() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        tripList.clear();

        db.collection("trips")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Trip trip = document.toObject(Trip.class);
                            tripList.add(trip);
                        }
                        tripAdapter.notifyDataSetChanged();
                    } else {
                        Log.e("Firebase", "Lỗi khi đọc dữ liệu: ", task.getException());
                    }
                });
    }
}