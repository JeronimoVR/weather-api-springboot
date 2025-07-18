package com.jeronimo.weatherapi.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeronimo.weatherapi.dto.WeatherResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class WeatherClientImpl implements WeatherClient{

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${weather.api.key}")
    private String apiKey;

    public WeatherClientImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public WeatherResponse getWeather(String city, String date) {
        try {
            String url = String.format(
                    "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/%s/%s/?key=%s",
                    city, date, apiKey
            );
            System.out.println("🌐 Llamando a la API externa: " + url);

            String jsonResponse = restTemplate.getForObject(url, String.class);

            // Parsear manualmente el JSON para extraer solo lo necesario
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode current = root.path("currentConditions");

            return new WeatherResponse(
                    root.path("resolvedAddress").asText(),
                    current.path("temp").asDouble(),
                    current.path("humidity").asInt(),
                    current.path("conditions").asText()
            );

        } catch (RestClientException e) {
            throw new RuntimeException("🌐 Error al llamar a la API externa \n " +
                    "debes enviar la ciudad y las iniciales del pais -> london,uk \n" +
                    "la fecha debe tener formato aaaa-mm-dd \n"
                    + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("❌ Error procesando la respuesta de la API: " + e.getMessage());
        }

    }
}

