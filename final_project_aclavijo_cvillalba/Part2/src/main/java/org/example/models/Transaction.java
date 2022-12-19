package org.example.models;

public class Transaction {
  private String id;
  private String type;
  private double amount;
  private String date;
  
  public Transaction(String id, String type, double amount, String date) {
    this.id = id;
    this.type = type;
    this.amount = amount;
    this.date = date;
  }

  public Transaction() {
  }

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public double getAmount() {
    return amount;
  }
  public void setAmount(double amount) {
    this.amount = amount;
  }
  public String getDate() {
    return date;
  }
  public void setDate(String date) {
    this.date = date;
  }
}