package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayAdapterTrip adapter;
    private final List<Trip> tripList = new ArrayList<>();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final String currentUserId = "user_demo"; // 🔁 Sửa thành UID thật nếu cần
    private final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault());

    public HomeFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_passenger_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArrayAdapterTrip(tripList, requireActivity());
        recyclerView.setAdapter(adapter);

        Button btnSearchTrip = view.findViewById(R.id.btn_searchTrip);
        btnSearchTrip.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(1);
            }
        });

        loadTrips();

        return view;
    }

    private void loadTrips() {
        db.collection("bookings")
                .whereEqualTo("userID", currentUserId)
                .get()
                .addOnSuccessListener(bookingSnapshots -> {
                    if (!isAdded()) return; // ✅ Đảm bảo Fragment còn tồn tại

                    Set<String> completedTripIDs = new HashSet<>();
                    for (var doc : bookingSnapshots) {
                        Booking booking = doc.toObject(Booking.class);
                        if ("complete".equalsIgnoreCase(booking.getStatus())) {
                            completedTripIDs.add(booking.getTripID());
                        }
                    }

                    db.collection("trips")
                            .get()
                            .addOnSuccessListener(tripSnapshots -> {
                                if (!isAdded()) return; // ✅ Kiểm tra lại trước khi xử lý

                                tripList.clear();
                                String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

                                for (var doc : tripSnapshots) {
                                    Trip trip = doc.toObject(Trip.class);
                                    if (trip.getTripID() == null || trip.getStatus() == null || trip.getDate() == null)
                                        continue;

                                    boolean isToday = trip.getDate().equals(today);
                                    boolean isCanceledOrCompleted = trip.getStatus().equalsIgnoreCase("cancel") ||
                                            trip.getStatus().equalsIgnoreCase("complete");
                                    boolean isAlreadyCompletedByUser = completedTripIDs.contains(trip.getTripID());

                                    if (isToday && !isCanceledOrCompleted && !isAlreadyCompletedByUser) {
                                        tripList.add(trip);
                                    }
                                }

                                adapter.notifyDataSetChanged();

                                if (tripList.isEmpty()) {
                                    if (isAdded()) { // ✅ Tránh crash khi Toast
                                        Toast.makeText(requireContext(), "Không có chuyến đi nào trong ngày hôm nay", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(e -> Log.e("HomeFragment", "Lỗi tải trips: " + e.getMessage()));
                })
                .addOnFailureListener(e -> Log.e("HomeFragment", "Lỗi tải bookings: " + e.getMessage()));
    }


    private boolean isTripInFuture(Trip trip) {
        try {
            String fullDateTime = trip.getTime() + ", " + trip.getDate();
            Date tripDateTime = dateTimeFormat.parse(fullDateTime);
            return tripDateTime != null && tripDateTime.after(new Date());
        } catch (ParseException e) {
            Log.e("DateParse", "Lỗi phân tích ngày giờ: " + e.getMessage());
            return false;
        }
    }
}