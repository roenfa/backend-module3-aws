package org.example.services;

import org.example.models.Transaction;

import java.util.List;

public interface IAthenaService {
    String submitQuery(String myQuery);
    void waitForQueryToComplete(String queryExecutionId);
    List<Transaction> processQueryResult(String queryExecutionId);
}
