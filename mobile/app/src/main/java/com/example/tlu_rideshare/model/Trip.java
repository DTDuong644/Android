package com.example.tlu_rideshare.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Lớp đại diện cho thông tin của một chuyến đi trong ứng dụng TLU Rideshare.
 * Thực hiện Parcelable để hỗ trợ truyền dữ liệu giữa các thành phần Android.
 */
public class Trip implements Parcelable {

    private String driverName;
    private String time;
    private int price;
    private String vehicle;
    private String yourLocation;
    private String destination;
    private String phoneNumber;
    private String licensePlate;
    private int emptyChair;
    private boolean isUserCreated;
    private boolean isRated; // Thêm để theo dõi trạng thái đánh giá
    private int rating; // Thêm để lưu điểm đánh giá (1-5)

    /**
     * Constructor cơ bản với các trường bắt buộc.
     * @param driverName Tên tài xế
     * @param time Thời gian khởi hành
     * @param price Giá vé
     * @param vehicle Loại phương tiện
     */
    public Trip(String driverName, String time, int price, String vehicle) {
        this.driverName = driverName != null ? driverName : "";
        this.time = time != null ? time : "";
        this.price = price;
        this.vehicle = vehicle != null ? vehicle : "";
        this.yourLocation = "";
        this.destination = "";
        this.phoneNumber = "";
        this.licensePlate = "";
        this.emptyChair = 0;
        this.isUserCreated = false;
        this.isRated = false;
        this.rating = 0;
    }

    /**
     * Constructor chi tiết với đầy đủ thông tin chuyến đi.
     * @param driverName Tên tài xế
     * @param licensePlate Biển số xe
     * @param emptyChair Số ghế trống
     * @param phoneNumber Số điện thoại liên lạc
     * @param price Giá vé
     * @param yourLocation Điểm đi
     * @param destination Điểm đến
     * @param time Thời gian khởi hành
     * @param vehicle Loại phương tiện
     */
    public Trip(String driverName, String licensePlate, int emptyChair, String phoneNumber, int price,
                String yourLocation, String destination, String time, String vehicle) {
        this(driverName, licensePlate, emptyChair, phoneNumber, price, yourLocation, destination, time, vehicle, true);
    }

    /**
     * Constructor chi tiết với tùy chọn isUserCreated.
     * @param driverName Tên tài xế
     * @param licensePlate Biển số xe
     * @param emptyChair Số ghế trống
     * @param phoneNumber Số điện thoại liên lạc
     * @param price Giá vé
     * @param yourLocation Điểm đi
     * @param destination Điểm đến
     * @param time Thời gian khởi hành
     * @param vehicle Loại phương tiện
     * @param isUserCreated Chuyến đi do người dùng tạo hay không
     */
    public Trip(String driverName, String licensePlate, int emptyChair, String phoneNumber, int price,
                String yourLocation, String destination, String time, String vehicle, boolean isUserCreated) {
        this.driverName = driverName != null ? driverName : "";
        this.licensePlate = licensePlate != null ? licensePlate : "";
        this.emptyChair = emptyChair >= 0 ? emptyChair : 0;
        this.phoneNumber = phoneNumber != null ? phoneNumber : "";
        this.price = price >= 0 ? price : 0;
        this.yourLocation = yourLocation != null ? yourLocation : "";
        this.destination = destination != null ? destination : "";
        this.time = time != null ? time : "";
        this.vehicle = vehicle != null ? vehicle : "";
        this.isUserCreated = isUserCreated;
        this.isRated = false;
        this.rating = 0;
    }

    // Getters
    public boolean isUserCreated() { return isUserCreated; }
    public String getDestination() { return destination; }
    public String getDriverName() { return driverName; }
    public int getEmptyChair() { return emptyChair; }
    public String getLicensePlate() { return licensePlate; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getPrice() { return price; }
    public String getTime() { return time; }
    public String getVehicle() { return vehicle; }
    public String getYourLocation() { return yourLocation; }
    public boolean isRated() { return isRated; }
    public int getRating() { return rating; }

    // Setters
    public void setDestination(String destination) { this.destination = destination != null ? destination : ""; }
    public void setDriverName(String driverName) { this.driverName = driverName != null ? driverName : ""; }
    public void setEmptyChair(int emptyChair) { this.emptyChair = emptyChair >= 0 ? emptyChair : 0; }
    public void setUserCreated(boolean userCreated) { this.isUserCreated = userCreated; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate != null ? licensePlate : ""; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber != null ? phoneNumber : ""; }
    public void setPrice(int price) { this.price = price >= 0 ? price : 0; }
    public void setTime(String time) { this.time = time != null ? time : ""; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle != null ? vehicle : ""; }
    public void setYourLocation(String yourLocation) { this.yourLocation = yourLocation != null ? yourLocation : ""; }
    public void setRated(boolean rated) { this.isRated = rated; }
    public void setRating(int rating) { this.rating = rating >= 0 && rating <= 5 ? rating : 0; }

    // Parcelable implementation
    protected Trip(Parcel in) {
        driverName = in.readString();
        time = in.readString();
        price = in.readInt();
        vehicle = in.readString();
        yourLocation = in.readString();
        destination = in.readString();
        phoneNumber = in.readString();
        licensePlate = in.readString();
        emptyChair = in.readInt();
        isUserCreated = in.readByte() != 0;
        isRated = in.readByte() != 0;
        rating = in.readInt();
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(driverName);
        dest.writeString(time);
        dest.writeInt(price);
        dest.writeString(vehicle);
        dest.writeString(yourLocation);
        dest.writeString(destination);
        dest.writeString(phoneNumber);
        dest.writeString(licensePlate);
        dest.writeInt(emptyChair);
        dest.writeByte((byte) (isUserCreated ? 1 : 0));
        dest.writeByte((byte) (isRated ? 1 : 0));
        dest.writeInt(rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return driverName.equals(trip.driverName) &&
                destination.equals(trip.destination) &&
                rating == trip.rating;
    }

    @Override
    public int hashCode() {
        int result = driverName.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + rating;
        return result;
    }
}