package org.example.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.example.models.Transaction;

public class TransactionRepository implements ITransactionRepository {
  private List<Transaction> transactions;

  public TransactionRepository(){
    this.transactions = new ArrayList<>();
  }

  @Override
  public Transaction save(Transaction transaction) {
    this.transactions.add(transaction);
    return transaction;
  }

  @Override
  public Collection<Transaction> getAll() {
    return this.transactions;
  }

  @Override
  public Transaction findById(String id) {
    for(Transaction transaction: getAll()){
      if(transaction.getId().equals(id)){
        return transaction;
      }
    }

    return null;
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub
  }

  

}
