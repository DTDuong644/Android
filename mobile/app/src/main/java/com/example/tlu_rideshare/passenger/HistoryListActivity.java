package com.example.tlu_rideshare.passenger;

import static com.example.tlu_rideshare.passenger.YourRatingAdapter.getFeedback;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.FeedBack;
import com.example.tlu_rideshare.model.Trip;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryListActivity extends AppCompatActivity {

    private static final int RATING_REQUEST_CODE = 1001;
    private static final String PREF_RATED_TRIPS = "rated_trips";

    private static HistoryListActivity instance;

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter adapter;
    private List<Trip> tripHistoryList;
    private List<FeedBack> feedbackList;
    private Button btnSearchNewTrip;
    private ImageView imageViewBack;

    private TripViewModel tripViewModel;

    public static HistoryListActivity getInstance() {
        return instance;
    }

    public List<Trip> getTripHistoryList() {
        return tripHistoryList;
    }

    public List<FeedBack> getFeedbackList() {
        return feedbackList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.history_list_layout);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnSearchNewTrip = findViewById(R.id.btnSearchNewTrip);
        imageViewBack = findViewById(R.id.imageView);

        tripHistoryList = new ArrayList<>();
        feedbackList = new ArrayList<>();

        adapter = new HistoryAdapter(tripHistoryList, this::showRatingDialog);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        tripViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        tripViewModel.getTripList().observe(this, trips -> {
            tripHistoryList.clear();
            for (Trip trip : trips) {
                trip.setRated(isTripRated(trip.getTripID())); // Kiểm tra trạng thái đánh giá
                tripHistoryList.add(trip);
            }
            adapter.notifyDataSetChanged();
        });

        btnSearchNewTrip.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selected_tab", 1);
            startActivity(intent);
            finish();
        });

        imageViewBack.setOnClickListener(v -> onBackPressed());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    private void showRatingDialog(Trip trip, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_rating_layout, null);
        builder.setView(dialogView);

        RadioGroup radioGroup = dialogView.findViewById(R.id.radioGroupRating);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);

        AlertDialog dialog = builder.create();

        btnSubmit.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            if (selectedId == -1) {
                Toast.makeText(this, "Vui lòng chọn mức đánh giá", Toast.LENGTH_SHORT).show();
                return;
            }

            int rating = getRatingFromRadio(selectedId);
            String content = getFeedback(rating);

            FeedBack feedback = new FeedBack();
            feedback.setFeedBackID("fb_" + System.currentTimeMillis());
            feedback.setTripID(trip.getTripID());
            String driverID = trip.getTripID() + "_driver";
            feedback.setDriverID(driverID); // Hoặc driverID nếu có
            feedback.setUserID("user_demo");
            feedback.setContent(content);
            feedback.setRating(rating);
            feedback.setTime(new Timestamp(new Date().getTime()));

            addFeedback(feedback);
            trip.setRated(true);
            saveRatedTrip(trip.getTripID());
            adapter.notifyItemChanged(position);

            Toast.makeText(this, "Cảm ơn bạn đã đánh giá!", Toast.LENGTH_SHORT).show();
            adapter.markTripAsRated(trip.getTripID());
            Intent intent = new Intent(this, YourRatingActivity.class);
            intent.putParcelableArrayListExtra("tripList", new ArrayList<>(tripHistoryList));
            startActivityForResult(intent, RATING_REQUEST_CODE);

            dialog.dismiss();
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }

    private int getRatingFromRadio(int selectedId) {
        if (selectedId == R.id.rb1) return 1;
        if (selectedId == R.id.rb2) return 2;
        if (selectedId == R.id.rb3) return 3;
        if (selectedId == R.id.rb4) return 4;
        if (selectedId == R.id.rb5) return 5;
        return 0;
    }

    private void addFeedback(FeedBack feedback) {
        feedbackList.add(feedback);
        Set<FeedBack> set = new HashSet<>(feedbackList);
        feedbackList.clear();
        feedbackList.addAll(set);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RATING_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<Trip> updatedTripList = data.getParcelableArrayListExtra("tripList");
            if (updatedTripList != null) {
                tripHistoryList.clear();
                tripHistoryList.addAll(updatedTripList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    private void saveRatedTrip(String tripID) {
        SharedPreferences prefs = getSharedPreferences(PREF_RATED_TRIPS, MODE_PRIVATE);
        prefs.edit().putBoolean(tripID, true).apply();
    }

    private boolean isTripRated(String tripID) {
        SharedPreferences prefs = getSharedPreferences(PREF_RATED_TRIPS, MODE_PRIVATE);
        return prefs.getBoolean(tripID, false);
    }
}
