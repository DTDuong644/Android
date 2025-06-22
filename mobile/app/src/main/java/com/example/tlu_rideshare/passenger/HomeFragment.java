package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
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
import com.example.tlu_rideshare.model.Trip;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayAdapterTrip adapter;
    private List<Trip> tripList;
    private TripViewModel tripViewModel;
    private SimpleDateFormat dateTimeFormat;

    public HomeFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment_passenger_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tripList = new ArrayList<>();
        adapter = new ArrayAdapterTrip(tripList, requireActivity());
        recyclerView.setAdapter(adapter);

        dateTimeFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault());

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        tripViewModel.getTripList().observe(getViewLifecycleOwner(), trips -> {
            if (trips != null) {
                List<Trip> filteredTrips = new ArrayList<>();
                for (Trip trip : trips) {
                    if (isTripOnCurrentDateAndAfterCurrentTime(trip) && !trip.isUserCreated()) {
                        filteredTrips.add(trip);
                    }
                }
                tripList.clear();
                tripList.addAll(filteredTrips);
                adapter.notifyDataSetChanged();
            }
        });

        Button btn_searchTrip = view.findViewById(R.id.btn_searchTrip);
        btn_searchTrip.setOnClickListener(view1 -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchToTab(1);
            }
        });

        return view;
    }

    private boolean isTripOnCurrentDateAndAfterCurrentTime(Trip trip) {
        try {
            Date now = new Date();
            String fullTripDateTime = trip.getTime() + ", " + trip.getDate();
            Date tripDateTime = dateTimeFormat.parse(fullTripDateTime);
            return tripDateTime != null && tripDateTime.after(now);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
}
