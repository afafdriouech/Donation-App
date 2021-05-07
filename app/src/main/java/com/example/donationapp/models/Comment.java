package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Comment  implements Serializable {
    private String nameAsso;
    private String idDonater;
    private String review;
    public String key;

    public Comment(String nameAsso, String idDonater, String review) {
        this.nameAsso = nameAsso;
        this.idDonater = idDonater;
        this.review = review;
    }

    public Comment() { }

    public void setNameAsso(String nameAsso) {
        this.nameAsso = nameAsso;
    }

    public void setIdDonater(String idDonater) {
        this.idDonater = idDonater;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getNameAsso() {
        return nameAsso;
    }

    public String getIdDonater() {
        return idDonater;
    }

    public String getReview() {
        return review;
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
