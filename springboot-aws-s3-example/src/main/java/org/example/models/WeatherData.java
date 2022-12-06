package org.example.models;

import javax.validation.constraints.NotNull;

public class WeatherData {

    @NotNull(message = "temperature must not be null")

    private Integer temperature; //required

    private Double humidityPoint;
    private Integer windKmh;

    @NotNull(message = "pressure can not be null")
    private Integer pressureHPa; //required

    public Integer getTemperature(){
        return this.temperature;
    }

    public void setTemperature(Integer t){
        this.temperature = t;
    }

    public Double getHumidityPoint(){
        return this.humidityPoint;
    }

    public Integer getWindKmh(){
        return this.windKmh;
    }

    public Integer getPressureHPa(){
        return this.windKmh;
    }

}
