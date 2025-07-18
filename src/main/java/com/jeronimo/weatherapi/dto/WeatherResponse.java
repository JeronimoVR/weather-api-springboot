package com.jeronimo.weatherapi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

    // Getters y setters (o usa Lombok si lo prefieres)
    private String city;
    private double temperature;
    private int humidity;
    private String description;

    public WeatherResponse() {}

    public WeatherResponse(String city, double temperature, int humidity, String description) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.description = description;
    }
}
