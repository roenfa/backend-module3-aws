package org.example.services;

public interface IAthenaService {
    String submitQuery(String myQuery);
    void waitForQueryToComplete(String queryExecutionId) throws InterruptedException;
    void processQueryResult(String queryExecutionId);
}
