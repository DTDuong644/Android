package com.example.tlu_rideshare.model;

public class Trip {
    private String id;
    private String fromLocation;
    private String toLocation;
    private String date;
    private String time;
    private int seatsAvailable;
    private int seatsBooked;

    public Trip() {
        // Required for Firebase
    }

    public Trip(String id, String fromLocation, String toLocation, String date, String time, int seatsAvailable, int seatsBooked) {
        this.id = id;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.date = date;
        this.time = time;
        this.seatsAvailable = seatsAvailable;
        this.seatsBooked = seatsBooked;
    }

    // Getters
    public String getId() { return id; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public int getSeatsBooked() { return seatsBooked; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }
}
