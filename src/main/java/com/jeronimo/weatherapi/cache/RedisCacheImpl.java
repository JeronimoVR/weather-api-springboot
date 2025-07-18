package com.jeronimo.weatherapi.cache;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeronimo.weatherapi.dto.WeatherResponse;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheImpl implements RedisCache{

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;


    public RedisCacheImpl(StringRedisTemplate redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Optional<WeatherResponse> getWeather(String city,String date) {
        String key = city + ":" + date;
        String json = redisTemplate.opsForValue().get(key);

        if (json == null) return Optional.empty();

        try {
            WeatherResponse response = objectMapper.readValue(json, WeatherResponse.class);
            return Optional.of(response);
        } catch (JsonProcessingException e) {
            System.err.println("❌ Error deserializando JSON desde Redis: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void setWeather(String city,String date, WeatherResponse weatherResponse, long ttlInSeconds) {
        if (city == null || date == null || weatherResponse == null) {
            throw new IllegalArgumentException("Ciudad, fecha o respuesta inválida");
        }
        try {
            String json = objectMapper.writeValueAsString(weatherResponse);
            String key = city + ":" + date;
            redisTemplate.opsForValue().set(key, json, ttlInSeconds, TimeUnit.SECONDS);

        } catch (JsonProcessingException e) {
            System.err.println("❌ Error serializando JSON para Redis: " + e.getMessage());
        }
    }
}
