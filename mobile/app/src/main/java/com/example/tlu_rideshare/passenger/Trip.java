package com.example.tlu_rideshare.passenger;

public class Trip {
    public String driverName;
    public String time;
    public int price;
    public String vehicle;
    public String schedule;
    public String phoneNumber;
    public String licensePlate;
    public int numOfChair;

    public Trip(String driverName, String time, int price, String vehicle) {
        this.driverName = driverName;
        this.time = time;
        this.price = price;
        this.vehicle = vehicle;
    }

    public Trip(String driverName, String licensePlate, int numOfChair, String phoneNumber, int price, String schedule, String time, String vehicle) {
        this.driverName = driverName;
        this.licensePlate = licensePlate;
        this.numOfChair = numOfChair;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.schedule = schedule;
        this.time = time;
        this.vehicle = vehicle;
    }
}
