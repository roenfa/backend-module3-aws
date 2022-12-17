package lambda.verify.stock.services;

import lambda.verify.stock.models.Product;

import java.util.List;

public interface IAthenaService {
    String submitQuery(String myQuery);
    void waitForQueryToComplete(String queryExecutionId);
    List<Product> processQueryResult(String queryExecutionId);
}
