package org.example.handlers;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;

import java.net.URLConnection;

public class DependencyFactory {
    public static LambdaClient lambdaClient() {
        return LambdaClient.builder()
                .credentialsProvider(new ProfileCredentialsProvider("default"))
                .region(Regions.US_EAST_1)
                .httpClientBuilder(URLConnectionHttpClient.builder)
                .build();
    }
}
