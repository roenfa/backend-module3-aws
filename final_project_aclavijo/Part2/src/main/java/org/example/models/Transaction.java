package org.example.models;

public class Transaction {
    private String id;
    private String type;
    private double amount;
    private String date;

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getDate() {
        return this.date;
    }

    public void setId(String transactionId) {
        this.id = transactionId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Id: " + this.id + " Type: " + this.type + " Amount: " + this.amount + " Date: " + this.date;
    }
}
