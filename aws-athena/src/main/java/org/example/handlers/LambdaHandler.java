package org.example.handlers;

import java.util.List;
import java.util.Map;

import org.example.configurations.AthenaClientFactory;
import org.example.models.Transaction;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import software.amazon.awssdk.services.athena.AthenaClient;

public class LambdaHandler implements RequestHandler<Map<String, String>, List<Transaction>> {
  private static final Logger logger = LoggerFactory.getLogger(LambdaHandler.class);

  @Override
  public List<Transaction> handleRequest(Map<String, String> input, Context context) {

  // Create an AthenaClient
   AthenaClientFactory factory = new AthenaClientFactory();
   AthenaClient athenaClient = factory.createClientDev();
   AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
 
   logger.info("Initializing Athena Orchestrator");
   // System.out.println("Initializing Athena Orchestrator");
   AthenaOrchestrator orchestrator = new AthenaOrchestrator(input.get("query"), athenaQueryExecutor);
   // System.out.println("Executing Athena Orchestrator");
   logger.info("Executing Athena Orchestrator");
    
    return orchestrator.execute();
  }

}
