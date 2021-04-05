package com.example.donationapp.models;


import androidx.appcompat.app.AppCompatActivity;

public class Projet extends AppCompatActivity {

    private String titre;
    private String DateLancement;
    private String DureeRealisation;
    private String DateEcheance;
    private String budget;
    private String lieu;
    private String avancement;
    private String description;
    private String image;
    private String idAsso;

    public Projet(String nom, String dateLancement, String dureeRealisation, String dateEcheance, String budget, String lieu, String avancement, String description, String image, String idAsso) {
        titre = nom;
        DateLancement = dateLancement;
        DureeRealisation = dureeRealisation;
        DateEcheance = dateEcheance;
        this.budget = budget;
        this.lieu = lieu;
        this.avancement = avancement;
        this.description = description;
        this.image = image;
        this.idAsso = idAsso;
    }

    public String getNom() {
        return titre;
    }

    public String getDateLancement() {
        return DateLancement;
    }

    public String getDureeRealisation() {
        return DureeRealisation;
    }

    public String getDateEcheance() {
        return DateEcheance;
    }

    public String getBudget() {
        return budget;
    }

    public String getLieu() {
        return lieu;
    }

    public String getAvancement() {
        return avancement;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getIdAsso() {
        return idAsso;
    }

    public void setNom(String nom) {
        titre = nom;
    }

    public void setDateLancement(String dateLancement) {
        DateLancement = dateLancement;
    }

    public void setDureeRealisation(String dureeRealisation) {
        DureeRealisation = dureeRealisation;
    }

    public void setDateEcheance(String dateEcheance) {
        DateEcheance = dateEcheance;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIdAsso(String idAsso) {
        this.idAsso = idAsso;
    }
}
