package com.example.tlu_rideshare;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.adapter.TripAdapter;
import com.example.tlu_rideshare.model.Trip;

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
        tripList.clear();
        tripList.add(new Trip("1", "Hà Nội", "Hưng Yên", "16/05/2025", "15:00", 4, 2));
        tripList.add(new Trip("2", "Hà Nội", "Nam Định", "17/05/2025", "08:30", 5, 1));
        tripList.add(new Trip("3", "Hà Nội", "Thái Bình", "18/05/2025", "09:15", 3, 3));
        tripList.add(new Trip("4", "Hà Nội", "Bắc Giang", "19/05/2025", "13:00", 4, 0));
        tripAdapter.notifyDataSetChanged();
    }
}