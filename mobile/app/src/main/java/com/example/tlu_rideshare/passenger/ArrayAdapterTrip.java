package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.List;

public class ArrayAdapterTrip extends RecyclerView.Adapter<ArrayAdapterTrip.TripViewHolder> {
    private List<Trip> tripList;

    public ArrayAdapterTrip(List<Trip> tripList) {
        this.tripList = tripList;
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriver, tvTime, tvPrice, tvVehicle, tvTripTitle;
        Button btnDetails;

        public TripViewHolder(View itemView) {
            super(itemView);
            tvTripTitle = itemView.findViewById(R.id.tvTripTitle);
            tvDriver = itemView.findViewById(R.id.tvDriver);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvVehicle = itemView.findViewById(R.id.tvVehicle);
            btnDetails = itemView.findViewById(R.id.btnDetails);
        }
    }

    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tvTripTitle.setText("🚌 Chuyến đi " + (position + 1));
        holder.tvDriver.setText("Tài xế: " + trip.driverName);
        holder.tvTime.setText("Thời gian khởi hành: " + trip.time);
        holder.tvPrice.setText("Giá: " + trip.price + " VNĐ");
        holder.tvVehicle.setText("Phương tiện: " + trip.vehicle);
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}

