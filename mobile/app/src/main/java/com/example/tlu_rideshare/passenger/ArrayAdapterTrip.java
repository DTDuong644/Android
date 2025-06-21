package com.example.tlu_rideshare.passenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.List;

public class ArrayAdapterTrip extends RecyclerView.Adapter<ArrayAdapterTrip.TripViewHolder> {
    private List<Trip> tripList;
    private FragmentActivity activity;

    public ArrayAdapterTrip(List<Trip> tripList, FragmentActivity activity) {
        this.tripList = tripList;
        this.activity = activity;
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

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_trip, parent, false);
        return new TripViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TripViewHolder holder, int position) {
        Trip trip = tripList.get(position);
        holder.tvTripTitle.setText("🚌 Chuyến đi " + (position + 1));
        holder.tvDriver.setText("Tài xế: " + trip.getDriverName());
        holder.tvTime.setText("Thời gian khởi hành: " + trip.getTime());
        holder.tvPrice.setText("Giá: " + trip.getPrice() + " VNĐ");

        if (trip.getVehicle().startsWith("Ô tô")) {
            holder.tvVehicle.setText("Phương tiện: Ô tô (còn " + trip.getEmptyChair() + " chỗ)");
        } else {
            holder.tvVehicle.setText("Phương tiện: " + trip.getVehicle());
        }

        holder.btnDetails.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("trip", trip);
            SeeDetailsFragment seeDetailsFragment = new SeeDetailsFragment();
            seeDetailsFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tabContent, seeDetailsFragment)
                    .addToBackStack(null) // Đã có, đảm bảo back stack
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}