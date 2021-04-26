package com.example.donationapp.models;


import com.google.firebase.firestore.Exclude;

public class Projet{

    private String titre;
    private String DateLancement;
    private String DureeRealisation;
    private String DateEcheance;
    private String budget;
    private String lieu;
    private String avancement;
    private String description;
    private String image;
    private String imageUrl;
    private String idAsso;
    private String key;


    public Projet(String titre, String DateLancement, String DureeRealisation, String DateEcheance, String budget, String lieu, String avancement, String description, String image, String imageUrl, String idAsso) {
        this.titre = titre;
        this.DateLancement = DateLancement;
        this.DureeRealisation = DureeRealisation;
        this.DateEcheance = DateEcheance;
        this.budget = budget;
        this.lieu = lieu;
        this.avancement = avancement;
        this.description = description;
        this.image = image;
        this.imageUrl = imageUrl;
        this.idAsso = idAsso;
    }

    public Projet(){}

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDateLancement() {
        return DateLancement;
    }

    public void setDateLancement(String dateLancement) {
        DateLancement = dateLancement;
    }

    public String getDureeRealisation() {
        return DureeRealisation;
    }

    public void setDureeRealisation(String dureeRealisation) {
        DureeRealisation = dureeRealisation;
    }

    public String getDateEcheance() {
        return DateEcheance;
    }

    public void setDateEcheance(String dateEcheance) {
        DateEcheance = dateEcheance;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getAvancement() {
        return avancement;
    }

    public void setAvancement(String avancement) {
        this.avancement = avancement;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
