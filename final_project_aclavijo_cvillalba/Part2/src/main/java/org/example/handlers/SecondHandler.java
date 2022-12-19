package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class SecondHandler implements RequestHandler<Object, Object> {
    @Override
    public Object handleRequest(Object input, Context context) {
        System.out.println("Input: " + input);
        return input;
    }
}
