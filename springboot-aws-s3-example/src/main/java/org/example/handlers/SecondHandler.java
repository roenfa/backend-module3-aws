package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.example.models.WeatherData;

public class SecondHandler implements RequestHandler<WeatherData, Object> {
    @Override
    public Object handleRequest(WeatherData input, Context context) {
        System.out.println("Input: " + input.toString());
        WeatherData weatherData = new WeatherData();
        weatherData.setTemperature(10);
        System.out.println("WeatherData: " + weatherData.toString());
        return weatherData;
    }
}
