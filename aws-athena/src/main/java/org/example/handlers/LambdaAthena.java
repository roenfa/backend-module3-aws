package org.example.handlers;


import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import software.amazon.awssdk.core.SdkBytes;

public class LambdaAthena  implements RequestHandler<Object, Object> {

    @Override
    public Object handleRequest(Object input, Context context) {

        @Override
        public Object handleRequest (Object input, Context context){


            Transaction payloadData = new Transaction();
            //This will convert object to JSON String
            String inputJSON = new Gson().toJson(payloadData);
            SdkBytes payload = SdkBytes.fromUtf8String(inputJSON);


            System.out.println("Response: " + response.toString());
            System.out.println("Payload: " + response.payload().asUtf8String());

            return input;

        }
    }
}
