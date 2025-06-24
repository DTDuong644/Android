package com.example.tlu_rideshare.passenger;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FieldValue;

import java.util.List;

public class ArrayAdapterListTrip extends RecyclerView.Adapter<ArrayAdapterListTrip.ListTripViewHolder> {
    private final List<Trip> tripList;
    private final List<Booking> bookingList;
    private final String currentUserId;

    public ArrayAdapterListTrip(List<Trip> tripList, List<Booking> bookingList) {
        this.tripList = tripList;
        this.bookingList = bookingList;
        this.currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static class ListTripViewHolder extends RecyclerView.ViewHolder {
        TextView txtSchedule, txtPhoneNumber, txtLicensePlate, txtTime, txtNumOfChair, txtPrice, txtDriverID;
        Button btnCancelTrip;

        public ListTripViewHolder(View itemView) {
            super(itemView);
            txtSchedule = itemView.findViewById(R.id.txtSchedule);
            txtDriverID = itemView.findViewById(R.id.txtNameDriver);
            txtPhoneNumber = itemView.findViewById(R.id.txtPhoneNumber);
            txtLicensePlate = itemView.findViewById(R.id.txtLicensePlate);
            txtTime = itemView.findViewById(R.id.txtTime);
            txtNumOfChair = itemView.findViewById(R.id.txtNumOfChair);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            btnCancelTrip = itemView.findViewById(R.id.btnCancelTrip);
        }
    }

    @NonNull
    @Override
    public ListTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_layout, parent, false);
        return new ListTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        Booking booking = findBookingByTripAndUser(trip.getTripID(), currentUserId);

        holder.txtSchedule.setText("Tuyến xe: " + trip.getFromLocation() + " -> " + trip.getToLocation());
        holder.txtLicensePlate.setText("Biển số xe: " + trip.getLicensePlate());
        holder.txtTime.setText("Thời gian: " + trip.getDate() + " " + trip.getTime());

        // 🔽 Truy vấn tên và số điện thoại tài xế từ Firestore
        FirebaseFirestore.getInstance()
                .collection("users")
                .document(trip.getDriverID())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String name = documentSnapshot.getString("fullName");
                        String phone = documentSnapshot.getString("phoneNumber");

                        holder.txtDriverID.setText("Tài xế: " + (name != null ? name : "Không rõ"));
                        holder.txtPhoneNumber.setText("SĐT: " + (phone != null ? phone : "Chưa cập nhật"));
                    } else {
                        holder.txtDriverID.setText("Tài xế: Không rõ");
                        holder.txtPhoneNumber.setText("SĐT: Không rõ");
                    }
                })
                .addOnFailureListener(e -> {
                    holder.txtDriverID.setText("Tài xế: Lỗi tải");
                    holder.txtPhoneNumber.setText("SĐT: Lỗi");
                });

        if (booking != null) {
            holder.txtNumOfChair.setText("Số ghế bạn đặt: " + booking.getSeatsBooked());
            holder.txtPrice.setText("Giá vé: " + (trip.getPrice() * booking.getSeatsBooked()) + " VNĐ");

            holder.btnCancelTrip.setOnClickListener(v -> {
                new AlertDialog.Builder(holder.itemView.getContext())
                        .setTitle("Xác nhận hủy")
                        .setMessage("Bạn có chắc muốn hủy chuyến này không?")
                        .setPositiveButton("Hủy chuyến", (dialog, which) -> {
                            FirebaseFirestore.getInstance()
                                    .collection("bookings")
                                    .document(booking.getBookingID())
                                    .update("status", "cancel");

                            FirebaseFirestore.getInstance()
                                    .collection("trips")
                                    .document(trip.getTripID())
                                    .update("seatsBooked", FieldValue.increment(-booking.getSeatsBooked()));

                            tripList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, tripList.size());

                            Toast.makeText(holder.itemView.getContext(), "Bạn đã hủy chuyến đi này", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Không", null)
                        .show();
            });

        } else {
            holder.txtNumOfChair.setText("Không rõ số ghế đã đặt");
            holder.txtPrice.setText("Không xác định");
            holder.btnCancelTrip.setEnabled(false);
        }
    }


    private Booking findBookingByTripAndUser(String tripID, String userID) {
        for (Booking b : bookingList) {
            if (b.getTripID().equals(tripID) && b.getUserID().equals(userID)) {
                return b;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
