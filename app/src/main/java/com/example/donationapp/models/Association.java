package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Association implements Serializable {
    public String adresse;
    public String assoName;
    public String dateCreation;
    public String email;
    public String password;
    public String phone;
    public String key;

    public Association(String adresse,String assoName,String dateCreation,String email,String password,String phone){
        this.adresse= adresse;
        this.assoName= assoName;
        this.dateCreation = dateCreation;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }
    public Association(){}

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setAssoName(String assoName) {
        this.assoName = assoName;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getAssoName() {
        return assoName;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
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
