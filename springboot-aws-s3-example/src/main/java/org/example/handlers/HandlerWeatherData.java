package org.example.handlers;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.models.WeatherData;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class HandlerWeatherData implements RequestHandler<WeatherData, String> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    public String handleRequest(WeatherData input, Context context) {

        validator.validate(input)
                .forEach(violation -> System.out.println(violation.getMessage()));

        String response;

        LambdaLogger logger = context.getLogger();

        logger.log("INPUT " + input);

        response = gson.toJson(input);

        return response;
    }
}
