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
  public Transaction getTransaction(String id) {
    // TODO fix and find
    return null;
    /* return transactions.stream()
      .filter(transaction -> id == transaction.getId())
      .findAny()
      .orElse(null); */
  }

}
