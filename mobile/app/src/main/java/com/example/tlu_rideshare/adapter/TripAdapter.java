package com.example.tlu_rideshare.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.List;

public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {
    private List<Trip> tripList;

    public TripAdapter(List<Trip> tripList) {
        this.tripList = tripList;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_trip_item, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        int seatsLeft = trip.getSeatsAvailable() - trip.getSeatsBooked();

        holder.tvTripTitle.setText("Chuyến #" + trip.getId());
        holder.tvDateTime.setText("Giờ: " + trip.getTime() + " | Ngày: " + trip.getDate());
        holder.tvFrom.setText("Điểm đón: " + trip.getFromLocation());
        holder.tvTo.setText("Điểm trả: " + trip.getToLocation());
        holder.tvSeats.setText("Còn lại: " + seatsLeft + " ghế");
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }

    public static class TripViewHolder extends RecyclerView.ViewHolder {
        TextView tvTripTitle, tvDateTime, tvFrom, tvTo, tvSeats;

        public TripViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTripTitle = itemView.findViewById(R.id.tvTripTitle);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvFrom = itemView.findViewById(R.id.tvFrom);
            tvTo = itemView.findViewById(R.id.tvTo);
            tvSeats = itemView.findViewById(R.id.tvSeats);
        }
    }
}
