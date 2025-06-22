package com.example.tlu_rideshare.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.PropertyName;

public class Trip implements Parcelable {
    private String tripID;
    private String driverID;
    private String fromLocation;
    private String toLocation;
    private String licensePlate;
    private int price;
    private int seatsAvailable;
    private int seatsBooked;
    private String time;
    private String date;
    private String vihicleType;

    private boolean rated;
    private boolean userCreated = false;

    public Trip() {}

    // Getters/Setters
    public String getTripID() { return tripID; }
    public void setTripID(String tripID) { this.tripID = tripID; }

    public String getDriverID() { return driverID; }
    public void setDriverID(String driverID) { this.driverID = driverID; }

    public String getFromLocation() { return fromLocation; }
    public void setFromLocation(String fromLocation) { this.fromLocation = fromLocation; }

    public String getToLocation() { return toLocation; }
    public void setToLocation(String toLocation) { this.toLocation = toLocation; }

    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public int getSeatsAvailable() { return seatsAvailable; }
    public void setSeatsAvailable(int seatsAvailable) { this.seatsAvailable = seatsAvailable; }

    public int getSeatsBooked() { return seatsBooked; }
    public void setSeatsBooked(int seatsBooked) { this.seatsBooked = seatsBooked; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getVihicleType() { return vihicleType; }
    public void setVihicleType(String vihicleType) { this.vihicleType = vihicleType; }

    public boolean isRated() { return rated; }
    public void setRated(boolean rated) { this.rated = rated; }

    public boolean isUserCreated() { return userCreated; }
    public void setUserCreated(boolean userCreated) { this.userCreated = userCreated; }

    // Parcelable
    protected Trip(Parcel in) {
        tripID = in.readString();
        driverID = in.readString();
        fromLocation = in.readString();
        toLocation = in.readString();
        licensePlate = in.readString();
        price = in.readInt();
        seatsAvailable = in.readInt();
        seatsBooked = in.readInt();
        time = in.readString();
        date = in.readString();
        vihicleType = in.readString(); // sửa chỗ này
        rated = in.readByte() != 0;
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
        dest.writeString(driverID);
        dest.writeString(fromLocation);
        dest.writeString(toLocation);
        dest.writeString(licensePlate);
        dest.writeInt(price);
        dest.writeInt(seatsAvailable);
        dest.writeInt(seatsBooked);
        dest.writeString(time);
        dest.writeString(date);
        dest.writeString(vihicleType); // sửa chỗ này
        dest.writeByte((byte) (rated ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }
}


