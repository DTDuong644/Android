package com.example.tlu_rideshare.passenger;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tlu_rideshare.R;

public class SeeDetailsFragment extends Fragment {

    private TextView tvDate, tvDriverName, tvRating, tvTime, tvPrice, tvPickup, tvDropoff, tvLicensePlate;
    private ImageView imgBack;
    private Button btnBook;
    private TripViewModel tripViewModel;

    public SeeDetailsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_details_layout, container, false);

        // Initialize ViewModel
        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        // Map views
        imgBack = view.findViewById(R.id.imageView2);
        tvDate = view.findViewById(R.id.tvDate);
        tvDriverName = view.findViewById(R.id.tvDriverName);
        tvRating = view.findViewById(R.id.tvRide);
        tvTime = view.findViewById(R.id.tvTime);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvPickup = view.findViewById(R.id.tvPickup);
        tvDropoff = view.findViewById(R.id.tvDropoff);
        tvLicensePlate = view.findViewById(R.id.tvLicensePlate);
        btnBook = view.findViewById(R.id.btnBook);

        // Check for null views
        if (imgBack == null || tvDate == null || tvDriverName == null || tvRating == null ||
                tvTime == null || tvPrice == null || tvPickup == null || tvDropoff == null ||
                tvLicensePlate == null || btnBook == null) {
            Log.e("SeeDetailsFragment", "One or more views are null");
            return view;
        }

        // Get Trip data from Bundle
        Bundle bundle = getArguments();
        Trip trip = null;
        if (bundle != null) {
            trip = bundle.getParcelable("trip");
            if (trip != null) {
                tvDate.setText("Ngày: " + (trip.getTime() != null ? trip.getTime().split(", ")[1] : "Không xác định"));
                tvDriverName.setText("Tài xế: " + trip.getDriverName());
                tvRating.setText("Đánh giá: 5.0 ★");
                tvTime.setText("Thời gian khởi hành: " + (trip.getTime() != null ? trip.getTime().split(", ")[0] : "Không xác định"));
                tvPrice.setText("Giá: " + trip.getPrice() + " VND");
                tvPickup.setText("Điểm đón: " + (trip.getYourLocation() != null ? trip.getYourLocation() : "Không xác định"));
                tvDropoff.setText("Điểm trả: " + (trip.getDestination() != null ? trip.getDestination() : "Không xác định"));
                tvLicensePlate.setText("Biển số xe: " + (trip.getLicensePlate() != null ? trip.getLicensePlate() : "Không xác định"));
            }
        }

        // Store trip for use in dialog
        final Trip finalTrip = trip;

        // Back button click
        imgBack.setOnClickListener(v -> {
            Log.d("SeeDetailsFragment", "Back button clicked, back stack count: " + getParentFragmentManager().getBackStackEntryCount());
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            } else {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.tabContent, new HomeFragment())
                        .commit();
            }
        });

        // Book button click
        btnBook.setOnClickListener(v -> {
            if (finalTrip == null) {
                Toast.makeText(getContext(), "Không có thông tin chuyến đi!", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create and show dialog
            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.dialog_booking_layout);

            // Map dialog views
            ImageView dialogDriverImage = dialog.findViewById(R.id.dialogDriverImage);
            TextView dialogDriverName = dialog.findViewById(R.id.dialogDriverName);
            TextView dialogPhoneNumber = dialog.findViewById(R.id.dialogPhoneNumber);
            TextView dialogLicensePlate = dialog.findViewById(R.id.dialogLicensePlate);
            LinearLayout passengerInputContainer = dialog.findViewById(R.id.passengerInputContainer);
            EditText editTextPassengers = dialog.findViewById(R.id.editTextPassengers);
            Button btnContact = dialog.findViewById(R.id.btnContact);
            Button btnConfirmBooking = dialog.findViewById(R.id.btnConfirmBooking);
            ImageView btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);

            // Set dialog data
            dialogDriverName.setText("Tài xế: " + finalTrip.getDriverName());
            dialogPhoneNumber.setText("Số điện thoại: " + finalTrip.getPhoneNumber());
            dialogLicensePlate.setText("Biển số xe: " + finalTrip.getLicensePlate());
            dialogDriverImage.setImageResource(R.drawable.avatar);

            // Check vehicle type
            if ("Ô tô".equals(finalTrip.getVehicle()) && finalTrip.getEmptyChair() > 0) {
                // Show EditText for cars
                passengerInputContainer.setVisibility(View.VISIBLE);
                editTextPassengers.setText("1"); // Default to 1 passenger
            } else {
                // Hide EditText for motorcycles or no seats
                passengerInputContainer.setVisibility(View.GONE);
            }

            // Close button
            btnCloseDialog.setOnClickListener(v1 -> dialog.dismiss());

            // Contact button
            btnContact.setOnClickListener(v1 -> {
                if (finalTrip.getPhoneNumber() == null || finalTrip.getPhoneNumber().isEmpty()) {
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + finalTrip.getPhoneNumber()));
                startActivity(intent);
            });

            // Confirm booking button
            btnConfirmBooking.setOnClickListener(v1 -> {
                if (finalTrip.getEmptyChair() <= 0) {
                    Toast.makeText(getContext(), "Không còn ghế trống!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    return;
                }

                // Determine number of passengers
                int passengers = 1; // Default for motorcycles
                if ("Ô tô".equals(finalTrip.getVehicle())) {
                    String input = editTextPassengers.getText().toString().trim();
                    if (input.isEmpty()) {
                        editTextPassengers.setError("Vui lòng nhập số người!");
                        return;
                    }
                    try {
                        passengers = Integer.parseInt(input);
                        if (passengers < 1) {
                            editTextPassengers.setError("Số người phải lớn hơn 0!");
                            return;
                        }
                        if (passengers > finalTrip.getEmptyChair()) {
                            editTextPassengers.setError("Số người vượt quá ghế trống (" + finalTrip.getEmptyChair() + ")!");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        editTextPassengers.setError("Số người không hợp lệ!");
                        return;
                    }
                }

                // Update trip
                finalTrip.setUserCreated(true);
                finalTrip.setEmptyChair(finalTrip.getEmptyChair() - passengers);

                // Update TripViewModel
                tripViewModel.updateTrip(finalTrip);

                Toast.makeText(getContext(), "Đặt xe thành công cho " + passengers + " người!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                // Navigate to ListFragment
                if (getActivity() instanceof HomeActivity) {
                    ((HomeActivity) getActivity()).switchToTab(2);
                }
            });

            dialog.show();
        });

        return view;
    }
}