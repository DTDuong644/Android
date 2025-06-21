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

    private List<String> locationList;
    private Trip trip;
    private boolean isYourLocation; // Biến để xác định đang chọn yourLocation hay destination
    private OnLocationSelectedListener listener;

    // Interface để thông báo khi một vị trí được chọn
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

        // Xử lý khi người dùng nhấn vào một vị trí
        holder.itemView.setOnClickListener(v -> {
            Context context = holder.itemView.getContext();

            if (isYourLocation) {
                trip.setYourLocation(location);
                Toast.makeText(holder.itemView.getContext(), "Đã chọn vị trí: " + location, Toast.LENGTH_SHORT).show();
            } else {
                trip.setDestination(location);
                Toast.makeText(holder.itemView.getContext(), "Đã chọn điểm đến: " + location, Toast.LENGTH_SHORT).show();
            }
            // Gọi listener để thông báo vị trí đã được chọn
            if (listener != null) {
                listener.onLocationSelected(location, isYourLocation);
            }

            if (trip.getYourLocation() != null && trip.getDestination() != null) {
                String yourProvince = extractProvince(trip.getYourLocation());
                String destinationProvince = extractProvince(trip.getDestination());

                if (trip.getYourLocation().equals(trip.getDestination())) {
                    trip.setYourLocation(null); // Xóa dữ liệu
                    trip.setDestination(null);
                    listener.onLocationSelected("", true); // clear UI
                    listener.onLocationSelected("", false);
                    new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Điểm đến và vị trí của bạn đang giống nhau.")
                            .setPositiveButton("OK", null)
                            .show();
                } else if (!yourProvince.isEmpty() && yourProvince.equals(destinationProvince)) {
                    new AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Điểm đến và vị trí của bạn đang cùng tỉnh, hãy chắc chắn với lựa chọn của mình!")
                            .setPositiveButton("OK", null)
                            .show();
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

    private String extractProvince(String locationName) {
        String[] parts = locationName.split(" - ");
        if (parts.length == 2) {
            return parts[1].trim();
        }
        return "";
    }
}
