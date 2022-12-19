package org.example.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;

@Configuration
public class AthenaClientFactory {
    @Bean
    public AthenaClient createClientDev() {
        return AthenaClient.builder()
                .region(Region.US_EAST_1)
                .build();
    }
}
