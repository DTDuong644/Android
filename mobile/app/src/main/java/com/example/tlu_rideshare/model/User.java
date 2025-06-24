package com.example.tlu_rideshare.model;

public class User {
    private String customerId;
    private String avatar;
    private String fullName;
    private String email;
    private String hometown;
    private String phoneNumber;
    private String descrip;
    private boolean isAccountVerified;

    public User(String customerId, String avatar, String descrip, String email, String fullName,
                String hometown, String phoneNumber, boolean isAccountVerified) {
        this.customerId = customerId;
        this.avatar = avatar;
        this.descrip = descrip;
        this.email = email;
        this.fullName = fullName;
        this.hometown = hometown;
        this.phoneNumber = phoneNumber;
        this.isAccountVerified = isAccountVerified;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    // Các getter & setter khác giữ nguyên...
    public String getAvatar() { return avatar; }
    public void setAvatar(String avatar) { this.avatar = avatar; }

    public String getDescrip() { return descrip; }
    public void setDescrip(String descrip) { this.descrip = descrip; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getHometown() { return hometown; }
    public void setHometown(String hometown) { this.hometown = hometown; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public boolean isAccountVerified() { return isAccountVerified; }
    public void setAccountVerified(boolean accountVerified) { isAccountVerified = accountVerified; }
}