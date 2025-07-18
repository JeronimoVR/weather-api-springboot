package com.jeronimo.weatherapi.client;

import com.jeronimo.weatherapi.dto.WeatherResponse;

import java.util.Date;

public interface WeatherClient {

    WeatherResponse getWeather(String city, String date);

}
