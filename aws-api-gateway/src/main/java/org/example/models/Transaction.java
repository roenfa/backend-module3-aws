
package org.example.models;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
  private String id;
  private String type;
  private double amount;
  private String date;

  @Override
  public String toString() {
    return "Id: "+this.id+" Type: "+this.type+" Amount"+this.amount+" Date: "+this.date;
  }
}
