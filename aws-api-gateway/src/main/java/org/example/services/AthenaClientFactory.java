package org.example.services;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;


public class AthenaClientFactory {
    public static AthenaClient createClientDev() {
        return AthenaClient.builder()
                .region(Region.US_EAST_1)
                .httpClient(UrlConnectionHttpClient.create())
                .build();
    }
}
