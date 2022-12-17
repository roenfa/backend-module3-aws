package lambda.verify.stock.services;


import com.google.gson.JsonArray;

import lombok.SneakyThrows;
import lambda.verify.stock.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AthenaOrchestrator {
    private static final Logger logger = LoggerFactory.getLogger(AthenaService.class);
    private final String query;
    private AthenaService athenaService;
    JsonArray result;

    public AthenaOrchestrator(String query, AthenaService athenaService) {
        this.query = query;
        this.athenaService = athenaService;
    }

    @SneakyThrows
    public List<Product> execute() {
        List<Product> transactionList = new ArrayList<>();
        String queryExecutionId =
                this.athenaService.submitQuery(this.query);

        this.athenaService.waitForQueryToComplete(queryExecutionId);
        transactionList = this.athenaService.processQueryResult(queryExecutionId);
        for (Product t: transactionList) {
            logger.info("Transaction = " + t);
        }
        return transactionList;
    }
}