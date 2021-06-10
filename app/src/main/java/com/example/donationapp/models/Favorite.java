package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Favorite implements Serializable {



    private String nameAsso;
    private String idDonater;
    private String idAsso;
    public String key;
    public Favorite(String nameAsso, String idDonater, String idAsso) {

        this.nameAsso = nameAsso;
        this.idDonater = idDonater;
        this.idAsso = idAsso;

    }
    public Favorite(){}

    public String getNameAsso() {
        return nameAsso;
    }

    public String getIdDonater() {
        return idDonater;
    }

    public void setNameAsso(String nameAsso) {
        this.nameAsso = nameAsso;
    }

    public void setIdDonater(String idDonater) {
        this.idDonater = idDonater;
    }

    public String getIdAsso() {
        return idAsso;
    }

    public void setIdAsso(String idAsso) {
        this.idAsso = idAsso;
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
