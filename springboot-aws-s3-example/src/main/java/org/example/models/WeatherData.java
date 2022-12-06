package org.example.models;

public class WeatherData {
    private Integer temperature;//required
    private Double humidityPoint;
    private Integer windKmh;
    private Integer pressureHPa;//required


    public WeatherData(Integer temperature, Double humidityPoint, Integer windKmh, Integer pressureHPa) {
        this.temperature = temperature;
        this.humidityPoint = humidityPoint;
        this.windKmh = windKmh;
        this.pressureHPa = pressureHPa;
    }

    

    public WeatherData(Double humidityPoint, Integer windKmh) {
        this.humidityPoint = humidityPoint;
        this.windKmh = windKmh;
    }



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
