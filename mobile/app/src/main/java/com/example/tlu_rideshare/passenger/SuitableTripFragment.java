package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SuitableTripFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayAdapterTrip adapter;
    private List<Trip> tripList;
    private TripViewModel tripViewModel;
    private SimpleDateFormat dateTimeFormat;
    private SimpleDateFormat dateFormat;
    private TextView tvFrom, tvTo, tvDate;

    public SuitableTripFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_suitable_trip, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tvFrom = view.findViewById(R.id.tvFrom);
        tvTo = view.findViewById(R.id.tvTo);
        tvDate = view.findViewById(R.id.tvDate);

        tripList = new ArrayList<>();
        dateTimeFormat = new SimpleDateFormat("HH:mm, dd/MM/yyyy", Locale.getDefault());
        dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        // Truyền requireActivity() vào ArrayAdapterTrip
        adapter = new ArrayAdapterTrip(tripList, requireActivity());
        recyclerView.setAdapter(adapter);

        tripViewModel.getYourLocation().observe(getViewLifecycleOwner(), location -> {
            tvFrom.setText("Từ: " + (location != null ? location : ""));
        });

        tripViewModel.getDestination().observe(getViewLifecycleOwner(), dest -> {
            tvTo.setText("Đến: " + (dest != null ? dest : ""));
        });

        tripViewModel.getSelectedDate().observe(getViewLifecycleOwner(), date -> {
            tvDate.setText("Ngày: " + (date != null ? date : ""));
        });

        tripViewModel.getTripList().observe(getViewLifecycleOwner(), trips -> {
            if (trips != null) {
                String selectedDate = tripViewModel.getSelectedDate().getValue();
                String yourLocation = tripViewModel.getYourLocation().getValue();
                String destination = tripViewModel.getDestination().getValue();
                List<Trip> filteredTrips = new ArrayList<>();

                if (selectedDate != null && yourLocation != null && destination != null) {
                    Date currentDateTime = new Date();
                    String currentDateStr = dateFormat.format(currentDateTime);

                    for (Trip trip : trips) {
                        try {
                            Date tripDateTime = dateTimeFormat.parse(trip.getTime());
                            String tripDate = dateFormat.format(tripDateTime);

                            if (tripDate.equals(selectedDate) &&
                                    trip.getYourLocation() != null && trip.getYourLocation().toLowerCase().contains(yourLocation.toLowerCase()) &&
                                    trip.getDestination() != null && trip.getDestination().toLowerCase().contains(destination.toLowerCase())) {
                                if (tripDate.equals(currentDateStr)) {
                                    if (tripDateTime.after(currentDateTime)) {
                                        filteredTrips.add(trip);
                                    }
                                } else {
                                    filteredTrips.add(trip);
                                }
                            }
                        } catch (ParseException e) {
                            Log.e("SuitableTripFragment", "Parse error for time: " + trip.getTime(), e);
                        }
                    }
                } else {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin để tìm chuyến xe", Toast.LENGTH_LONG).show();
                    return;
                }

                tripList.clear();
                tripList.addAll(filteredTrips);
                Log.d("SuitableTripFragment", "Filtered trips count: " + filteredTrips.size());
                adapter.notifyDataSetChanged();

                if (tripList.isEmpty()) {
                    Toast.makeText(getContext(), "Không có chuyến xe nào trong ngày này", Toast.LENGTH_LONG).show();
                }
            }
        });

        ImageView imgBack = view.findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.tabContent, new MoveFragment())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }
}