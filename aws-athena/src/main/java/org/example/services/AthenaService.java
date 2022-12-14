package org.example.services;

import org.example.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.athena.model.*;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

import java.util.ArrayList;
import java.util.List;

public class AthenaService<T> implements IAthenaService {
    private static final Logger logger = LoggerFactory.getLogger(AthenaService.class);
    private final AthenaClient athenaClient;

    public AthenaService(AthenaClient athenaClient) {
        this.athenaClient = athenaClient;
    }

    public String submitQuery(String query) {

        QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder()
                .database(Constants.ATHENA_DEFAULT_DATABASE).build();

        ResultConfiguration resultConfiguration = ResultConfiguration.builder()
                .outputLocation(Constants.ATHENA_OUTPUT_BUCKET).build();

        StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
                .queryString(query)
                .queryExecutionContext(queryExecutionContext)
                .resultConfiguration(resultConfiguration).build();

        StartQueryExecutionResponse startQueryExecutionResponse = this.athenaClient.startQueryExecution(startQueryExecutionRequest);

        return startQueryExecutionResponse.queryExecutionId();
    }

    // Waits for an Amazon Athena query to complete, fail or to be cancelled.
    public void waitForQueryToComplete(String queryExecutionId) throws InterruptedException {
        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
                .queryExecutionId(queryExecutionId).build();

        GetQueryExecutionResponse getQueryExecutionResponse;
        boolean isQueryStillRunning = true;

        while (isQueryStillRunning) {
            getQueryExecutionResponse = this.athenaClient.getQueryExecution(getQueryExecutionRequest);
            String queryState =
                    getQueryExecutionResponse.queryExecution().status().state().toString();
            if (queryState.equals(QueryExecutionState.FAILED.toString())) {
                throw new RuntimeException("Error message: " + getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason());
            } else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
                throw new RuntimeException("The Amazon Athena query was cancelled.");
            } else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
                isQueryStillRunning = false;
            } else {
                Thread.sleep(Constants.SLEEP_AMOUNT_IN_MS);
            }
            logger.info("The current status is: " + queryState);
        }
    }
    public void processQueryResult(String queryExecutionId) {
        GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                .queryExecutionId(queryExecutionId).build();
        GetQueryResultsIterable getQueryResultsResults = this.athenaClient.getQueryResultsPaginator(getQueryResultsRequest);
        for (GetQueryResultsResponse resultResponse : getQueryResultsResults) {
            List<ColumnInfo> columnInfoList = resultResponse.resultSet().resultSetMetadata().columnInfo();
            int resultSize = resultResponse.resultSet().rows().size();
            logger.info("Result size: " + resultSize);
            List<Row> results = resultResponse.resultSet().rows();
            processRow(results, columnInfoList);
        }
    }
    private void processRow(List<Row> rowList, List<ColumnInfo> columnInfoList) {
        List<String> columns = new ArrayList<>();
        for (ColumnInfo columnInfo : columnInfoList) {
            columns.add(columnInfo.name());
        }
        for (Row row: rowList) {
            int index = 0;
            for (Datum datum : row.data()) {
                logger.info(columns.get(index) + ": " + datum.varCharValue());
                index++;
            }
            logger.info("===================================");
        }
    }
}
