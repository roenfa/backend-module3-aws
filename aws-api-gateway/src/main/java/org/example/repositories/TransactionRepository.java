package org.example.repositories;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.constants.AthenaQueries;
import org.example.models.Transaction;

import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class TransactionRepository implements ITransactionRepository {
  private List<Transaction> transactions;
  private static final String SECOND_FUNCTION_NAME = "djag-athena-lambda";
  private final LambdaClient lambdaClient;

  private AthenaQueries athenaQueries;

  Gson json;
  public TransactionRepository(LambdaClient lambdaClient){
    this.transactions = new ArrayList<>();
    this.lambdaClient = lambdaClient;
  }

  @Override
  public Transaction save(Transaction transaction) {
    String query = athenaQueries.ATHENA_CREATE_TRANSACTION;
    query +=  "'"+transaction.getId()+"'," + "'"+ transaction.getDate() + "', '" + transaction.getType() + "', " + transaction.getAmount() +")";
    return lambdaAthenaInvoke(query).get(0);
  }

  @Override
  public List<Transaction> getAll() {
    String query = athenaQueries.ATHENA_SELECT_QUERY;
    return lambdaAthenaInvoke(query);
  }

  @Override
  public Transaction findById(String id) {
    String query = athenaQueries.ATHENA_FIND_BY_ID_QUERY + "'" + id + "'";
    return lambdaAthenaInvoke(query).get(0);
  }

  @Override
  public void deleteById(String id) {
    String query = athenaQueries.ATHENA_DELETE_TRANSACTION + "'" + id + "'";
    lambdaAthenaInvoke(query);
  }


  private List<Transaction> lambdaAthenaInvoke(String query) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    System.out.println("**************************************************");
    //SELECT * FROM transactions to object
    String queryPayload = "{\"query\": \"" + query + "\"}";

    System.out.println("Call: "+queryPayload);
    System.out.println("**************************************************");

    var payload = SdkBytes.fromUtf8String(queryPayload);

    InvokeRequest invokeRequest = InvokeRequest.builder()
            .functionName(SECOND_FUNCTION_NAME)
            .invocationType(InvocationType.REQUEST_RESPONSE)
            .payload(payload)
            .build();
    //bufferreader to response




    InvokeResponse response = lambdaClient.invoke(invokeRequest);
    System.out.println("response: " + invokeRequest.payload().asString(StandardCharsets.UTF_8));

    InputStream responsePayload = response.payload().asInputStream();
    BufferedReader reader = new BufferedReader(new InputStreamReader(responsePayload, Charset.forName("US-ASCII")));
    Transaction[] transactions = gson.fromJson(reader, Transaction[].class);

    return Arrays.asList(transactions);
  }

}
