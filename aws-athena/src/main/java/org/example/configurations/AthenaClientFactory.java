package org.example.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
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
        return AthenaClient.builder()
            .region(Region.US_EAST_1)
            .build();
    }
}
