package com.example.tlu_rideshare.passenger;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MoveFragment extends Fragment implements SelectLocationAdapter.OnLocationSelectedListener {

    private Button btnFromLocation, btnToLocation, btnTime, btnSearch;
    private Trip trip;
    private TripViewModel tripViewModel;
    private final Calendar selectedDate = Calendar.getInstance();

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    public MoveFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.move_fragment_passenger_layout, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        btnFromLocation = view.findViewById(R.id.btnYourLocation);
        btnToLocation = view.findViewById(R.id.btnDestination);
        btnTime = view.findViewById(R.id.btnTime);
        btnSearch = view.findViewById(R.id.buttonSearch);

        trip = new Trip();
        trip.setFromLocation("");
        trip.setToLocation("");
        trip.setDate("");
        trip.setTime("");

        btnFromLocation.setOnClickListener(v -> {
            LocationDialogFragment dialog = LocationDialogFragment.newInstance(true, trip);
            dialog.setOnLocationSelectedListener(this);
            dialog.show(getParentFragmentManager(), "select_from");
        });

        btnToLocation.setOnClickListener(v -> {
            LocationDialogFragment dialog = LocationDialogFragment.newInstance(false, trip);
            dialog.setOnLocationSelectedListener(this);
            dialog.show(getParentFragmentManager(), "select_to");
        });

        btnTime.setOnClickListener(this::showDatePicker);

        btnSearch.setOnClickListener(v -> {
            if (validateTripData()) {
                sendTripDataAndNavigate();
            }
        });

        return view;
    }

    private void showDatePicker(View view) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(requireContext(),
                (DatePicker view1, int y, int m, int d) -> {
                    selectedDate.set(y, m, d);
                    Date selected = selectedDate.getTime();
                    String selectedDateStr = dateFormat.format(selected);

                    String selectedTime;
                    if (dateFormat.format(new Date()).equals(selectedDateStr)) {
                        selectedTime = timeFormat.format(new Date());
                    } else {
                        selectedTime = "00:00";
                    }

                    trip.setDate(selectedDateStr);
                    trip.setTime(selectedTime);
                    btnTime.setText(selectedDateStr + " " + selectedTime);
                }, year, month, day);

        datePicker.show();
    }

    private boolean validateTripData() {
        if (trip.getFromLocation().isEmpty() ||
                trip.getToLocation().isEmpty() ||
                trip.getDate().isEmpty() ||
                trip.getTime().isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn đầy đủ thông tin chuyến đi!", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Date current = dateFormat.parse(dateFormat.format(new Date()));
            Date selected = dateFormat.parse(trip.getDate());
            if (selected != null && selected.before(current)) {
                Toast.makeText(getContext(), "Không thể chọn ngày trong quá khứ!", Toast.LENGTH_SHORT).show();
                return false;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Lỗi xử lý ngày!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void sendTripDataAndNavigate() {
        tripViewModel.setYourLocation(trip.getFromLocation());
        tripViewModel.setDestination(trip.getToLocation());
        tripViewModel.setSelectedDate(trip.getDate());

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.tabContent, new SuitableTripFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onLocationSelected(String location, boolean isFromLocation) {
        if (isFromLocation) {
            trip.setFromLocation(location);
            btnFromLocation.setText(location);
        } else {
            trip.setToLocation(location);
            btnToLocation.setText(location);
        }
    }
}
