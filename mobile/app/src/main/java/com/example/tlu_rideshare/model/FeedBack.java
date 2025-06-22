package com.example.tlu_rideshare.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class FeedBack implements Serializable {
    private String feedBackID;
    private String tripID;
    private String driverID;
    private String userID;
    private String content;
    private int rating;
    private Timestamp time;

    public FeedBack() {
    }

    public FeedBack(String feedBackID, String tripID, String driverID, String userID, String content, int rating, Timestamp time) {
        this.feedBackID = feedBackID;
        this.tripID = tripID;
        this.driverID = driverID;
        this.userID = userID;
        this.content = content;
        this.rating = rating;
        this.time = time;
    }

    public String getFeedBackID() {
        return feedBackID;
    }

    public void setFeedBackID(String feedBackID) {
        this.feedBackID = feedBackID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getDriverID() {
        return driverID;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FeedBack)) return false;
        FeedBack that = (FeedBack) o;
        return feedBackID.equals(that.feedBackID); // So sánh theo ID
    }

    @Override
    public int hashCode() {
        return feedBackID.hashCode();
    }

}
