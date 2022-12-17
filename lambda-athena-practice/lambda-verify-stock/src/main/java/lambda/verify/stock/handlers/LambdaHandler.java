package lambda.verify.stock.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import lambda.verify.stock.configurations.AthenaClientFactory;
import lambda.verify.stock.models.ProductInput;
import lambda.verify.stock.models.Response;
import lambda.verify.stock.services.AthenaService;
import lambda.verify.stock.services.ProductServices;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import software.amazon.awssdk.services.athena.AthenaClient;

public class LambdaHandler implements RequestStreamHandler {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream, Charset.forName("US-ASCII"))));
        try
        {
            ProductInput[] payloadInput = gson.fromJson(reader, ProductInput[].class);
            List<ProductInput> productsList = Arrays.asList(payloadInput);
            
            AthenaClientFactory factory = new AthenaClientFactory();
            AthenaClient athenaClient = factory.createClientDev();
            AthenaService athenaQueryExecutor = new AthenaService(athenaClient);
            logger.log("Initializing Athena Orchestrator");

            ProductServices productServices = new ProductServices(athenaQueryExecutor);
            List<Response> responseList = productServices.getProductsStock(productsList);
            
            String result = gson.toJson(responseList);
            writer.write(gson.toJson(result));
            if (writer.checkError()) {
                logger.log("WARNING: Writer encountered an error.");
            }
            System.out.println("");
        } catch (IllegalStateException | JsonSyntaxException exception) {
            logger.log(exception.toString());
        } finally {
            reader.close();
            writer.close();
        }
    }
}
