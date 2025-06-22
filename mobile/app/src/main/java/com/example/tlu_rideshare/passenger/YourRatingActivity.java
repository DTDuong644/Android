package com.example.tlu_rideshare.passenger;

import android.content.Intent;
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

import java.util.ArrayList;

public class YourRatingActivity extends AppCompatActivity {

    private TextView tvRatingScore;
    private RecyclerView recyclerView;
    private ArrayList<FeedBack> feedbackList = new ArrayList<>();
    private ArrayList<Trip> tripList = new ArrayList<>();
    private YourRatingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_rating_layout);

        tvRatingScore = findViewById(R.id.tvRatingScore);
        recyclerView = findViewById(R.id.recyclerView);
        ImageView imgBackYourRating = findViewById(R.id.imgBackYourRating);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ✅ Nhận dữ liệu từ Intent
        tripList = getIntent().getParcelableArrayListExtra("tripList");
        if (tripList == null) tripList = new ArrayList<>();

        feedbackList = (ArrayList<FeedBack>) getIntent().getSerializableExtra("feedbackList");
        if (feedbackList == null) feedbackList = new ArrayList<>();

        adapter = new YourRatingAdapter(feedbackList, tripList);
        recyclerView.setAdapter(adapter);

        if (imgBackYourRating != null) {
            imgBackYourRating.setOnClickListener(v -> onBackPressed());
        }

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

        updateTotalFeedbackCount();
    }

    private void updateTotalFeedbackCount() {
        tvRatingScore.setText(String.valueOf(feedbackList.size()));
    }

    // ✅ Gửi lại dữ liệu khi quay về
    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("tripList", tripList);
        resultIntent.putExtra("feedbackList", feedbackList);
        setResult(RESULT_OK, resultIntent);
        super.onBackPressed();
    }
}
