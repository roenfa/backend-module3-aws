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
import java.util.HashMap;

import org.example.configurations.AthenaClientFactory;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import software.amazon.awssdk.services.athena.AthenaClient;

public class LambdaHandler implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        String queryString = "";
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("US-ASCII"))));
        try
        {
            HashMap event = gson.fromJson(reader, HashMap.class);
            logger.log("STREAM TYPE: " + inputStream.getClass().toString());
            logger.log("EVENT TYPE: " + event.getClass().toString());
            for(Object query: event.keySet()){
                if(query.toString().equals("query")){
                    queryString = event.get(query).toString();
                    logger.log(event.get(query).toString());
                };
            }
            
            AthenaClientFactory factory = new AthenaClientFactory();
            AthenaClient athenaClient = factory.createClientDev();
            AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
            logger.log("Initializing Athena Orchestrator");

            AthenaOrchestrator orchestrator = new AthenaOrchestrator(queryString, athenaQueryExecutor);
            logger.log("Executing Athena Orchestrator");
            String result = gson.toJson(orchestrator.execute());
            writer.write(result);
            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
            System.out.println(result);
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
    
}
