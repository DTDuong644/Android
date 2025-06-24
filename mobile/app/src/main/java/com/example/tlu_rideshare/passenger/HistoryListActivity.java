package com.example.tlu_rideshare.passenger;

import static com.example.tlu_rideshare.passenger.YourRatingAdapter.getFeedback;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.FeedBack;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import com.google.firebase.Timestamp;
import java.util.*;

public class HistoryListActivity extends AppCompatActivity {

    private final String currentUserId = "user_demo";

    private RecyclerView recyclerViewHistory;
    private HistoryAdapter adapter;
    private List<Trip> tripHistoryList;
    private List<Booking> bookingList;
    private Button btnSearchNewTrip;
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_list_layout);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnSearchNewTrip = findViewById(R.id.btnSearchNewTrip);
        imageViewBack = findViewById(R.id.imageView);

        tripHistoryList = new ArrayList<>();
        bookingList = new ArrayList<>();

        adapter = new HistoryAdapter(tripHistoryList, bookingList, this::showRatingDialog);
        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewHistory.setAdapter(adapter);

        loadCompletedTrips();

        btnSearchNewTrip.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("selected_tab", 1);
            startActivity(intent);
            finish();
        });

        imageViewBack.setOnClickListener(v -> onBackPressed());
    }

    private void loadCompletedTrips() {
        FirebaseFirestore.getInstance()
                .collection("bookings")
                .whereEqualTo("userID", currentUserId)
                .whereEqualTo("status", "confirm") // ✅ Chỉ lấy các booking đã đặt (không lấy "cancel")
                .get()
                .addOnSuccessListener(bookingSnapshots -> {
                    bookingList.clear();
                    List<String> tripIDs = new ArrayList<>();

                    for (QueryDocumentSnapshot doc : bookingSnapshots) {
                        Booking b = doc.toObject(Booking.class);
                        bookingList.add(b);
                        tripIDs.add(b.getTripID());
                    }

                    if (!tripIDs.isEmpty()) {
                        fetchCompletedTripsForBooking(tripIDs);
                    } else {
                        tripHistoryList.clear();
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void fetchCompletedTripsForBooking(List<String> tripIDs) {
        FirebaseFirestore.getInstance()
                .collection("trips")
                .whereEqualTo("status", "complete") // ✅ Chỉ lấy các trip đã hoàn thành
                .get()
                .addOnSuccessListener(tripSnapshots -> {
                    tripHistoryList.clear();
                    for (QueryDocumentSnapshot doc : tripSnapshots) {
                        Trip trip = doc.toObject(Trip.class);
                        if (tripIDs.contains(trip.getTripID())) {
                            tripHistoryList.add(trip);
                        }
                    }
                    adapter.notifyDataSetChanged();
                });
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
            feedback.setDriverID(trip.getDriverID());
            feedback.setUserID(currentUserId);
            feedback.setContent(content);
            feedback.setRating(rating);
            feedback.setTime(Timestamp.now());

            // ✅ Bước 1: Lưu feedback vào Firestore
            FirebaseFirestore.getInstance()
                    .collection("feedbacks")
                    .document(feedback.getFeedBackID())
                    .set(feedback)
                    .addOnSuccessListener(aVoid -> {
                        // ✅ Bước 2: Update rated = true cho booking
                        FirebaseFirestore.getInstance()
                                .collection("bookings")
                                .whereEqualTo("userID", currentUserId)
                                .whereEqualTo("tripID", trip.getTripID())
                                .get()
                                .addOnSuccessListener(querySnapshot -> {
                                    for (QueryDocumentSnapshot doc : querySnapshot) {
                                        doc.getReference().update("rated", true);
                                    }

                                    Toast.makeText(this, "Cảm ơn bạn đã đánh giá!", Toast.LENGTH_SHORT).show();
                                    loadCompletedTrips(); // làm mới danh sách lịch sử
                                    dialog.dismiss();
                                });
                    });
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
}
