package lambda.shoping.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.List;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import lambda.shoping.models.Product;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.lambda.LambdaClient;
import software.amazon.awssdk.services.lambda.model.InvocationType;
import software.amazon.awssdk.services.lambda.model.InvokeRequest;
import software.amazon.awssdk.services.lambda.model.InvokeResponse;

public class ShopingCartHandler implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String SECOND_FUNCTION_NAME = "verify_stock_cavs";
    private final LambdaClient lambdaClient = DependencyFactory.lambdaClient();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("US-ASCII"))));

        try
        {
            List<Product> payloadClass = gson.fromJson(reader, List.class);
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

            writer.write(gson.toJson(response.payload().asUtf8String()));
            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}
