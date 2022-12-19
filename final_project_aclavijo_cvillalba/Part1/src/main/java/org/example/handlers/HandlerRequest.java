package org.example.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import org.example.configurations.AthenaClientFactory;
import org.example.constants.Constants;
import org.example.models.Transaction;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import software.amazon.awssdk.services.athena.AthenaClient;

public class HandlerRequest implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    StringBuilder sb = new StringBuilder();
    
    private String defaultQuery = Constants.ATHENA_SAMPLE_QUERY;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String inputString = sb.toString();
        inputString=inputString.replace('"', ' ');
        inputString=inputString.replace(" ", "");
        inputString=inputString.replace("{", "");
        inputString=inputString.replace("}", "");
        String[] column = inputString.split(":",0);        

        String Query = "SELECT * FROM transactionsupdate where " + column[0]+" = "+"'"+column[1].toUpperCase()+"'"+ ";";

        AthenaClientFactory factory = new AthenaClientFactory();
        AthenaClient athenaClient = factory.createClientDev();
        AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
        logger.log("Initializing Athena Orchestrator");

        if(inputString.length()>5){
            AthenaOrchestrator orchestrator = new AthenaOrchestrator(Query, athenaQueryExecutor);
            logger.log("Executing Athena Orchestrator");
            orchestrator.execute();
        }else{
            AthenaOrchestrator orchestrator = new AthenaOrchestrator(defaultQuery, athenaQueryExecutor);
            logger.log("Executing Athena Orchestrator");
            orchestrator.execute();
        }
    }
}