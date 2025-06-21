package com.example.tlu_rideshare.passenger;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class YourRatingActivity extends AppCompatActivity {
    private TextView tvRatingScore;
    private RecyclerView recyclerView;
    private List<Trip> ratedTrips = new ArrayList<>();
    private YourRatingAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.your_rating_layout);

        tvRatingScore = findViewById(R.id.tvRatingScore);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView imgBackYourRating = findViewById(R.id.imgBackYourRating);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new YourRatingAdapter(ratedTrips);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        String driverName = intent.getStringExtra("driverName");
        int rating = intent.getIntExtra("rating", 0);
        String destination = intent.getStringExtra("destination");

        // Thêm dữ liệu từ Intent nếu hợp lệ
        if (driverName != null && rating > 0) {
            Trip newTrip = new Trip(driverName, "", 0, "", 0, "", destination, "", "", false);
            newTrip.setRating(rating);
            newTrip.setRated(true);
            // Kiểm tra trùng lặp dựa trên driverName, destination, và rating
            boolean exists = ratedTrips.stream().anyMatch(t ->
                    t.getDriverName().equals(driverName) &&
                            t.getDestination().equals(destination) &&
                            t.getRating() == rating);
            if (!exists) {
                ratedTrips.add(newTrip);
                adapter.notifyDataSetChanged();
            }
        }

        // Tải toàn bộ danh sách từ HistoryListActivity và loại bỏ trùng lặp
        loadRatedTrips();
        removeDuplicates();
        updateAverageRating();

        // Xử lý sự kiện nhấn nút back
        if (imgBackYourRating != null) {
            imgBackYourRating.setOnClickListener(v -> onBackPressed());
        } else {
            Log.e("YourRatingActivity", "imgBackYourRating not found in layout");
        }

        // Áp dụng insets
        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        } else {
            Log.e("YourRatingActivity", "main View not found in layout");
        }
    }

    private void loadRatedTrips() {
        HistoryListActivity activity = HistoryListActivity.getInstance();
        if (activity != null) {
            // Lấy tất cả các chuyến đã đánh giá từ tripHistoryList
            List<Trip> newTrips = activity.getTripHistoryList().stream()
                    .filter(Trip::isRated)
                    .collect(Collectors.toList());
            ratedTrips.addAll(newTrips);
            adapter.notifyDataSetChanged();
        }
    }

    private void removeDuplicates() {
        // Loại bỏ trùng lặp dựa trên driverName, destination, và rating
        Set<Trip> uniqueTripsSet = new HashSet<>();
        for (Trip trip : ratedTrips) {
            uniqueTripsSet.add(trip); // Sử dụng HashSet với equals và hashCode
        }
        ratedTrips.clear();
        ratedTrips.addAll(uniqueTripsSet);
        adapter.notifyDataSetChanged();
    }

    private void updateAverageRating() {
        if (ratedTrips.isEmpty()) {
            tvRatingScore.setText("0");
            return;
        }
        // Hiển thị tổng số bài đánh giá
        int totalRatings = ratedTrips.size();
        tvRatingScore.setText(String.valueOf(totalRatings));
    }
}