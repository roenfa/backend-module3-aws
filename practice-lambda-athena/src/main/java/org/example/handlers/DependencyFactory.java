package org.example.handlers;

import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;


public class DependencyFactory {
    public static LambdaClient lambdaClient() {

        return LambdaClient.builder()
                .region(Region.US_EAST_1)
                .httpClient(UrlConnectionHttpClient.builder().build())
                .build();
    }
}
