package org.example.handlers;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.lambda.LambdaClient;


public class DependencyFactory {
    public static LambdaClient lambdaClient() {
        // TODO add a default credential provider to invoke lambda
        AwsCredentials cred = AwsBasicCredentials.create("AKIARECXQVRMBMM2NNZT", "lXeg+3DqizdcbqCNrJvTIVAJGcQhqm4L/MyMx7Ta");

        AwsCredentialsProvider credProvider = StaticCredentialsProvider.create(cred);

        return LambdaClient.builder()
                .credentialsProvider(credProvider)
                .region(Region.US_EAST_1)
                .httpClientBuilder(UrlConnectionHttpClient.builder())
                .build();
    }
}
