package org.example.services;

import org.example.models.Transactions;
import software.amazon.awssdk.services.athena.AthenaClient;

import java.util.List;

public interface IAthenaService {
    void getDataFromAthena(String myQuery);
}
