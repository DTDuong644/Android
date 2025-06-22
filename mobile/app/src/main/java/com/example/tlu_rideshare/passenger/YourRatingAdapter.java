package com.example.tlu_rideshare.passenger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tlu_rideshare.R;
import com.example.tlu_rideshare.model.FeedBack;
import com.example.tlu_rideshare.model.Trip;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class YourRatingAdapter extends RecyclerView.Adapter<YourRatingAdapter.ViewHolder> {
    private List<FeedBack> feedbackList;
    private Map<String, Trip> tripMap;

    public YourRatingAdapter(List<FeedBack> feedbackList, List<Trip> tripList) {
        this.feedbackList = feedbackList;
        this.tripMap = new HashMap<>();
        for (Trip trip : tripList) {
            tripMap.put(trip.getTripID(), trip);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_your_rating, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FeedBack feedback = feedbackList.get(position);
        holder.tvDriverName.setText("Tài xế: " + feedback.getDriverID());
        holder.tvRating.setText(feedback.getRating() + "/5");
        holder.tvFeedback.setText(getFeedback(feedback.getRating()));
        holder.tvTimeRating.setText("Thời gian: " + formatTimestamp(feedback.getTime()));

        Trip relatedTrip = tripMap.get(feedback.getTripID());
        if (relatedTrip != null) {
            // ✅ Sửa dùng fromLocation và toLocation thay vì yourLocation và destination
            holder.tvRide.setText("Tuyến: " + relatedTrip.getFromLocation() + " → " + relatedTrip.getToLocation());
        } else {
            holder.tvRide.setText("Tuyến: [Không xác định]");
        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDriverName, tvRating, tvRide, tvFeedback, tvTimeRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDriverName = itemView.findViewById(R.id.tvDriverName);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvRide = itemView.findViewById(R.id.tvRide);
            tvFeedback = itemView.findViewById(R.id.tvFeedback);
            tvTimeRating = itemView.findViewById(R.id.tvTimeRating);
        }
    }

    public static String getFeedback(int rating) {
        switch (rating) {
            case 1: return "Rất không hài lòng";
            case 2: return "Không hài lòng";
            case 3: return "Bình thường";
            case 4: return "Hài lòng";
            case 5: return "Rất hài lòng";
            default: return "Không xác định";
        }
    }

    private String formatTimestamp(java.sql.Timestamp timestamp) {
        if (timestamp == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        return sdf.format(timestamp);
    }
}
