package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.models.WeatherData;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.jsii.api.InvokeRequest;

public class FirstHandler implements RequestHandler<Object, Object> {
    private static final String SECOND_FUNCTION_NAME = "SecondFunction";
    private final LambdaClient lambdaClient;

    public FirstHandler() {
        lambdaClient = DependencyFactory.lambdaClient();
    }

    @Override
    public Object handleRequest(Object input, Context context) {
//        var payload = SdkBytes.fromUtf8String("{\"input\":\"bootcamp\"}");
        var payload = new WeatherData();
        payload.setTemperature(8);

        InvokeRequest invokeRequest = InvokeRequest.builder()
                .functionName(SECOND_FUNCTION_NAME)
                .invocationType(Invocation.EVENT)
                .payload(payload)
                .build();

        InvokeResponse response = lambdaClient.invoke(invokeRequest);

        System.out.println("Response: " + response.toString());
        System.out.println("Payload: " + response.payload.asUtf8String());

        return input;
    }


}
