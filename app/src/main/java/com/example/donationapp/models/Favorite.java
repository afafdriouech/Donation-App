package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Favorite implements Serializable {



    private String nameAsso;
    private String idDonater;
    public String key;
    public Favorite(String nameAsso, String idDonater) {

        this.nameAsso = nameAsso;
        this.idDonater = idDonater;

    }
    public Favorite(){}


    public void setNameAsso(String nameAsso) {
        nameAsso = nameAsso;
    }

    public void setIdDonater(String idDonater) {
        this.idDonater = idDonater;
    }



    public String getNameAsso() {
        return nameAsso;
    }

    public String getIdDonater() {
        return idDonater;
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
