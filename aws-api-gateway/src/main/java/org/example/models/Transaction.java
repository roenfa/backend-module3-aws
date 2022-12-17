
package org.example.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
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

  @Override
  public String toString() {
    return "Id: "+this.id+" Type: "+this.type+" Amount"+this.amount+" Date: "+this.date;
  }
}
