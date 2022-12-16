package org.example.services;

import lombok.SneakyThrows;
import org.example.models.Products;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AthenaOrchestrator {
    private static final Logger logger = LoggerFactory.getLogger(AthenaService.class);
    private final String query;
    private AthenaService athenaService;

    public AthenaOrchestrator(String query, AthenaService athenaService) {
        this.query = query;
        this.athenaService = athenaService;
    }

    @SneakyThrows
    public List<Products> execute() {
        List<Products> transactionList = new ArrayList<>();
        String queryExecutionId =
                this.athenaService.submitQuery(this.query);

        this.athenaService.waitForQueryToComplete(queryExecutionId);
        transactionList = this.athenaService.processQueryResult(queryExecutionId);
        for (Products t: transactionList) {
            logger.info("Transaction = " + t);
        }

        return transactionList;
    }
}