package org.example.repositories;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.models.Transaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class TransactionRepository implements ITransactionRepository {
  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  private static final String SECOND_FUNCTION_NAME = "cvillalba-aclavijo-final-project1";
  private List<Transaction> transactions;
  private LambdaClient lambdaClient;

  public TransactionRepository(LambdaClient lambdaClient){
    this.transactions = new ArrayList<>();
    this.lambdaClient = lambdaClient;
  }

  @Override
  public Transaction save(Transaction transaction) {
    this.transactions.add(transaction);
    return transaction;
  }

  @Override
  public Collection<Transaction> getAll() {
    return this.transactions;
  }

  @Override
  public Collection<Transaction> getBy(String params) {
    String payload = this.generatePayload(params);
    return this.invokeLambda(payload);
  }

  private Collection<Transaction> invokeLambda(String payloadString){
    
    var payload = SdkBytes.fromUtf8String(payloadString);

    InvokeRequest invokeRequest = InvokeRequest.builder()
            .functionName(SECOND_FUNCTION_NAME)
            .invocationType(InvocationType.REQUEST_RESPONSE)
            .payload(payload)
            .build();

    InvokeResponse response = lambdaClient.invoke(invokeRequest);
    InputStream responsePayload = response.payload().asInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(responsePayload, Charset.forName("US-ASCII")));
    Transaction[] transactions = gson.fromJson(reader, Transaction[].class);
    
    return Arrays.asList(transactions);
  }

  private String generatePayload(String params){
    String[] paramsArray = params.split("=");
    Map<String, String> queryObject = new HashMap<>();
    if(!params.equalsIgnoreCase("")){
      queryObject.put(paramsArray[0], paramsArray[1]);
    } else {
      queryObject.put("query", "all");
    }
    return gson.toJson(queryObject);
  }
}
