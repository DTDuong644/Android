package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.FeedBack;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class YourRatingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvRatingScore;
    private YourRatingAdapter adapter;
    private List<FeedBack> feedbackList = new ArrayList<>();
    private List<Trip> tripList = new ArrayList<>();

    private final String currentUserId = "user_demo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_rating_layout);

        tvRatingScore = findViewById(R.id.tvRatingScore);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ImageView imgBack = findViewById(R.id.imgBackYourRating);
        if (imgBack != null) {
            imgBack.setOnClickListener(v -> onBackPressed());
        }

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        loadFeedbacks();
    }

    private void loadFeedbacks() {
        FirebaseFirestore.getInstance()
                .collection("feedbacks")
                .whereEqualTo("userID", currentUserId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    feedbackList.clear();
                    List<String> tripIDs = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : snapshot) {
                        FeedBack fb = doc.toObject(FeedBack.class);
                        feedbackList.add(fb);
                        tripIDs.add(fb.getTripID());
                    }

                    loadTrips(tripIDs);
                })
                .addOnFailureListener(e -> Log.e("YourRating", "Load feedback failed", e));
    }

    private void loadTrips(List<String> tripIDs) {
        if (tripIDs.isEmpty()) {
            updateUI();
            return;
        }

        FirebaseFirestore.getInstance()
                .collection("trips")
                .whereIn("tripID", tripIDs)
                .get()
                .addOnSuccessListener(snapshot -> {
                    tripList.clear();
                    for (QueryDocumentSnapshot doc : snapshot) {
                        tripList.add(doc.toObject(Trip.class));
                    }
                    updateUI();
                })
                .addOnFailureListener(e -> Log.e("YourRating", "Load trips failed", e));
    }

    private void updateUI() {
        adapter = new YourRatingAdapter(feedbackList, tripList);
        recyclerView.setAdapter(adapter);
        tvRatingScore.setText(String.valueOf(feedbackList.size()));
    }
}
