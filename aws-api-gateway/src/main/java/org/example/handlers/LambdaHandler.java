package org.example.handlers;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.InitializationWrapper;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.serverless.proxy.spring.SpringBootProxyHandlerBuilder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import org.example.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LambdaHandler implements RequestStreamHandler {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    private static final Logger logger = LoggerFactory.getLogger(LambdaHandler.class);

    static {
        try {
            handler = new SpringBootProxyHandlerBuilder<AwsProxyRequest>()
                    .defaultProxy()
                    .initializationWrapper(new InitializationWrapper())
                    .servletApplication()
                    .springBootApplication(Main.class)
                    .buildAndInitialize();
        } catch (ContainerInitializationException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring boot app" + e);
        }
    }

    @Override
    public void handleRequest(InputStream input, OutputStream output, Context context) throws IOException {
        logger.info("Handle Request calling to proxy stream");
        handler.proxyStream(input, output, context);
        logger.info("Calling to proxy stream completed!!");
    }
}
