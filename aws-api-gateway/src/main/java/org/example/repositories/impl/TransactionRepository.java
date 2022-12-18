package org.example.repositories.impl;


import org.example.models.Transaction;
import org.example.repositories.ITransactionRepository;
import org.example.services.AthenaClientFactory;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TransactionRepository implements ITransactionRepository {
    private List<Transaction> transactions;
    private static final Logger logger = LoggerFactory.getLogger(TransactionRepository.class);
    private AthenaService athenaExecutor;
    public TransactionRepository() {
        this.transactions = new ArrayList<>();
        this.athenaExecutor = new AthenaService(AthenaClientFactory.createClientDev());
    }


    @Override
    public Transaction save(Transaction transaction) {
        String query =String.format(
                "Insert into transactions values ('%s','%s',%s,null)",
                transaction.getId(),
                transaction.getType(),
                String.valueOf(transaction.getAmount()).replace(',','.'));
        System.out.println(query);
        AthenaOrchestrator orchestrator = new AthenaOrchestrator(query, this.athenaExecutor);
        orchestrator.execute();
        return transaction;
    }

    @Override
    public Collection<Transaction> getAll() {
        String query ="SELECT * FROM transactions";
        logger.info("Initializing Athena Orchestrator");
        logger.info("Executing Athena Orchestrator");
        return new AthenaOrchestrator(query, this.athenaExecutor).execute();
    }

    @Override
    public Transaction getById(String id) {

        String query = String.format("SELECT * FROM transactions where id = '%s'",id);
        logger.info("Initializing Athena Orchestrator");
        AthenaOrchestrator orchestrator = new AthenaOrchestrator(query, this.athenaExecutor);
        logger.info("Executing Athena Orchestrator");
        return orchestrator.execute().stream().findFirst().get();
    }
}
