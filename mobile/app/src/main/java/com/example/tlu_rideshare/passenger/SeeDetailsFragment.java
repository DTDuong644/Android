package com.example.tlu_rideshare.passenger;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class SeeDetailsFragment extends Fragment {

    private TextView tvDate, tvDriverID, tvRating, tvTime, tvPrice, tvPickup, tvDropoff, tvLicensePlate;
    private ImageView imgBack;
    private Button btnBook;
    private TripViewModel tripViewModel;
    private List<Booking> bookingList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_details_layout, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        imgBack = view.findViewById(R.id.imageView2);
        tvDate = view.findViewById(R.id.tvDate);
        tvDriverID = view.findViewById(R.id.tvDriverName); // Hiển thị driverID thay vì driverName
        tvRating = view.findViewById(R.id.tvRide);
        tvTime = view.findViewById(R.id.tvTime);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvPickup = view.findViewById(R.id.tvPickup);
        tvDropoff = view.findViewById(R.id.tvDropoff);
        tvLicensePlate = view.findViewById(R.id.tvLicensePlate);
        btnBook = view.findViewById(R.id.btnBook);

        Bundle bundle = getArguments();
        Trip trip = null;
        if (bundle != null) {
            trip = bundle.getParcelable("trip");
            if (trip != null) {
                tvDate.setText("Ngày: " + (trip.getDate() != null ? trip.getDate() : "Không xác định"));
                tvTime.setText("Thời gian khởi hành: " + (trip.getTime() != null ? trip.getTime() : "Không xác định"));
                tvDriverID.setText("Tài xế ID: " + trip.getDriverID());
                tvRating.setText("Đánh giá: 5.0 ★");
                tvPrice.setText("Giá: " + trip.getPrice() + " VND");
                tvPickup.setText("Điểm đón: " + (trip.getFromLocation() != null ? trip.getFromLocation() : "Không xác định"));
                tvDropoff.setText("Điểm trả: " + (trip.getToLocation() != null ? trip.getToLocation() : "Không xác định"));
                tvLicensePlate.setText("Biển số xe: " + (trip.getLicensePlate() != null ? trip.getLicensePlate() : "Không xác định"));
            }
        }

        final Trip finalTrip = trip;

        imgBack.setOnClickListener(v -> {
            if (getParentFragmentManager().getBackStackEntryCount() > 0) {
                getParentFragmentManager().popBackStack();
            } else {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.tabContent, new HomeFragment())
                        .commit();
            }
        });

        btnBook.setOnClickListener(v -> {
            if (finalTrip == null) {
                Toast.makeText(getContext(), "Không có thông tin chuyến đi!", Toast.LENGTH_SHORT).show();
                return;
            }

            Dialog dialog = new Dialog(requireContext());
            dialog.setContentView(R.layout.dialog_booking_layout);

            ImageView dialogDriverImage = dialog.findViewById(R.id.dialogDriverImage);
            TextView dialogDriverID = dialog.findViewById(R.id.dialogDriverName);
            TextView dialogPhoneNumber = dialog.findViewById(R.id.dialogPhoneNumber);
            TextView dialogLicensePlate = dialog.findViewById(R.id.dialogLicensePlate);
            LinearLayout passengerInputContainer = dialog.findViewById(R.id.passengerInputContainer);
            EditText editTextPassengers = dialog.findViewById(R.id.editTextPassengers);
            Button btnContact = dialog.findViewById(R.id.btnContact);
            Button btnConfirmBooking = dialog.findViewById(R.id.btnConfirmBooking);
            ImageView btnCloseDialog = dialog.findViewById(R.id.btnCloseDialog);

            dialogDriverID.setText("Tài xế ID: " + finalTrip.getDriverID());
            dialogPhoneNumber.setText("Số điện thoại: Chưa có"); // Bạn có thể thêm trường phone sau
            dialogLicensePlate.setText("Biển số xe: " + finalTrip.getLicensePlate());
            dialogDriverImage.setImageResource(R.drawable.avatar);

            int seatsLeft = finalTrip.getSeatsAvailable() - finalTrip.getSeatsBooked();

            if ("Ô tô".equals(finalTrip.getVihicleType()) && seatsLeft > 0) {
                passengerInputContainer.setVisibility(View.VISIBLE);
                editTextPassengers.setText("1");
            } else {
                passengerInputContainer.setVisibility(View.GONE);
            }

            btnCloseDialog.setOnClickListener(v1 -> dialog.dismiss());

            btnContact.setOnClickListener(v1 -> {
                Toast.makeText(getContext(), "Tài xế chưa cập nhật số điện thoại", Toast.LENGTH_SHORT).show();
            });

            btnConfirmBooking.setOnClickListener(v1 -> {
                int passengersTemp = 1;
                if ("Ô tô".equals(finalTrip.getVihicleType())) {
                    String input = editTextPassengers.getText().toString().trim();
                    if (input.isEmpty()) {
                        editTextPassengers.setError("Vui lòng nhập số người!");
                        return;
                    }
                    try {
                        passengersTemp = Integer.parseInt(input);
                        if (passengersTemp < 1 || passengersTemp > seatsLeft) {
                            editTextPassengers.setError("Số người không hợp lệ!");
                            return;
                        }
                    } catch (NumberFormatException e) {
                        editTextPassengers.setError("Số người không hợp lệ!");
                        return;
                    }
                }

                final int passengers = passengersTemp;

                Booking booking = new Booking();
                booking.setBookingID("book_" + System.currentTimeMillis());
                booking.setTripID(finalTrip.getTripID());
                booking.setUserID("user_demo");
                booking.setBookingTime(Timestamp.now());
                booking.setStatus("confirm");
                booking.setSeatsBooked(passengers);

                FirebaseFirestore.getInstance()
                        .collection("bookings")
                        .document(booking.getBookingID())
                        .set(booking)
                        .addOnSuccessListener(unused -> {
                            finalTrip.setSeatsBooked(finalTrip.getSeatsBooked() + passengers);
                            FirebaseFirestore.getInstance()
                                    .collection("trips")
                                    .document(finalTrip.getTripID())
                                    .update("seatsBooked", com.google.firebase.firestore.FieldValue.increment(passengers));

                            Toast.makeText(getContext(), "Đặt xe thành công cho " + passengers + " người!", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            // Sau khi cập nhật Trip xong
                            tripViewModel.updateTrip(finalTrip);

                            // Gọi lại filterTrips trong ListFragment nếu đang hiện ListFragment
                            Fragment currentFragment = getParentFragmentManager().findFragmentById(R.id.tabContent);
                            if (currentFragment instanceof ListFragment) {
                                ((ListFragment) currentFragment).reloadBookings(); // <- bạn cần tạo hàm này
                            }

                            if (getActivity() instanceof MainActivity) {
                                ((MainActivity) getActivity()).switchToTab(2);
                            }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(getContext(), "Đặt xe thất bại!", Toast.LENGTH_SHORT).show();
                        });
            });

            dialog.show();
        });

        return view;
    }
}

