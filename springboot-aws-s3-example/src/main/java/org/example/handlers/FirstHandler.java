package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.example.models.WeatherData;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;
public class FirstHandler implements RequestHandler<Object, Object> {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String SECOND_FUNCTION_NAME = "RESecondFunction";
    private final LambdaClient lambdaClient;

    public FirstHandler() {
        lambdaClient = DependencyFactory.lambdaClient();
    }

    @Override
    public Object handleRequest(Object input, Context context) {
        var payloadClass = new WeatherData();
        payloadClass.setTemperature(8);
        String payloadString = gson.toJson(payloadClass);
        var payload = SdkBytes.fromUtf8String(payloadString);

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(SECOND_FUNCTION_NAME)
                .invocationType(InvocationType.EVENT)
                .payload(payload)
                .build();

        InvokeResponse response = lambdaClient.invoke(invokeRequest);

        System.out.println("Response: " + response.toString());
        System.out.println("Payload: " + response.payload().asUtf8String());

        return input;
    }


}
