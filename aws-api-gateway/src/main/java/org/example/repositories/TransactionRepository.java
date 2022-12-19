package org.example.repositories;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
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
  private static final String SECOND_FUNCTION_NAME = "aws_athena_cavs";
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
    Transaction[] payloadInput = gson.fromJson(reader, Transaction[].class);
    
    return Arrays.asList(payloadInput);
  }

  private String generatePayload(String params){
    List<String> columns = new ArrayList<>();
    String[] paramsArray = params.split("=");
    Map<String, String> queryObject = new HashMap<>();
    Transaction transaction = new Transaction();
    Field[] atributes = transaction.getClass().getDeclaredFields();
    for(Field f: atributes){
      String type = f.getType().getSimpleName();
      String name = f.getName();
      columns.add(name);
      if(name.equalsIgnoreCase(paramsArray[0]) && type.equalsIgnoreCase("String")){
        paramsArray[1] = "'" + paramsArray[1] + "'";
      }
    }
    String columnsString = String.join(",", columns);
    String queryConstraint = paramsArray[0] + " = " + paramsArray[1];
    return gson.toJson(queryObject);
  }
}
