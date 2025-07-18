package com.jeronimo.weatherapi.service;

import com.jeronimo.weatherapi.cache.RedisCache;
import com.jeronimo.weatherapi.client.WeatherClient;
import com.jeronimo.weatherapi.dto.WeatherResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class WeatherService {

    private final WeatherClient weatherClient;
    private final RedisCache redisCache;
    private static final long CACHE_TTL_SECONDS = 43200; // 12 horas

    public WeatherService(RedisCache redisCache, WeatherClient weatherClient) {
        this.redisCache = redisCache;
        this.weatherClient = weatherClient;
    }

    public WeatherResponse getWeatherForCity(String city, String date) {
        // 1. Buscar en caché
        Optional<WeatherResponse> cached = redisCache.getWeather(city.toLowerCase(), date);

        if (cached.isPresent()) {
            System.out.println("✅ Clima obtenido desde cache");
            return cached.get();
        }

        // 2. Si no está en caché, consultar API externa
        System.out.println("🌐 Clima obtenido desde API externa");
        WeatherResponse weather = weatherClient.getWeather(city,date);

        // 3. Guardar en cache para futuras consultas
        redisCache.setWeather(city.toLowerCase(),date, weather, CACHE_TTL_SECONDS);

        return weather;
    }

}
