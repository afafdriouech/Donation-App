package com.example.donationapp.models;

public class Mydonation {

    private String project;
    private String amount;
    private String currency;
    private String association;
    private String dateOfDonation;

    public Mydonation(){}

    public Mydonation(String project, String amount, String currency, String association, String dateOfDonation) {
        this.project = project;
        this.amount = amount;
        this.currency = currency;
        this.association = association;
        this.dateOfDonation = dateOfDonation;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAssociation() {
        return association;
    }

    public void setAssociation(String association) {
        this.association = association;
    }

    public String getDateOfDonation() {
        return dateOfDonation;
    }

    public void setDateOfDonation(String dateOfDonation) {
        this.dateOfDonation = dateOfDonation;
    }
}
