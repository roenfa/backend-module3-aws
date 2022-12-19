package org.example.repositories;

import java.util.Collection;

import org.example.models.Transaction;

public interface ITransactionRepository {
  Transaction save(Transaction transaction);
  Collection<Transaction> getAll();
  Collection<Transaction> getTransaction(String id);
}
