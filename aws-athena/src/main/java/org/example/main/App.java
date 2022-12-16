package org.example.main;

import org.example.configurations.AthenaClientFactory;
import org.example.constants.Constants;
import org.example.handlers.LambdaHandler;
import org.example.services.AthenaOrchestrator;
import org.example.services.AthenaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.services.lambda.runtime.Context;

import software.amazon.awssdk.services.athena.AthenaClient;

@SpringBootApplication
public class App implements CommandLineRunner {
    private Context context;
    private String defaultQuery = Constants.ATHENA_SAMPLE_QUERY;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        LambdaHandler lambdaHandler = new LambdaHandler();
        // lambdaHandler.handleRequest(defaultQuery, context);
    }
}