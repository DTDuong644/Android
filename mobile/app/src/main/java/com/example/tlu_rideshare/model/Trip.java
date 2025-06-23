package com.example.tlu_rideshare.model;

import java.io.Serializable;

public class Trip implements Serializable {
    private String tripID;
    private String fromLocation;
    private String toLocation;
    private String driverID;
    private String date;
    private String time;
    private int seatsAvailable;
    private int seatsBooked;
    private String licensePlate;
    private String vihicleType;
    private int price;

    private String status;

    public Trip() {
        // Required for Firebase
    }

    public Trip(String tripID, String fromLocation, String toLocation, String driverID,
                String date, String time, int seatsAvailable, int seatsBooked,
                String licensePlate, String vihicleType, int price, String status) {
        this.tripID = tripID;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.driverID = driverID;
        this.date = date;
        this.time = time;
        this.seatsAvailable = seatsAvailable;
        this.seatsBooked = seatsBooked;
        this.licensePlate = licensePlate;
        this.vihicleType = vihicleType;
        this.price = price;
        this.status = status;
    }

    // Getters
    public String getTripID() {
        return tripID;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public String getDriverID() {
        return driverID;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getVihicleType() {
        return vihicleType;
    }

    public int getPrice() {
        return price;
    }

    public String getStatus(){
        return status;
    }

    // Setters
    public void setTripID(String tripID) {
        this.tripID = tripID;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDriverID(String driverID) {
        this.driverID = driverID;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public void setSeatsBooked(int seatsBooked) {
        this.seatsBooked = seatsBooked;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setVihicleType(String vihicleType) {
        this.vihicleType = vihicleType;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
