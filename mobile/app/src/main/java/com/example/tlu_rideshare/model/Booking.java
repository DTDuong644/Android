package com.example.tlu_rideshare.model;

import com.google.firebase.Timestamp;
import java.io.Serializable;

public class Booking implements Serializable {
    private String bookingID;
    private String tripID;
    private String userID;
    private Timestamp bookingTime;
    private String status;
    private int seatsBooked;
    private boolean rated = false;

    public Booking() {}

    public Booking(String bookingID, String tripID, String userID, Timestamp bookingTime, String status) {
        this.bookingID = bookingID;
        this.tripID = tripID;
        this.userID = userID;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public String getBookingID() {
        return bookingID;
    }

    public void setBookingID(String bookingID) {
        this.bookingID = bookingID;
    }

    public String getTripID() {
        return tripID;
    }

    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Timestamp getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Timestamp bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

}
