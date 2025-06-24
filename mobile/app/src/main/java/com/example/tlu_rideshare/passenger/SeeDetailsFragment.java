package com.example.tlu_rideshare.passenger;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.tlu_rideshare.MainActivity;
import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SeeDetailsFragment extends Fragment {

    private TextView tvDate, tvDriverID, tvRating, tvTime, tvPrice, tvPickup, tvDropoff, tvLicensePlate;
    private ImageView imgBack;
    private Button btnBook;
    private TripViewModel tripViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.see_details_layout, container, false);

        tripViewModel = new ViewModelProvider(requireActivity()).get(TripViewModel.class);

        imgBack = view.findViewById(R.id.imageView2);
        tvDate = view.findViewById(R.id.tvDate);
        tvDriverID = view.findViewById(R.id.tvDriverName);
        tvRating = view.findViewById(R.id.tvRide);
        tvTime = view.findViewById(R.id.tvTime);
        tvPrice = view.findViewById(R.id.tvPrice);
        tvPickup = view.findViewById(R.id.tvPickup);
        tvDropoff = view.findViewById(R.id.tvDropoff);
        tvLicensePlate = view.findViewById(R.id.tvLicensePlate);
        btnBook = view.findViewById(R.id.btnBook);

        Bundle bundle = getArguments();
        Trip trip = bundle != null ? bundle.getParcelable("trip") : null;

        if (trip != null) {
            tvDate.setText("Ngày: " + trip.getDate());
            tvTime.setText("Thời gian khởi hành: " + trip.getTime());
            tvRating.setText("Đánh giá: 5.0 ★");
            tvPrice.setText("Giá: " + trip.getPrice() + " VND");
            tvPickup.setText("Điểm đón: " + trip.getFromLocation());
            tvDropoff.setText("Điểm trả: " + trip.getToLocation());
            tvLicensePlate.setText("Biển số xe: " + trip.getLicensePlate());

            // Load tên tài xế từ Firestore
            loadDriverInfo(trip.getDriverID(), tvDriverID, null);
        }

        Trip finalTrip = trip;

        imgBack.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        btnBook.setOnClickListener(v -> {
            if (finalTrip == null) {
                Toast.makeText(getContext(), "Không có thông tin chuyến đi!", Toast.LENGTH_SHORT).show();
                return;
            }

            String currentUserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
            if (finalTrip.getDriverID().equals(currentUserID)) {
                Toast.makeText(getContext(), "Bạn không thể đặt chuyến của chính mình!", Toast.LENGTH_SHORT).show();
                return;
            }

            showBookingDialog(finalTrip, currentUserID);
        });

        return view;
    }

    private void showBookingDialog(Trip trip, String userID) {
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

        dialogLicensePlate.setText("Biển số xe: " + trip.getLicensePlate());
        dialogDriverImage.setImageResource(R.drawable.avatar);

        // Load tên và số điện thoại tài xế
        loadDriverInfo(trip.getDriverID(), dialogDriverID, dialogPhoneNumber);

        int seatsLeft = trip.getSeatsAvailable() - trip.getSeatsBooked();
        if ("Ô tô".equals(trip.getVihicleType()) && seatsLeft > 0) {
            passengerInputContainer.setVisibility(View.VISIBLE);
            editTextPassengers.setText("1");
        } else {
            passengerInputContainer.setVisibility(View.GONE);
        }

        btnCloseDialog.setOnClickListener(v -> dialog.dismiss());

        btnContact.setOnClickListener(v -> {
            if (dialogPhoneNumber.getText() != null) {
                String phoneText = dialogPhoneNumber.getText().toString().trim();
                String phoneNumber = phoneText.replace("Số điện thoại:", "").trim();  // Xoá tiền tố nếu có

                if (!phoneNumber.isEmpty()) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(android.net.Uri.parse("tel:" + phoneNumber));
                    if (requireContext().checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                            == android.content.pm.PackageManager.PERMISSION_GRANTED) {
                        startActivity(callIntent);
                    } else {
                        Toast.makeText(getContext(), "Ứng dụng chưa được cấp quyền gọi điện", Toast.LENGTH_SHORT).show();
                        // 👉 Có thể yêu cầu quyền nếu muốn tự động
                    }
                } else {
                    Toast.makeText(getContext(), "Số điện thoại không hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnConfirmBooking.setOnClickListener(v -> {
            int passengers = 1;
            if ("Ô tô".equals(trip.getVihicleType())) {
                String input = editTextPassengers.getText().toString().trim();
                if (input.isEmpty()) {
                    editTextPassengers.setError("Vui lòng nhập số người!");
                    return;
                }
                try {
                    passengers = Integer.parseInt(input);
                    if (passengers < 1 || passengers > seatsLeft) {
                        editTextPassengers.setError("Số người không hợp lệ!");
                        return;
                    }
                } catch (NumberFormatException e) {
                    editTextPassengers.setError("Số người không hợp lệ!");
                    return;
                }
            }

            Booking booking = new Booking();
            booking.setBookingID("book_" + System.currentTimeMillis());
            booking.setTripID(trip.getTripID());
            booking.setUserID(userID);
            booking.setBookingTime(Timestamp.now());
            booking.setStatus("confirm");
            booking.setSeatsBooked(passengers);

            int finalPassengers = passengers;
            FirebaseFirestore.getInstance().collection("bookings")
                    .document(booking.getBookingID())
                    .set(booking)
                    .addOnSuccessListener(unused -> {
                        FirebaseFirestore.getInstance().collection("trips")
                                .document(trip.getTripID())
                                .update("seatsBooked", com.google.firebase.firestore.FieldValue.increment(finalPassengers));

                        Toast.makeText(getContext(), "Đặt xe thành công!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        trip.setSeatsBooked(trip.getSeatsBooked() + finalPassengers);
                        tripViewModel.updateTrip(trip);

                        if (getActivity() instanceof MainActivity) {
                            ((MainActivity) getActivity()).switchToTab(2);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(getContext(), "Đặt xe thất bại!", Toast.LENGTH_SHORT).show();
                    });
        });

        dialog.show();
    }

    // Lấy tên + số điện thoại từ Firestore dựa vào driverID
    private void loadDriverInfo(String driverID, TextView nameView, @Nullable TextView phoneView) {
        FirebaseFirestore.getInstance().collection("users")
                .document(driverID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String name = documentSnapshot.getString("fullName");
                    String phone = documentSnapshot.getString("phoneNumber");

                    if (nameView != null) {
                        nameView.setText("Tài xế: " + (name != null ? name : driverID));
                    }

                    if (phoneView != null) {
                        phoneView.setText("Số điện thoại: " + (phone != null ? phone : "Chưa cập nhật"));
                    }
                })
                .addOnFailureListener(e -> {
                    if (nameView != null) nameView.setText("Tài xế: " + driverID);
                    if (phoneView != null) phoneView.setText("Số điện thoại: Chưa cập nhật");
                });
    }
}
