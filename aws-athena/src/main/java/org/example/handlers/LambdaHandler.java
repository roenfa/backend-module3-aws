package org.example.handlers;

import java.io.*;

import org.example.configurations.AthenaClientFactory;
import org.example.constants.Constants;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import software.amazon.awssdk.services.athena.AthenaClient;

public class LambdaHandler implements RequestStreamHandler {
  private static final Logger logger = LoggerFactory.getLogger(LambdaHandler.class);
  private String defaultQuery = Constants.ATHENA_SAMPLE_QUERY;

  @Override
  public void handleRequest(InputStream inputStream, OutputStream output, Context context) throws IOException {
    // Create an AthenaClient
    AthenaClientFactory factory = new AthenaClientFactory();
    AthenaClient athenaClient = factory.createClientDev();
    AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
  
    logger.info("Initializing Athena Orchestrator");
    // System.out.println("Initializing Athena Orchestrator");
    AthenaOrchestrator orchestrator = new AthenaOrchestrator(defaultQuery, athenaQueryExecutor);
    // System.out.println("Executing Athena Orchestrator");
    logger.info("Executing Athena Orchestrator");
    
    orchestrator.execute();
  }
  
}
