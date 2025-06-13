package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;

import java.util.List;

public class YourRatingAdapter extends RecyclerView.Adapter<YourRatingAdapter.ViewHolder> {
    private List<Trip> ratedTrips;

    public YourRatingAdapter(List<Trip> ratedTrips) {
        this.ratedTrips = ratedTrips;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trip trip = ratedTrips.get(position);
        holder.tvDriverName.setText("Tài xế: " + trip.getDriverName());
        holder.tvRating.setText(trip.getRating() + "/5"); // Hiển thị rating dưới dạng X/5
        holder.tvRide.setText("Trường đại học -> " + trip.getDestination());
        holder.tvFeedback.setText(getFeedback(trip.getRating()));
    }

    @Override
    public int getItemCount() {
        return ratedTrips.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriverName, tvRating, tvRide, tvFeedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverName = itemView.findViewById(R.id.tvDriverName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvRide = itemView.findViewById(R.id.tvRide); // Thêm tvRide
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
        }
    }

    private String getFeedback(int rating) {
        switch (rating) {
            case 1: return "Rất không hài lòng";
            case 2: return "Không hài lòng";
            case 3: return "Bình thường";
            case 4: return "Hài lòng";
            case 5: return "Rất hài lòng";
            default: return "";
        }
    }
}