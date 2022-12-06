import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.example.handlers.HandlerWeatherData;
import org.example.models.WeatherData;
import org.junit.Before;
import org.junit.Test;

import org.assertj.core.api.Assertions;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HandlersTest {
    private WeatherData inputData = new WeatherData(1, 1.0, 120, 10);
    private WeatherData invalidInputData = new WeatherData(1.0, 120);
    private Context context;
    private LambdaLogger lambdaLogger;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String expectedResult = gson.toJson(inputData);

    @Before
    public void setUp() {
        lambdaLogger = mock(LambdaLogger.class);
        context = mock(Context.class);
    }

    @Test
    public void HandlerWeatherDataTest_validInput(){
        HandlerWeatherData inputDataHandler = new HandlerWeatherData();
        when(context.getLogger()).thenReturn(lambdaLogger);
        String result = inputDataHandler.handleRequest(inputData, context);
        Assertions.assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void HandlerWeatherDataTest_invalidInput() {
        HandlerWeatherData inputDataHandler = new HandlerWeatherData();
        when(context.getLogger()).thenReturn(lambdaLogger);
        String result = inputDataHandler.handleRequest(invalidInputData, context);
        Assertions.assertThat(result).isEqualTo("missing fields");
    }
}
