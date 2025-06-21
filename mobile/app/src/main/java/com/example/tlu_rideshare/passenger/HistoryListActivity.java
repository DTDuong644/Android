package com.example.tlu_rideshare.passenger;

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

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.ArrayList;
import java.util.List;

public class HistoryListActivity extends AppCompatActivity {
    private static HistoryListActivity instance;
    private RecyclerView recyclerViewHistory;
    private HistoryAdapter adapter;
    private List<Trip> tripHistoryList;
    private Button btnSearchNewTrip;
    private ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.history_list_layout);

        recyclerViewHistory = findViewById(R.id.recyclerViewHistory);
        btnSearchNewTrip = findViewById(R.id.btnSearchNewTrip);
        imageViewBack = findViewById(R.id.imageView);

        recyclerViewHistory.setLayoutManager(new LinearLayoutManager(this));
        tripHistoryList = new ArrayList<>();
        adapter = new HistoryAdapter(tripHistoryList, (trip, position) -> showRatingDialog(trip, position));
        recyclerViewHistory.setAdapter(adapter);

        btnSearchNewTrip.setOnClickListener(v -> {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("selected_tab", 1);
            startActivity(intent);
            finish();
        });

        imageViewBack.setOnClickListener(v -> onBackPressed());

        loadSampleData();
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
            if (selectedId != -1) {
                int rating = getRatingFromRadio(selectedId);
                trip.setRated(true);
                trip.setRating(rating);
                adapter.notifyItemChanged(position);
                Toast.makeText(this, "Đã đánh giá: " + rating, Toast.LENGTH_SHORT).show();
                updateRatingUI(trip, rating);
                dialog.dismiss();
            } else {
                Toast.makeText(this, "Vui lòng chọn một mức đánh giá", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    private int getRatingFromRadio(int selectedId) {
        if (selectedId == R.id.rb1) return 1;
        else if (selectedId == R.id.rb2) return 2;
        else if (selectedId == R.id.rb3) return 3;
        else if (selectedId == R.id.rb4) return 4;
        else if (selectedId == R.id.rb5) return 5;
        return 0;
    }

    private void updateRatingUI(Trip trip, int rating) {
        Intent intent = new Intent(this, YourRatingActivity.class);
        intent.putExtra("driverName", trip.getDriverName());
        intent.putExtra("rating", rating);
        intent.putExtra("destination", trip.getDestination());
        startActivity(intent);
    }

    private void loadSampleData() {
        Trip trip1 = new Trip(
                "Nguyễn Văn A",
                "30A-12345",
                2,
                "0123456789",
                600000,
                "Trường Đại học Thủy lợi - Hà Nội",
                "Bến xe Mỹ Đình",
                "18:00, 10/06/2025",
                "Ô tô",
                true
        );
        tripHistoryList.add(trip1);
        Trip trip2 = new Trip(
                "Nguyễn Văn A",
                "30A-12345",
                3,
                "0123456789",
                600000,
                "Trường Đại học Thủy lợi - Hà Nội",
                "Bến xe Mỹ Đình",
                "18:00, 10/06/2025",
                "Ô tô",
                true
        );
        tripHistoryList.add(trip2);
        adapter.notifyDataSetChanged();
    }

    // Thêm getter cho tripHistoryList
    public List<Trip> getTripHistoryList() {
        return tripHistoryList;
    }

    // Getter cho instance
    public static HistoryListActivity getInstance() {
        return instance;
    }
}