package com.example.donationapp.models;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Donation implements Serializable {

    private String projectId;
    private String donaterId;
    private String donationAmount;
    private String currency;
    private String cardNumber;
    private String cardDate;
    private String cardCVC;
    private String cardName;
    @Exclude
    public String key;

    public Donation() {
    }

    public Donation(String projectId, String donaterId, String donationAmount,
                    String currency, String cardNumber, String cardDate, String cardCVC, String cardName) {
        this.projectId = projectId;
        this.donaterId = donaterId;
        this.donationAmount = donationAmount;
        this.currency = currency;
        this.cardNumber = cardNumber;
        this.cardDate = cardDate;
        this.cardCVC = cardCVC;
        this.cardName = cardName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getDonaterId() {
        return donaterId;
    }

    public void setDonaterId(String donaterId) {
        this.donaterId = donaterId;
    }

    public String getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(String donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardDate() {
        return cardDate;
    }

    public void setCardDate(String cardDate) {
        this.cardDate = cardDate;
    }

    public String getCardCVC() {
        return cardCVC;
    }

    public void setCardCVC(String cardCVC) {
        this.cardCVC = cardCVC;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
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
