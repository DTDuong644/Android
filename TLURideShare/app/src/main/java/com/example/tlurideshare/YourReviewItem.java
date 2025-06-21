package com.example.tlurideshare;

public class YourReviewItem {
    private String driverName;
    private String route;
    private String ratingScore;
    private String reviewComment;

    public YourReviewItem(String driverName, String route, String ratingScore, String reviewComment) {
        this.driverName = driverName;
        this.route = route;
        this.ratingScore = ratingScore;
        this.reviewComment = reviewComment;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getRoute() {
        return route;
    }

    public String getRatingScore() {
        return ratingScore;
    }

    public String getReviewComment() {
        return reviewComment;
    }
} 