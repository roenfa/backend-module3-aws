package org.example.config;


import org.example.repositories.ITransactionRepository;
import org.example.repositories.TransactionRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.services.lambda.LambdaClient;

@Configuration
public class RepositoriesConfigurator {

    @Bean
    public ITransactionRepository iTransactionRepository(){
        LambdaClient lambdaClient = DependencyFactory.lambdaClient();
        var transactionRepository = new TransactionRepository(lambdaClient);
        return  transactionRepository;
    }
}