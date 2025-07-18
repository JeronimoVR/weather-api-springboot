package com.jeronimo.weatherapi.controller;

import com.jeronimo.weatherapi.dto.WeatherResponse;
import com.jeronimo.weatherapi.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/weather")
public class WeatherAppController {

    private final WeatherService weatherService;

    public WeatherAppController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    public WeatherResponse getWeather(@RequestParam String city, @RequestParam String date) {
        return weatherService.getWeatherForCity(city,date);
    }

}
