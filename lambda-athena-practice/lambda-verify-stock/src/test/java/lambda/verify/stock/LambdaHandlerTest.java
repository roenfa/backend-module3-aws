package lambda.verify.stock;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lambda.verify.stock.handlers.LambdaHandler;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class LambdaHandlerTest {
    private Context context;
    private LambdaLogger lambdaLogger;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Before
    public void setUp() {
        lambdaLogger = mock(LambdaLogger.class);
        context = mock(Context.class);
    }

    @Test
    public void InputDataTest() throws Exception {
        InputStream input = new FileInputStream("src/test/java/lambda/verify/stock/input.json");
        OutputStream output = new FileOutputStream("src/test/java/lambda/verify/stock/output.json");
        LambdaHandler lambdaHandler = new LambdaHandler();
        when(context.getLogger()).thenReturn(lambdaLogger);
        lambdaHandler.handleRequest(input, output, context);
    }
}
