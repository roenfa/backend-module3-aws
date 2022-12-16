package org.example.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.AthenaClientBuilder;

@Configuration
public class AthenaClientFactory {
//    @Bean
//    @Profile("prod")
//    public AthenaClient createClient() {
//        return AthenaClient.builder()
//                .region(Region.AP_SOUTH_1)
//                .credentialsProvider(InstanceProfileCredentialsProvider.create()).build();
//    }

    
    private final AthenaClientBuilder builder = AthenaClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .httpClientBuilder(UrlConnectionHttpClient.builder());

    @Bean
    @Profile("dev")
    public AthenaClient createClientDev(){
        return builder.build();
    }
    
}
