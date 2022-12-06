package org.example.models;

public class WeatherData {
    private Integer temperature;//required
    private Double humidityPoint;
    private Integer windKmh;
    private Integer pressureHPa;//required

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
        return this.pressureHPa;
    }
    public void setPressureHPa(Integer p){
        this.pressureHPa = p;
    }

}
