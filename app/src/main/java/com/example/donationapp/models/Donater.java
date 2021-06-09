package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

public class Donater {

    private String FullName;
    private String Email;
    private String Password;
    private String Address;
    private String Phone;
    @Exclude
    public String key;
    public Donater(String fullName, String email, String password, String address, String phone) {
        FullName = fullName;
        Email = email;
        Password = password;
        Address = address;
        Phone = phone;
    }
    public Donater(){}

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    @Exclude
    public String getKey() {
        return key;
    }
    @Exclude
    public void setKey(String key){
        this.key = key;
    }
}
