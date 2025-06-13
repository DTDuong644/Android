package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tlu_rideshare.R;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<Trip> tripList;
    private OnRateClickListener listener;

    // Interface để xử lý sự kiện click nút Đánh giá
    public interface OnRateClickListener {
        void onRateClick(Trip trip, int position);
    }

    public HistoryAdapter(List<Trip> tripList, OnRateClickListener listener) {
        this.tripList = tripList;
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
        holder.tvRoute.setText("Tuyến xe: " + trip.getYourLocation() + " → " + trip.getDestination());
        holder.tvDriver.setText("Tài xế: " + trip.getDriverName());
        holder.tvPhone.setText("Sđ điện thoại: " + trip.getPhoneNumber());
        holder.tvPlate.setText("Biển số xe: " + trip.getLicensePlate());
        holder.tvTime.setText("Thời gian: " + trip.getTime());
        holder.tvSeats.setText("Số ghế: " + trip.getEmptyChair());
        holder.tvPrice.setText("Giá: " + trip.getPrice() + " VND");

        // Ẩn nút Đánh giá nếu chuyến đi đã được đánh giá
        if (trip.isRated()) {
            holder.btnRate.setVisibility(View.GONE);
        } else {
            holder.btnRate.setVisibility(View.VISIBLE);
            // Xử lý sự kiện click nút Đánh giá
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
        TextView tvRoute, tvDriver, tvPhone, tvPlate, tvTime, tvSeats, tvPrice, tvVehicle;
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