package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout chứa nội dung trang chủ
        View view = inflater.inflate(R.layout.home_fragment_passenger_layout, container, false);
        // Setup RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Sample data
        List<Trip> tripList = new ArrayList<>();
        tripList.add(new Trip("Nguyễn Văn A", "08:00", 300000, "Xe máy"));
        tripList.add(new Trip("Trần Văn B", "09:30", 250000, "Ô tô"));
        tripList.add(new Trip("Lê Thị C", "10:45", 280000, "Xe máy"));

        // Set adapter
        ArrayAdapterTrip adapter = new ArrayAdapterTrip(tripList);
        recyclerView.setAdapter(adapter);

        Button btn_searchTrip = view.findViewById(R.id.btn_searchTrip);
        btn_searchTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof HomeActivity) {
                    ((HomeActivity) getActivity()).switchToTab(1);
                }
            }
        });

        return view;
    }
}
