package com.example.donationapp.models;

import java.io.Serializable;

public class Favorite implements Serializable {



    private String NameAsso;
    private String idDonater;

    public Favorite(String nameAsso, String idDonater) {

        NameAsso = nameAsso;
        this.idDonater = idDonater;
    }
    public Favorite(){}


    public void setNameAsso(String nameAsso) {
        NameAsso = nameAsso;
    }

    public void setIdDonater(String idDonater) {
        this.idDonater = idDonater;
    }



    public String getNameAsso() {
        return NameAsso;
    }

    public String getIdDonater() {
        return idDonater;
    }
}
