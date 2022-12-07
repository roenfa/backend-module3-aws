package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.models.WeatherData;

public class SecondHandler implements RequestHandler<WeatherData, Object> {
    @Override
    public Object handleRequest(WeatherData input, Context context) {
        System.out.println("Input: " + input);
        return input;
    }
}
