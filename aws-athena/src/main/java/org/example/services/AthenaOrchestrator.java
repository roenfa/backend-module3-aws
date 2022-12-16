package org.example.services;


import com.google.gson.JsonArray;

public class AthenaOrchestrator<T>
{
    private final String query;
    private AthenaService<T> athenaService;
    JsonArray result;

    public AthenaOrchestrator(String query, AthenaService<T> athenaService) {
        this.query = query;
        this.athenaService = athenaService;
    }

    public void execute() {
        String queryExecutionId =
                this.athenaService.submitQuery(this.query);
        try {
            this.athenaService.waitForQueryToComplete(queryExecutionId);
            this.athenaService.processQueryResult(queryExecutionId);
        } catch(InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public JsonArray getResult(){
        return this.athenaService.getResult();
    }
}