package com.example.tlu_rideshare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class YourReviewsAdapter extends RecyclerView.Adapter<YourReviewsAdapter.ViewHolder> {

    private List<YourReviewItem> yourReviewsList;

    public YourReviewsAdapter(List<YourReviewItem> yourReviewsList) {
        this.yourReviewsList = yourReviewsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_your_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        YourReviewItem item = yourReviewsList.get(position);
        holder.driverNameTextView.setText(item.getDriverName());
        holder.routeTextView.setText(item.getRoute());
        holder.ratingScoreTextView.setText(item.getRatingScore());
        holder.reviewCommentTextView.setText(item.getReviewComment());
    }

    @Override
    public int getItemCount() {
        return yourReviewsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView driverNameTextView;
        TextView routeTextView;
        TextView ratingScoreTextView;
        TextView reviewCommentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            driverNameTextView = itemView.findViewById(R.id.text_driver_name);
            routeTextView = itemView.findViewById(R.id.text_route);
            ratingScoreTextView = itemView.findViewById(R.id.text_rating_score);
            reviewCommentTextView = itemView.findViewById(R.id.text_review_comment);
        }
    }
} 