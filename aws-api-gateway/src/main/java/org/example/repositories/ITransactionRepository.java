package org.example.repositories;

import org.example.models.Transaction;

public interface ITransactionRepository extends IRepository<Transaction> {
    Transaction getById(String id);
}
