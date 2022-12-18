package org.example.handlers;

//import com.amazonaws.services.athena.AmazonAthena;
//import com.amazonaws.services.athena.AmazonAthenaClientBuilder;
//import com.amazonaws.services.athena.model.*;
import com.amazonaws.services.athena.AmazonAthena;
import com.amazonaws.services.athena.AmazonAthenaClientBuilder;
import com.amazonaws.services.athena.model.*;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.example.configurations.AthenaClientFactory;
import org.example.constants.Constants;
import org.example.main.App;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.athena.AthenaClient;

public class LambdaAthena  implements RequestHandler<Object, Object> {
    private static final Logger logger = LoggerFactory.getLogger(App.class);
    @Override
    public Object handleRequest(Object input, Context context) {
        AthenaClientFactory factory = new AthenaClientFactory();
        AthenaClient athenaClient = factory.createClientDev();
        AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
        logger.info("Initializing Athena Orchestrator");

        AthenaOrchestrator orchestrator = new AthenaOrchestrator(Constants.ATHENA_SAMPLE_QUERY, athenaQueryExecutor);
        logger.info("Executing Athena Orchestrator");
        orchestrator.execute();
//
//        AmazonAthena athena = AmazonAthenaClientBuilder.defaultClient();
//        String query = "SELECT * FROM transactions WHERE amount >= 5";
//        String database = "devopsathenadb";
//        String s3OutputLocation = "s3://mf-athena-s3";
//
//        StartQueryExecutionRequest request = new StartQueryExecutionRequest()
//                .withQueryString(query)
//                .withQueryExecutionContext(new QueryExecutionContext().withDatabase(database))
//                .withResultConfiguration(new ResultConfiguration().withOutputLocation(s3OutputLocation));
//        StartQueryExecutionResult result = athena.startQueryExecution(request);
//        String executionId = result.getQueryExecutionId();
//
//        // Wait for the query to complete
//        GetQueryExecutionRequest getRequest = new GetQueryExecutionRequest().withQueryExecutionId(executionId);
//        GetQueryExecutionResult getResult = athena.getQueryExecution(getRequest);
//        while (!getResult.getQueryExecution().getStatus().getState().equals("SUCCEEDED")) {
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                // Do nothing
//            }
//            getResult = athena.getQueryExecution(getRequest);
//        }
//
//        // Get the query results
//        GetQueryResultsRequest resultsRequest = new GetQueryResultsRequest().withQueryExecutionId(executionId);
//        GetQueryResultsResult resultsResult = athena.getQueryResults(resultsRequest);
//        String res = resultsResult.toString();
//        System.out.println(resultsResult);
//
       return null;
    }
}
