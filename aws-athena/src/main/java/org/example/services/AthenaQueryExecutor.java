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
    private static Logger logger;

    public AthenaQueryExecutor() {
        logger = LoggerFactory.getLogger(AthenaQueryExecutor.class);
    }

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

    public static <T> List<T> processResultRows(
            AthenaClient athenaClient, String queryExecutionId, Class<T> pojoClass) {
        List<T> res = new ArrayList<>();
        try {
            GetQueryResultsRequest getQueryResultsRequest =
                    GetQueryResultsRequest.builder()
                            .queryExecutionId(queryExecutionId).build();

            GetQueryResultsIterable getQueryResultsResults =
                    athenaClient.getQueryResultsPaginator(getQueryResultsRequest);

            for (GetQueryResultsResponse result : getQueryResultsResults) {
                List<Row> results = result.resultSet().rows();
                res = processRows(results, pojoClass);
            }
        } catch (AthenaException e) {
            logger.error("Failed to process with reason: {}", e.getMessage());
        }
        return res;
    }

    @SneakyThrows
    private static <T> List<T> processRows(List<Row> row, Class<T> pojoClass) {
        List<T> res = new ArrayList<>();

        PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(pojoClass);
        ArrayList<String> columnInfo = new ArrayList<>();
        for (int i = 0; i < row.size(); i++) {
            if( i == 0 ){
                columnInfo = holdColumnInfo(row.get(i).data());
                continue;
            }
            List<Datum> allData = row.get(i).data();

            try {
                T obj = createGenericInstance(
                        pojoClass.getDeclaredConstructor().newInstance(),
                        pojoClass);

                for(int j = 0; j < allData.size(); j++){
                    Datum data = allData.get(j);
                    for (PropertyDescriptor targetPd : targetPds) {
                        Method writeMethod = targetPd.getWriteMethod();
                        String fieldName = targetPd.getName();

                        if(fieldName != null  &&
                                fieldName.equalsIgnoreCase(columnInfo.get(j))){
                            writeMethod.invoke(obj, data.varCharValue());
                            break;
                        }
                    }
                }

                res.add(obj);

            } catch (InstantiationException x) {
                x.printStackTrace();
            } catch (IllegalAccessException x) {
                x.printStackTrace();
            } catch (NoSuchMethodException x) {
                x.printStackTrace();
            } catch (InvocationTargetException x) {
                x.printStackTrace();
            }

        }
        return res;
    }
}
