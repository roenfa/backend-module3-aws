package lambda.shoping.cart;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Before;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lambda.shoping.handlers.ShopingCartHandler;

public class HandlersTest {
    private Context context;
    private LambdaLogger lambdaLogger;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void setUp() {
        lambdaLogger = mock(LambdaLogger.class);
        context = mock(Context.class);
    }

    @Test
    public void HandlerWeatherDataTest_validInput() throws IOException {
        InputStream input = new FileInputStream("src/test/resources/products.json");
        OutputStream output = new FileOutputStream("src/test/resources/output.json");
        ShopingCartHandler inputDataHandler = new ShopingCartHandler();
        when(context.getLogger()).thenReturn(lambdaLogger);
        inputDataHandler.handleRequest(input, output, context);
    }
}
