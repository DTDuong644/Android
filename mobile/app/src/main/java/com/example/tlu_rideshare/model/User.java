package com.example.tlu_rideshare.model;

public class User {
    private String id;                  // Firestore document ID
    private String fullName;
    private String email;
    private String phoneNumber;
    private String hometown;
    private String dob;                // Date of birth (e.g., "04/06/2004")
    private boolean emailVerified;

    // Bắt buộc: Constructor rỗng để Firebase mapping
    public User() {}

    public User(String id, String fullName, String email, String phoneNumber,
                String hometown, String dob, boolean emailVerified) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.hometown = hometown;
        this.dob = dob;
        this.emailVerified = emailVerified;
    }

    // Getters và Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
