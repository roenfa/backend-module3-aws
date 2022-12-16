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
        LambdaLogger logger = context.getLogger();

        logger.log("Received request with WeatherData: " + input);

        // Validate the temperature and pressureHPa attributes of the WeatherData model
        if(input.getTemperature() != null && input.getPressureHPa() > 0){
            String response = gson.toJson(input);

            logger.log("Returning response: " + response);
            
            return response;
        }else{
            return "Invalid temperature or pressureHPa enter";
        }
    }
}
