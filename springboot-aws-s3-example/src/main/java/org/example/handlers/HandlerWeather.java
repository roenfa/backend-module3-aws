package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.Weather;

import java.util.MissingFormatArgumentException;

public class HandlerWeather implements RequestHandler<Weather, String> {
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public String handleRequest(Weather input, Context context) {
        String response;
        LambdaLogger logger = context.getLogger();
        logger.log("INPUT: " + input.getPressureHPa() +" "+ input.getTemperature() + " "+input.getWindKmh() + " "+input.getHumidityPoint());

        if(input.getTemperature() == null){
            throw new MissingFormatArgumentException("Temperature is required");
        } else if (input.getPressureHPa() == null) {
            throw new MissingFormatArgumentException("PressureHpa is required");
        } else {
            response = gson.toJson(input);
            return response;
        }
    }
}