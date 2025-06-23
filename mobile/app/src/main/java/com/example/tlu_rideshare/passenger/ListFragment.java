package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private Button btnSearch, btnHistory;
    private RecyclerView recyclerView;
    private ArrayAdapterListTrip adapter;
    private final List<Trip> tripList = new ArrayList<>();
    private final List<Booking> bookingList = new ArrayList<>();
    private TripViewModel tripViewModel;

    private final String currentUserId = "user_demo";  // ID người dùng hiện tại

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_passenger_layout, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);
        recyclerView = view.findViewById(R.id.recycleViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArrayAdapterListTrip(tripList, bookingList);
        recyclerView.setAdapter(adapter);

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(1);
            }
        });

        btnHistory = view.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HistoryListActivity.class);
            startActivity(intent);
        });

        // Quan sát Trip list một lần duy nhất
        tripViewModel.getTripList().observe(getViewLifecycleOwner(), this::filterTripsFromBookingList);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadBookingsFromFirebase(); // Gọi lại khi fragment quay lại
    }

    public void reloadBookings() {
        loadBookingsFromFirebase();
    }

    private void loadBookingsFromFirebase() {
        FirebaseFirestore.getInstance().collection("bookings")
                .whereEqualTo("userID", currentUserId)
                .addSnapshotListener((snapshots, error) -> {
                    if (error != null) {
                        Log.e("ListFragment", "Lỗi Firestore: " + error.getMessage());
                        return;
                    }

                    bookingList.clear();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        Booking booking = doc.toObject(Booking.class);
                        if (!"cancel".equals(booking.getStatus())) {
                            bookingList.add(booking);
                        }
                    }

                    // Lấy danh sách chuyến hiện tại rồi lọc
                    filterTripsFromBookingList(tripViewModel.getTripList().getValue());
                });
    }

    private void filterTripsFromBookingList(List<Trip> trips) {
        if (trips == null || !isAdded()) return;

        tripList.clear();
        for (Trip trip : trips) {
            // Bỏ qua trip có status là cancel hoặc completed
            String status = trip.getStatus() != null ? trip.getStatus().toLowerCase() : "";
            if (status.equals("cancel") || status.equals("completed")) {
                continue;
            }

            for (Booking booking : bookingList) {
                if (booking.getTripID().equals(trip.getTripID())) {
                    tripList.add(trip);
                    break;
                }
            }
        }

        adapter.notifyDataSetChanged();
    }

}
