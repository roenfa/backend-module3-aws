package org.example.handlers;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;

// import java.net.URLConnection;

// import org.apache.http.impl.client.HttpClientBuilder;

public class DependencyFactory {
    public static LambdaClient lambdaClient() {

        return LambdaClient.builder()
                .credentialsProvider((AwsCredentialsProvider) new ProfileCredentialsProvider("default"))
                .region(Region.US_EAST_1)
                // .httpClientBuilder(URLConnectionHttpClient.builder)
                .httpClientBuilder(null)
                .build(); 
    }
}
