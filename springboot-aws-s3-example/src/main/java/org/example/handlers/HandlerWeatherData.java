package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.example.models.WeatherData;

public class HandlerWeatherData implements RequestHandler<WeatherData, String> {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String handleRequest(WeatherData input, Context context) {
        ValidateField validateField = new ValidateField(input);
        String response = "missing fields";
        LambdaLogger logger = context.getLogger();
        
        if(validateField.validate(input).size() < 1){
            logger.log("INPUT " + input);
            response = gson.toJson(input);
        }
        return response;
    }
}
