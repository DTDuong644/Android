package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Booking;
import com.example.tlu_rideshare.model.Trip;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<Trip> tripList;
    private List<Booking> bookingList;
    private OnRateClickListener listener;

    public interface OnRateClickListener {
        void onRateClick(Trip trip, int position);
    }

    public HistoryAdapter(List<Trip> tripList, List<Booking> bookingList, OnRateClickListener listener) {
        this.tripList = tripList;
        this.bookingList = bookingList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = tripList.get(position);

        holder.tvRoute.setText("Tuyến xe: " + trip.getFromLocation() + " → " + trip.getToLocation());
        holder.tvDriver.setText("Tài xế: " + trip.getDriverID());
        holder.tvPhone.setText("Sđt: Chưa có");
        holder.tvPlate.setText("Biển số: " + trip.getLicensePlate());
        holder.tvTime.setText("Thời gian: " + trip.getDate() + " " + trip.getTime());
        holder.tvSeats.setText("Số ghế đặt: " + trip.getSeatsBooked());
        holder.tvPrice.setText("Giá: " + trip.getPrice() * trip.getSeatsBooked() + " VND");

        boolean isRated = true; // Mặc định là đã đánh giá
        for (Booking bk : bookingList) {
            if (bk.getTripID().equals(trip.getTripID()) && !bk.isRated()) {
                isRated = false;
                break;
            }
        }

        if (isRated) {
            holder.btnRate.setVisibility(View.GONE);
        } else {
            holder.btnRate.setVisibility(View.VISIBLE);
            holder.btnRate.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onRateClick(trip, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvRoute, tvDriver, tvPhone, tvPlate, tvTime, tvSeats, tvPrice;
        Button btnRate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoute = itemView.findViewById(R.id.tvRoute);
            tvDriver = itemView.findViewById(R.id.tvDriver);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvPlate = itemView.findViewById(R.id.tvPlate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvSeats = itemView.findViewById(R.id.tvSeats);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            btnRate = itemView.findViewById(R.id.btnRate);
        }
    }
}
