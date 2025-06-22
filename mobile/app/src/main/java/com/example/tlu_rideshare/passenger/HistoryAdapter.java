package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Trip> tripList;
    private Set<String> ratedTripIDs = new HashSet<>(); // ✅ Bộ nhớ ID chuyến đã đánh giá
    private OnRateClickListener listener;

    public interface OnRateClickListener {
        void onRateClick(Trip trip, int position);
    }

    public HistoryAdapter(List<Trip> tripList, OnRateClickListener listener) {
        this.tripList = tripList;
        this.listener = listener;
    }

    public void markTripAsRated(String tripID) {
        ratedTripIDs.add(tripID);
        notifyDataSetChanged();
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
        holder.tvDriver.setText("Tài xế: " + trip.getDriverID()); // Hiện tại chưa có tên, chỉ có driverID
        holder.tvPhone.setText("Sđt: Chưa có"); // Trip không còn phoneNumber
        holder.tvPlate.setText("Biển số: " + trip.getLicensePlate());
        holder.tvTime.setText("Thời gian: " + trip.getDate() + " " + trip.getTime());
        holder.tvSeats.setText("Số ghế còn: " + (trip.getSeatsAvailable() - trip.getSeatsBooked()));
        holder.tvPrice.setText("Giá: " + trip.getPrice() + " VND");

        if (ratedTripIDs.contains(trip.getTripID())) {
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
