package org.example.services;

import org.example.models.Products;

import java.util.List;

public interface IAthenaService {
    String submitQuery(String myQuery);
    void waitForQueryToComplete(String queryExecutionId);
    List<Products> processQueryResult(String queryExecutionId);
}
