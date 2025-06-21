package com.example.tlu_rideshare.model;

public class User {
    private String uid;
    private String name;
    private String email;
    private String phone;
    private String pickup;
    private String dropoff;
    private String role; // Ví dụ: "customer", "driver", "admin"

    public User() {
        // Bắt buộc có constructor rỗng cho Firebase
    }

    public User(String uid, String name, String email, String phone, String pickup, String dropoff, String role) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.role = role;
    }

    // Getter & Setter
    public String getUid() { return uid; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPickup() { return pickup; }
    public String getDropoff() { return dropoff; }
    public String getRole() { return role; }

    public void setUid(String uid) { this.uid = uid; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setPickup(String pickup) { this.pickup = pickup; }
    public void setDropoff(String dropoff) { this.dropoff = dropoff; }
    public void setRole(String role) { this.role = role; }
}
