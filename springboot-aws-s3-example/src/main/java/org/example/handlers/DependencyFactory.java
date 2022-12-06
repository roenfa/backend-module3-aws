package org.example.handlers;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import software.amazon.awscdk.core.Environment;
import software.amazon.awssdk.regions.Region;

import java.net.URLConnection;

public class DependencyFactory {
    public static LambdaClient lambdaClient() {
        return LambdaClient.builder()
                .credentialsProvider(new ProfileCredentialsProvider("default"))
                .region(Region.US_EAST_1)
                .httpClientBuilder(URLConnectionHttpClient.builder)
                .build();
    }
}
