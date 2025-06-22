package com.example.tlu_rideshare.passenger;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.Trip;

import java.util.List;

public class SelectLocationAdapter extends RecyclerView.Adapter<SelectLocationAdapter.LocationViewHolder> {

    private final List<String> locationList;
    private final Trip trip;
    private final boolean isYourLocation;
    private final OnLocationSelectedListener listener;

    public interface OnLocationSelectedListener {
        void onLocationSelected(String location, boolean isYourLocation);
    }

    public SelectLocationAdapter(List<String> locationList, Trip trip, boolean isYourLocation, OnLocationSelectedListener listener) {
        this.locationList = locationList;
        this.trip = trip;
        this.isYourLocation = isYourLocation;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_location, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        String location = locationList.get(position);
        holder.textViewLocationName.setText(location);

        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();

            if (isYourLocation) {
                trip.setFromLocation(location);
                Toast.makeText(context, "Đã chọn vị trí: " + location, Toast.LENGTH_SHORT).show();
            } else {
                trip.setToLocation(location);
                Toast.makeText(context, "Đã chọn điểm đến: " + location, Toast.LENGTH_SHORT).show();
            }

            if (listener != null) {
                listener.onLocationSelected(location, isYourLocation);
            }

            String from = trip.getFromLocation();
            String to = trip.getToLocation();

            if (from != null && to != null) {
                String provinceFrom = extractProvince(from);
                String provinceTo = extractProvince(to);

                if (from.equals(to)) {
                    trip.setFromLocation(null);
                    trip.setToLocation(null);
                    if (listener != null) {
                        listener.onLocationSelected("", true);
                        listener.onLocationSelected("", false);
                    }
                    showAlert(context, "Thông báo", "Điểm đến và vị trí của bạn đang giống nhau.");
                } else if (!provinceFrom.isEmpty() && provinceFrom.equals(provinceTo)) {
                    showAlert(context, "Thông báo", "Điểm đến và vị trí của bạn đang cùng tỉnh, hãy chắc chắn với lựa chọn của mình!");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView textViewLocationName;

        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewLocationName = itemView.findViewById(R.id.textViewLocationName);
        }
    }

    private String extractProvince(String location) {
        String[] parts = location.split(" - ");
        return parts.length == 2 ? parts[1].trim() : "";
    }

    private void showAlert(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .show();
    }
}
