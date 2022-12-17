package org.example.handlers;

//import com.amazonaws.services.athena.AmazonAthena;
//import com.amazonaws.services.athena.AmazonAthenaClientBuilder;
//import com.amazonaws.services.athena.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import software.amazon.awssdk.services.athena.model.*;

public class LambdaAthena  implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {

        AmazonAthena athena = AmazonAthenaClientBuilder.defaultClient();
        String query = "SELECT * FROM Transaction WHERE Type = 'refund'";
        String database = "devopsathenadb";
        String s3OutputLocation = "s3://smm-springboot/athena/final-project/";

        StartQueryExecutionRequest request = new StartQueryExecutionRequest()
                .withQueryString(query)
                .withQueryExecutionContext(new QueryExecutionContext().withDatabase(database))
                .withResultConfiguration(new ResultConfiguration().withOutputLocation(s3OutputLocation));
        StartQueryExecutionResult result = athena.startQueryExecution(request);
        String executionId = result.getQueryExecutionId();

        // Wait for the query to complete
        GetQueryExecutionRequest getRequest = new GetQueryExecutionRequest().withQueryExecutionId(executionId);
        GetQueryExecutionResult getResult = athena.getQueryExecution(getRequest);
        while (!getResult.getQueryExecution().getStatus().getState().equals("SUCCEEDED")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // Do nothing
            }
            getResult = athena.getQueryExecution(getRequest);
        }

        // Get the query results
        GetQueryResultsRequest resultsRequest = new GetQueryResultsRequest().withQueryExecutionId(executionId);
        GetQueryResultsResult resultsResult = athena.getQueryResults(resultsRequest);
        String res = resultsResult.toString();
        System.out.println(resultsResult);

        return res;
    }
}
