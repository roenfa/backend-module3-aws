
package org.example.models;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
  private String id;
  private String type;
  private double amount;
  private Date date;

  public Transaction(String id, String type, double amount, Date date) {
    this.id = id;
    this.type = type;
    this.amount = amount;
    this.date = date;
  }
}
