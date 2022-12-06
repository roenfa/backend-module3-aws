package org.example.handlers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.example.models.WeatherData;

public class ValidateField implements IValidateField {
    private Field[] properties;
    private String[] requiredFields = new String[]{"temperature", "pressureHPa"};

    public ValidateField(WeatherData input) {
        this.properties = input.getClass().getDeclaredFields();
    }

    public List<String> validate(Object input) {
        List<String> missingFields = new ArrayList<String>();
        for(Field f: properties){
            f.setAccessible(true);
            for(String reqField: requiredFields){
                if(f.getName() == reqField){
                    try{
                        if (f.get(input) == null) {
                            missingFields.add(f.getName());
                        }
                    } catch(IllegalAccessException e) {
                        System.err.println(e);
                        System.exit(1);
                    }       
                }
            }
        }
        return missingFields;
    }
}
