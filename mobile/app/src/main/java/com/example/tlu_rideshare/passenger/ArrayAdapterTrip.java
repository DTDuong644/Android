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
import com.google.firebase.firestore.FirebaseFirestore;

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

        String vehicleType = trip.getVihicleType();
        if (vehicleType != null) vehicleType = vehicleType.trim();

        if ("Xe máy".equalsIgnoreCase(vehicleType) && trip.getSeatsAvailable() <= 0) {
            trip.setSeatsAvailable(1);
        }

        int emptySeats = Math.max(trip.getSeatsAvailable() - trip.getSeatsBooked(), 0);

        if (emptySeats == 0) {
            holder.itemView.setVisibility(View.GONE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 0;
            holder.itemView.setLayoutParams(params);
            return;
        } else {
            holder.itemView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.itemView.setLayoutParams(params);
        }

        holder.tvTripTitle.setText("🚌 Chuyến đi " + (position + 1));
        holder.tvTime.setText("Thời gian khởi hành: " + trip.getDate() + " " + trip.getTime());
        holder.tvPrice.setText("Giá: " + trip.getPrice() + " VNĐ");

        if (vehicleType != null && vehicleType.startsWith("Ô tô")) {
            holder.tvVehicle.setText("Phương tiện: Ô tô (còn " + emptySeats + " chỗ)");
        } else {
            holder.tvVehicle.setText("Phương tiện: " + (vehicleType != null ? vehicleType : "Không rõ"));
        }

        // 🔽 Load tên tài xế từ Firestore
        String driverID = trip.getDriverID();
        holder.tvDriver.setText("Tài xế: đang tải...");

        if (driverID != null && !driverID.isEmpty()) {
            FirebaseFirestore.getInstance()
                    .collection("users")
                    .document(driverID)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String fullName = documentSnapshot.getString("fullName");
                            holder.tvDriver.setText("Tài xế: " + (fullName != null ? fullName : "Không rõ"));
                        } else {
                            holder.tvDriver.setText("Tài xế: Không tìm thấy");
                        }
                    })
                    .addOnFailureListener(e -> {
                        holder.tvDriver.setText("Tài xế: Lỗi tải");
                    });
        } else {
            holder.tvDriver.setText("Tài xế: Không rõ");
        }

        holder.btnDetails.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("trip", trip);
            SeeDetailsFragment seeDetailsFragment = new SeeDetailsFragment();
            seeDetailsFragment.setArguments(bundle);
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.tabContent, seeDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }
}
