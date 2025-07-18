package com.jeronimo.weatherapi.cache;

import com.jeronimo.weatherapi.dto.WeatherResponse;

import java.util.Optional;

public interface RedisCache {

    Optional<WeatherResponse> getWeather(String city,String date);
    void setWeather(String city,String date, WeatherResponse weatherResponse,long ttlInSeconds);


}
