package org.example.services;

import lombok.SneakyThrows;

import java.util.List;

public class AthenaOrchestrator<T>
{
    private final String query;
    private AthenaService athenaService;

    public AthenaOrchestrator(String query, AthenaService athenaService) {
        this.query = query;
        this.athenaService = athenaService;
    }

    @SneakyThrows
    public List<T> execute() {
        String queryExecutionId =
                this.athenaService.submitQuery(this.query);
        try {
            this.athenaService.waitForQueryToComplete(queryExecutionId);
            this.athenaService.processQueryResult(queryExecutionId);
        } catch(InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return null;
    }
}