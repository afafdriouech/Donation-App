package com.example.donationapp.models;

import java.io.Serializable;

public class Favorite implements Serializable {



    private String nameAsso;
    private String idDonater;

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
}
