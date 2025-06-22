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
import com.example.tlu_rideshare.model.Trip;

import java.text.Normalizer;
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
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private TextView tvFrom, tvTo, tvDate;

    public SuitableTripFragment() {}

    private String normalize(String str) {
        if (str == null) return "";
        str = Normalizer.normalize(str, Normalizer.Form.NFD);
        return str.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase().trim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.suitable_trip_layout, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        recyclerView = view.findViewById(R.id.recyclerViewTrips);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        tvFrom = view.findViewById(R.id.tvFrom);
        tvTo = view.findViewById(R.id.tvTo);
        tvDate = view.findViewById(R.id.tvDate);

        tripList = new ArrayList<>();
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
                String selectedDateStr = tripViewModel.getSelectedDate().getValue();
                String yourLocation = tripViewModel.getYourLocation().getValue();
                String destination = tripViewModel.getDestination().getValue();

                if (selectedDateStr == null || yourLocation == null || destination == null) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin để tìm chuyến xe", Toast.LENGTH_LONG).show();
                    return;
                }

                String userFrom = normalize(yourLocation);
                String userTo = normalize(destination);

                List<Trip> filteredTrips = new ArrayList<>();

                try {
                    Date selectedDate = dateFormat.parse(selectedDateStr);
                    Date today = dateFormat.parse(dateFormat.format(new Date())); // chỉ lấy ngày hiện tại

                    for (Trip trip : trips) {
                        String tripDateStr = trip.getDate() != null ? trip.getDate().trim() : "";
                        String tripTime = trip.getTime() != null ? trip.getTime().trim() : "";
                        String tripFrom = normalize(trip.getFromLocation());
                        String tripTo = normalize(trip.getToLocation());

                        if (!tripFrom.contains(userFrom)) continue;
                        if (!tripTo.contains(userTo)) continue;

                        Date tripDate = dateFormat.parse(tripDateStr);
                        if (tripDate == null || selectedDate == null || tripDate.before(selectedDate)) continue;

                        // Nếu là hôm nay thì lọc theo giờ
                        if (tripDate.equals(today)) {
                            Date currentTime = timeFormat.parse(timeFormat.format(new Date()));
                            Date tripTimeDate = timeFormat.parse(tripTime);
                            if (tripTimeDate != null && tripTimeDate.after(currentTime)) {
                                filteredTrips.add(trip);
                            }
                        } else {
                            filteredTrips.add(trip);
                        }
                    }

                } catch (ParseException e) {
                    Log.e("SuitableTripFragment", "Lỗi phân tích ngày/giờ", e);
                }

                tripList.clear();
                tripList.addAll(filteredTrips);
                adapter.notifyDataSetChanged();

                Log.d("SuitableTripFragment", "✅ Số chuyến hợp lệ: " + filteredTrips.size());

                if (tripList.isEmpty()) {
                    Toast.makeText(getContext(), "Không có chuyến xe nào phù hợp", Toast.LENGTH_LONG).show();
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
