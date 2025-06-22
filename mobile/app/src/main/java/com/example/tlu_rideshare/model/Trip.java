package com.example.tlu_rideshare.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Trip implements Parcelable {
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

    private boolean isRated;
    private int rating;

    private boolean userCreated = false;

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
        this.isRated = false;
        this.rating = 0;
    }

    // Getters
    public String getTripID() { return tripID; }
    public String getFromLocation() { return fromLocation; }
    public String getToLocation() { return toLocation; }
    public String getDriverID() { return driverID; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getSeatsAvailable() { return seatsAvailable; }
    public int getSeatsBooked() { return seatsBooked; }
    public String getLicensePlate() { return licensePlate; }
    public String getVihicleType() { return vihicleType; }
    public int getPrice() { return price; }
    public String getStatus() { return status; }
    public boolean isRated() { return isRated; }
    public int getRating() { return rating; }
    public boolean isUserCreated() { return userCreated; }

    // Setters
    public void setTripID(String tripID) { this.tripID = tripID; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }
    public void setDriverID(String driverID) { this.driverID = driverID; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public void setVihicleType(String vihicleType) { this.vihicleType = vihicleType; }
    public void setPrice(int price) { this.price = price; }
    public void setStatus(String status) { this.status = status; }
    public void setRated(boolean rated) { this.isRated = rated; }
    public void setRating(int rating) { this.rating = rating; }
    public void setUserCreated(boolean userCreated) { this.userCreated = userCreated; }

    // Parcelable Implementation
    protected Trip(Parcel in) {
        tripID = in.readString();
        fromLocation = in.readString();
        toLocation = in.readString();
        driverID = in.readString();
        date = in.readString();
        time = in.readString();
        seatsAvailable = in.readInt();
        seatsBooked = in.readInt();
        licensePlate = in.readString();
        vihicleType = in.readString();
        price = in.readInt();
        status = in.readString();
        isRated = in.readByte() != 0;
        rating = in.readInt();
        userCreated = in.readByte() != 0;
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tripID);
        dest.writeString(fromLocation);
        dest.writeString(toLocation);
        dest.writeString(driverID);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeInt(seatsAvailable);
        dest.writeInt(seatsBooked);
        dest.writeString(licensePlate);
        dest.writeString(vihicleType);
        dest.writeInt(price);
        dest.writeString(status);
        dest.writeByte((byte) (isRated ? 1 : 0));
        dest.writeInt(rating);
        dest.writeByte((byte) (userCreated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
