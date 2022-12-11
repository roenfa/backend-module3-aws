package org.example.services;

import org.example.models.Transactions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.athena.AthenaClient;

import java.util.List;

@Service
public class AthenaServiceImpl<T> implements IAthenaService
{
    @Autowired
    AthenaClient athenaClient;

    @Override
    public List<Transactions> getDataFromAthena(String myQuery){
        return new AthenaOrchestrator<>(athenaClient, myQuery, Transactions.class).execute();
    }
}