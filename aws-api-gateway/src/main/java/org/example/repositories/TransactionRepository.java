package org.example.repositories;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    System.out.println(lambdaAthenaInvoke());
    return this.transactions;
  }

  @Override
  public Transaction findById(String id) {
    for(Transaction transaction: getAll()){
      if(transaction.getId().equals(id)){
        return transaction;
      }
    }

    return null;
  }

  @Override
  public void deleteById(String id) {
    // TODO Auto-generated method stub
  }


  private InvokeResponse lambdaAthenaInvoke(){
    System.out.println("**************************************************");
    String query = "SELECT * FROM transactions;";

    System.out.println("Call: "+query);
    System.out.println("**************************************************");

    var payload = SdkBytes.fromUtf8String(query);

    InvokeRequest invokeRequest = InvokeRequest.builder()
            .functionName(SECOND_FUNCTION_NAME)
            .invocationType(InvocationType.REQUEST_RESPONSE)
            .payload(payload)
            .build();
    
    InvokeResponse response = lambdaClient.invoke(invokeRequest);
    System.out.println("Response: " + response.toString());
    System.out.println("Payload Stream: " + response.payload().asInputStream());
    System.out.println("Payload: " + response.payload().asUtf8String());


    return response;
  }

}
