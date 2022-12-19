package org.example.config;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;

public class DependencyFactory {
  public static LambdaClient lambdaClient() {
    AwsCredentialsProvider credProvider = DefaultCredentialsProvider.create();

    return LambdaClient.builder()
            .credentialsProvider(credProvider)        
            .region(Region.US_EAST_1)
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .build();
  }
}
