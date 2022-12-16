package org.example.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;

@Configuration
public class AthenaClientFactory {
//    @Bean
//    @Profile("prod")
//    public AthenaClient createClient() {
//        return AthenaClient.builder()
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(InstanceProfileCredentialsProvider.create()).build();
//    }

    @Bean
    public AthenaClient createClientDev() {
        // AwsCredentials cred = AwsBasicCredentials.create("API", "SECRET"); //used for local test code, on lambda will run by policy
        // AwsCredentialsProvider credProvider = StaticCredentialsProvider.create(cred);

        return AthenaClient.builder()
                // .credentialsProvider(credProvider)
                .region(Region.US_EAST_1)
                .build();
    }
}
