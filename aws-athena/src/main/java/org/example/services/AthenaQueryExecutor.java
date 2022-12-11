package org.example.services;

import lombok.SneakyThrows;
import org.example.constants.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import software.amazon.awssdk.services.athena.model.*;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Method;
import java.beans.PropertyDescriptor;

import static org.example.helpers.Helpers.createGenericInstance;
import static org.example.helpers.Helpers.holdColumnInfo;


public class AthenaQueryExecutor<T> {
    private static final Logger logger = LoggerFactory.getLogger(AthenaQueryExecutor.class);

    public static String submitAthenaQuery(AthenaClient athenaClient, String query) {

        QueryExecutionContext queryExecutionContext = QueryExecutionContext.builder()
                .database(Constants.ATHENA_DEFAULT_DATABASE).build();

        ResultConfiguration resultConfiguration = ResultConfiguration.builder()
                .outputLocation(Constants.ATHENA_OUTPUT_BUCKET).build();

        StartQueryExecutionRequest startQueryExecutionRequest = StartQueryExecutionRequest.builder()
                .queryString(query)
                .queryExecutionContext(queryExecutionContext)
                .resultConfiguration(resultConfiguration).build();

        StartQueryExecutionResponse startQueryExecutionResponse = athenaClient.startQueryExecution(startQueryExecutionRequest);

        return startQueryExecutionResponse.queryExecutionId();
    }

    // Waits for an Amazon Athena query to complete, fail or to be cancelled.
    public static void waitForQueryToComplete(
            AthenaClient athenaClient, String queryExecutionId) throws InterruptedException {
        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest.builder()
                .queryExecutionId(queryExecutionId).build();

        GetQueryExecutionResponse getQueryExecutionResponse;
        boolean isQueryStillRunning = true;

        while (isQueryStillRunning) {
            getQueryExecutionResponse = athenaClient.getQueryExecution(getQueryExecutionRequest);
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
            System.out.println("The current status is: " + queryState);
        }
    }

    // Process the result of each row

    public static void processResultRows(AthenaClient athenaClient, String queryExecutionId) {
        GetQueryResultsRequest getQueryResultsRequest = GetQueryResultsRequest.builder()
                .queryExecutionId(queryExecutionId).build();
        GetQueryResultsIterable getQueryResultsResults = athenaClient.getQueryResultsPaginator(getQueryResultsRequest);
        for (GetQueryResultsResponse Resultresult : getQueryResultsResults) {
            List<ColumnInfo> columnInfoList = Resultresult.resultSet().resultSetMetadata().columnInfo();
            int resultSize = Resultresult.resultSet().rows().size();
            logger.info("Result size: " + resultSize);
            List<Row> results = Resultresult.resultSet().rows();
            processRow(results, columnInfoList);
        }
    }
    private static void processRow(List<Row> rowList, List<ColumnInfo> columnInfoList) {
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
