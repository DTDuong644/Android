package com.example.tlu_rideshare.passenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private Button btnSearch;
    private Button btnHistory;
    private RecyclerView recyclerView;
    private ArrayAdapterListTrip adapter;
    private List<Trip> tripList;
    private TripViewModel tripViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_passenger_layout, container, false);

        // Khởi tạo ViewModel
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        recyclerView = view.findViewById(R.id.recycleViewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo danh sách chuyến đi
        tripList = new ArrayList<>();

        // Set adapter
        adapter = new ArrayAdapterListTrip(tripList);
        recyclerView.setAdapter(adapter);

        // Quan sát thay đổi từ ViewModel, chỉ hiển thị chuyến đi do người dùng tạo
        tripViewModel.getTripList().observe(getViewLifecycleOwner(), trips -> {
            if (trips != null) {
                tripList.clear();
                for (Trip trip : trips) {
                    if (trip.isUserCreated()) { // Chỉ thêm các chuyến đi do người dùng tạo
                        tripList.add(trip);
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(v -> {
            if (getActivity() instanceof HomeActivity) {
                ((HomeActivity) getActivity()).switchToTab(1);
            }
        });

        btnHistory = view.findViewById(R.id.btnHistory);
        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), HistoryListActivity.class);
            startActivity(intent);
        });

        return view;
    }
}