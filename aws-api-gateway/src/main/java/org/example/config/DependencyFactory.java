package org.example.config;

import org.springframework.context.annotation.Bean;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;


public class DependencyFactory {
    @Bean
    public static LambdaClient lambdaClient() {

        return LambdaClient.builder()
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .region(Region.US_EAST_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}