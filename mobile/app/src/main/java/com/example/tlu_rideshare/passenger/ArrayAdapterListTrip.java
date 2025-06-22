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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Timestamp;
import java.util.List;

public class ArrayAdapterListTrip extends RecyclerView.Adapter<ArrayAdapterListTrip.ListTripViewHolder> {
    private List<Trip> tripList;
    private List<Booking> bookingList;
    private String currentUserId = "user_demo";

    public ArrayAdapterListTrip(List<Trip> tripList, List<Booking> bookingList) {
        this.tripList = tripList;
        this.bookingList = bookingList;
    }

    public static class ListTripViewHolder extends RecyclerView.ViewHolder {
        TextView txtSchedule, txtPhoneNumber, txtLicensePlate, txtTime, txtNumOfChair, txtPrice, txtDriverID;
        Button btnCancelTrip;

        public ListTripViewHolder(View itemView) {
            super(itemView);
            txtSchedule = itemView.findViewById(R.id.txtSchedule);
            txtDriverID = itemView.findViewById(R.id.txtNameDriver); // Hiển thị driverID thay cho driverName
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
    public ListTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_layout, parent, false);
        return new ListTripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListTripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        Booking booking = findBookingByTripAndUser(trip.getTripID(), currentUserId);

        holder.txtSchedule.setText("Tuyến xe: " + trip.getFromLocation() + " -> " + trip.getToLocation());
        holder.txtDriverID.setText("Tài xế: " + trip.getDriverID()); // Tạm dùng driverID vì chưa có bảng User
        holder.txtPhoneNumber.setText("SĐT: Chưa có"); // Vì không còn phoneNumber trong Trip
        holder.txtLicensePlate.setText("Biển số xe: " + trip.getLicensePlate());
        holder.txtTime.setText("Thời gian: " + trip.getDate() + " " + trip.getTime());
        holder.txtNumOfChair.setText("Số ghế đã đặt: " + (trip.getSeatsBooked()));
        holder.txtPrice.setText("Giá vé: " + trip.getPrice()*trip.getSeatsBooked() + " VNĐ");

        booking.setSeatsBooked(trip.getSeatsBooked());

        holder.btnCancelTrip.setOnClickListener(v -> {
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Xác nhận hủy")
                    .setMessage("Bạn có chắc muốn hủy chuyến này không?")
                    .setPositiveButton("Hủy chuyến", (dialog, which) -> {
                        if (booking != null) {
                            booking.setStatus("cancel");

                            FirebaseFirestore.getInstance()
                                    .collection("bookings")
                                    .document(booking.getBookingID())
                                    .set(booking);

                            trip.setSeatsBooked(Math.max(0, trip.getSeatsBooked() - booking.getSeatsBooked()));
                            FirebaseFirestore.getInstance()
                                    .collection("trips")
                                    .document(trip.getTripID())
                                    .set(trip);
                        }

                        tripList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, tripList.size());

                        Toast.makeText(holder.itemView.getContext(), "Bạn đã hủy chuyến đi này", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Không", null)
                    .show();
        });
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
